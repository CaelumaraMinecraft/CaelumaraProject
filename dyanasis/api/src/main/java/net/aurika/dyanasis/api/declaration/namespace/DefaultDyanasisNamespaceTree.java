package net.aurika.dyanasis.api.declaration.namespace;

import net.aurika.dyanasis.api.Named;
import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.member.function.container.SimpleDyanasisFunctionRegistry;
import net.aurika.dyanasis.api.declaration.member.property.container.SimpleDyanasisPropertyRegistry;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.function.Consumer;

/**
 * The standard dyanasis namespace implementation.
 */
public class DefaultDyanasisNamespaceTree implements DyanasisNamespaceContainer, DyanasisRuntimeObject {

  private final @NotNull DyanasisRuntime runtime;
  private final @NotNull Map<String, StandardDyanasisNamespace> roots;

  public DefaultDyanasisNamespaceTree(@NotNull DyanasisRuntime runtime) {
    this(runtime, new LinkedHashMap<>());
  }

  public DefaultDyanasisNamespaceTree(@NotNull DyanasisRuntime runtime, @NotNull Map<String, StandardDyanasisNamespace> roots) {
    Validate.Arg.notNull(runtime, "runtime");
    Validate.Arg.notNull(roots, "roots");
    this.runtime = runtime;
    this.roots = roots;
  }

  @Unmodifiable
  public @NotNull Map<String, DyanasisNamespace> roots() {
    return Collections.unmodifiableMap(roots);
  }

  public @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace removeRoot(@NotNull String name) {
    @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace root = roots.remove(name);
    if (root != null) {
      root.killThisAndChildren();
    }
    return root;
  }

  @Override
  public @NotNull Map<DyanasisNamespaceIdent, ? extends StandardDyanasisNamespace> allNamespaces() {
    HashMap<DyanasisNamespaceIdent, StandardDyanasisNamespace> nss = new HashMap<>();
    for (Map.Entry<String, StandardDyanasisNamespace> rootEntry : roots.entrySet()) {
      StandardDyanasisNamespace rootNS = rootEntry.getValue();
      nss.put(DyanasisNamespaceIdent.path(rootEntry.getKey()), rootNS);
      collectAllChildren(rootEntry.getValue(), nss);
    }
    return nss;
  }

  protected static void collectAllChildren(@NotNull DefaultDyanasisNamespaceTree.StandardDyanasisNamespace ns, @NotNull Map<DyanasisNamespaceIdent, StandardDyanasisNamespace> container) {
    for (StandardDyanasisNamespace child : ns.children.values()) {
      container.put(child.ident(), child);
      collectAllChildren(child, container);
    }
  }

  @Override
  public @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace findNamespace(@NotNull DyanasisNamespaceIdent path) {
    if (path.length() == 0) {
      return null;
    }
    if (path.length() == 1) {
      return roots.get(path.sectionAt(0));
    }
    @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace ns = roots.get(path.sectionAt(0));
    for (int i = 1, pathLength = path.length(); i < pathLength; i++) {
      if (ns != null) {
        String name = path.sectionAt(i);
        ns = ns.foundChild(name);
      } else {
        return null;
      }
    }
    return ns;
  }

  @SuppressWarnings("PatternValidation")
  @Override
  public @NotNull StandardDyanasisNamespace foundOrCreate(@NotNull DyanasisNamespaceIdent path) {
    Validate.Arg.notNull(path, "path");
    int length = path.length();
    if (length == 0) {
      throw new IllegalArgumentException("DyanasisNamespace path must not be empty");
    }
    String rootName = path.sectionAt(0);
    @Nullable StandardDyanasisNamespace foundRoot = roots.get(rootName);
    if (foundRoot == null) {
      foundRoot = new StandardDyanasisNamespace(rootName);
      roots.put(rootName, foundRoot);
    }
    if (length == 1) {
      return foundRoot;
    } else {  // length > 1
      return foundOrCreate$$core(foundRoot, path, 1);
    }
  }

  @SuppressWarnings("PatternValidation")   // TODO validate
  protected final @NotNull StandardDyanasisNamespace foundOrCreate$$core(@NotNull StandardDyanasisNamespace ns, @NotNull DyanasisNamespaceIdent path, int index) {
    Validate.Arg.notNull(ns, "ns");
    Validate.Arg.notNull(path, "path");
    int length = path.length();
    String section = path.sectionAt(index);
    if (index < length) {
      @Nullable StandardDyanasisNamespace subNS = ns.foundChild(section);
      if (subNS == null) {
        subNS = new StandardDyanasisNamespace(section, ns);
        ns.addChild(subNS);
      }
      return foundOrCreate$$core(subNS, path, index + 1);
    } else {
      return ns;
    }
  }

  @Override
  public @NotNull DyanasisRuntime dyanasisRuntime() {
    return runtime;
  }

  public record A(int a, String b) {
  }

  public static void a() {
    A a = new A(1, null);
    int sada = a.a();
  }

  public class StandardDyanasisNamespace implements DyanasisNamespace, Named {

    @NamingContract.Namespace
    private final @NotNull String name;
    protected @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace parent;
    protected @NotNull Map<String, StandardDyanasisNamespace> children;
    protected boolean available = true;

    protected @NotNull StandardNamespacePropertyContainer properties = new StandardNamespacePropertyContainer();
    protected @NotNull StandardNamespaceFunctionContainer functions = new StandardNamespaceFunctionContainer();
    protected @Nullable NamespaceDoc doc;
    protected @NotNull Map<String, DyanasisType<?>> types = new HashMap<>();

    protected StandardDyanasisNamespace(@NamingContract.Namespace final @NotNull String name) {
      this(name, null, new LinkedHashMap<>());
    }

    protected StandardDyanasisNamespace(@NamingContract.Namespace final @NotNull String name, @Nullable StandardDyanasisNamespace parent) {
      this(name, parent, new LinkedHashMap<>());
    }

    public StandardDyanasisNamespace(
        @NamingContract.Namespace final @NotNull String name,
        @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace parent,
        @NotNull Map<String, StandardDyanasisNamespace> children
    ) {
      Validate.Arg.notNull(name, "name");
      this.name = name;
      this.parent = parent;
      this.children = children;
    }

    private void let(@NotNull Consumer<StandardDyanasisNamespace> action) {
      action.accept(this);
    }

    /**
     * @throws IllegalStateException when the namespace is not available
     */
    public void checkAvailable() {
      if (!available) {
        throw new IllegalStateException(
            "This namespace " + name + " is not available (probably loose parent namespace)");
      }
    }

    public boolean isAvailable() {
      return available;
    }

    protected void removeThis() {
      available = false;
      @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace parent = this.parent;
      if (parent != null) {
        parent.removeChild(this.name);
      }
    }

    /**
     * Lets this {@linkplain StandardDyanasisNamespace} invalidate.
     *
     * @return the previous survival
     */
    protected boolean killThis() {
      boolean temp = available;
      available = false;
      return temp;
    }

    protected void killChildren() {
      for (StandardDyanasisNamespace child : children.values()) {
        child.killThis();
        child.killChildren();
      }
    }

    protected void killThisAndChildren() {
      killThis();
      killChildren();
    }

    @Override
    @NamingContract.Namespace
    public @NotNull String name() {
      return name;
    }

    public @NotNull DyanasisNamespaceIdent ident() {
      @Nullable DyanasisNamespace ns = this;
      int count = 1;  // contains this
      while (ns != null) {
        count = count + 1;
        ns = ns.parent();
      }
      String[] path = new String[count];
      ns = this;
      int i = 0;
      while (ns != null) {
        path[i] = ns.name();
        i++;
        ns = ns.parent();
      }
      return DyanasisNamespaceIdent.path(path);
    }

    @Override
    public @Nullable DyanasisNamespace parent() {
      checkAvailable();
      return parent;
    }

    /**
     * Changes to the new parent and sync data.
     *
     * @param parent the new parent
     */
    protected void syncParent(@Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace parent) {
      checkAvailable();
      if (this.parent != null) {  // remove this from old parent's children
        this.parent.children.remove(this.name);
      }
      this.parent = parent;  // set the new parent
      if (parent != null) {
        parent.children.put(this.name, this);  // add this to new parent's children
      }
    }

    @Override
    @Contract("-> new")
    public @NotNull Map<String, StandardDyanasisNamespace> children() {
      checkAvailable();
      LinkedHashMap<String, StandardDyanasisNamespace> children = new LinkedHashMap<>();
      for (Map.Entry<String, StandardDyanasisNamespace> entry : this.children.entrySet()) {
        if (entry.getValue().isAvailable()) {
          children.put(entry.getKey(), entry.getValue());
        }
      }
      return children;
    }

    @Override
    public @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace foundChild(@NotNull String name) {
      checkAvailable();
      return children.get(name);
    }

    public @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace addChild(@NotNull DefaultDyanasisNamespaceTree.StandardDyanasisNamespace child) {
      checkAvailable();
      Validate.Arg.notNull(child, "child");
      String childName = child.name();
      Objects.requireNonNull(childName, "childName");
      return this.children.put(childName, child);
    }

    public @Nullable DefaultDyanasisNamespaceTree.StandardDyanasisNamespace removeChild(@NotNull String childName) {
      checkAvailable();
      Validate.Arg.notNull(childName, "childName");
      StandardDyanasisNamespace child = this.children.remove(childName);
      child.available = false;
      return child;
    }

    public @NotNull Map<String, StandardDyanasisNamespace> clearChildren() {
      checkAvailable();
      Map<String, StandardDyanasisNamespace> oldChildren = this.children;
      for (Map.Entry<String, StandardDyanasisNamespace> entry : oldChildren.entrySet()) {
        entry.getValue().available = false;
      }
      this.children = new LinkedHashMap<>();
      return oldChildren;
    }

    public @NotNull DefaultDyanasisNamespaceTree tree() {
      return DefaultDyanasisNamespaceTree.this;
    }

    @Override
    public @NotNull StandardNamespacePropertyContainer dyanasisProperties() {
      checkAvailable();
      return properties;
    }

    @Override
    public @NotNull StandardNamespaceFunctionContainer dyanasisFunctions() {
      checkAvailable();
      return functions;
    }

    @Override
    public @Nullable NamespaceDoc dyanasisDoc() {
      checkAvailable();
      return doc;
    }

    @Override
    public void dyanasisDoc(@Nullable NamespaceDoc doc) {
      checkAvailable();
      this.doc = doc;
    }

    @Override
    public @Nullable DyanasisType<?> getDyanasisType(@NotNull String typename) {
      Validate.Arg.notNull(typename, "typename");
      checkAvailable();
      return types.get(typename);
    }

    @Override
    public boolean hasDyanasisType(@NotNull String typename) {
      Validate.Arg.notNull(typename, "typename");
      checkAvailable();
      return types.containsKey(typename);
    }

    @Override
    public @Unmodifiable @NotNull Map<String, ? extends DyanasisType<?>> dyanasisTypes() {
      checkAvailable();
      return Collections.unmodifiableMap(types);
    }

    @Override
    public @Nullable DyanasisType<?> addDyanasisType(@NotNull DyanasisType<?> type) {
      Validate.Arg.notNull(type, "type");
      checkAvailable();
      String typename = type.ident().typeName();
      DyanasisNamespace namespace = type.dyanasisNamespace();
      if (!Objects.equals(namespace, this)) {
        throw new IllegalArgumentException(
            "The namespace of type: " + type + " doesn't equals the added namespace " + this);
      }
      return types.put(typename, type);
    }

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      checkAvailable();
      return DefaultDyanasisNamespaceTree.this.runtime;
    }

    public class StandardNamespacePropertyContainer extends SimpleDyanasisPropertyRegistry<NamespaceProperty> implements NamespacePropertyContainer<NamespaceProperty> {

      @Override
      public @NotNull StandardDyanasisNamespace owner() {
        return StandardDyanasisNamespace.this;
      }

    }

    public class StandardNamespaceFunctionContainer extends SimpleDyanasisFunctionRegistry<NamespaceFunction> implements NamespaceFunctionContainer<NamespaceFunction> {

      @Override
      public @NotNull StandardDyanasisNamespace owner() {
        return StandardDyanasisNamespace.this;
      }

    }

    @Override
    public String toString() {
      return getClass().getSimpleName() + Arrays.toString(ident().path());
    }

  }

}
