package net.aurika.dyanasis.api.declaration.namespace;

import net.aurika.dyanasis.api.Named;
import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.container.DyanasisFunctionContainer;
import net.aurika.dyanasis.api.declaration.invokable.property.container.DyanasisPropertyContainer;
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
public class DyanasisNamespaceTree implements DyanasisNamespaceContainer {

    private final @NotNull Map<String, NodeDyanasisNamespace> roots = new LinkedHashMap<>();

    @Unmodifiable
    public @NotNull Map<String, DyanasisNamespace> roots() {
        return Collections.unmodifiableMap(roots);
    }

    public @Nullable DyanasisNamespaceTree.NodeDyanasisNamespace removeRoot(@NotNull String name) {
        @Nullable NodeDyanasisNamespace root = roots.remove(name);
        if (root != null) {
            root.killThisAndChildren();
        }
        return root;
    }

    @Override
    public @NotNull Collection<? extends DyanasisNamespace> allNamespaces() {
        List<NodeDyanasisNamespace> nss = new ArrayList<>();
        for (NodeDyanasisNamespace root : roots.values()) {
            nss.add(root);
            collectAllChildren(root, nss);
        }
        return nss;
    }

    private static void collectAllChildren(@NotNull NodeDyanasisNamespace ns, @NotNull Collection<NodeDyanasisNamespace> container) {
        for (var child : ns.children.values()) {
            container.add(child);
            collectAllChildren(child, container);
        }
    }

    @Override
    public @Nullable NodeDyanasisNamespace findNamespace(@NotNull String @NotNull [] path) {
        if (path.length == 0) {
            return null;
        }
        if (path.length == 1) {
            return roots.get(path[0]);
        }
        @Nullable NodeDyanasisNamespace ns = roots.get(path[0]);
        for (int i = 1, pathLength = path.length; i < pathLength; i++) {
            if (ns != null) {
                String name = path[i];
                ns = ns.getChild(name);
            } else {
                return null;
            }
        }
        return ns;
    }

    public class NodeDyanasisNamespace implements DyanasisNamespace, Named {
        @NamingContract.Namespace
        private final @NotNull String name;
        protected @Nullable NodeDyanasisNamespace parent;
        protected @NotNull Map<String, NodeDyanasisNamespace> children;
        protected boolean available = true;

        public NodeDyanasisNamespace(@NamingContract.Namespace final @NotNull String name) {
            this(name, null, new LinkedHashMap<>());
        }

        public NodeDyanasisNamespace(@NamingContract.Namespace final @NotNull String name,
                                     @Nullable DyanasisNamespaceTree.NodeDyanasisNamespace parent,
                                     @NotNull Map<String, NodeDyanasisNamespace> children
        ) {
            Validate.Arg.notNull(name, "name");
            this.name = name;
            this.parent = parent;
            this.children = children;
        }

        private void let(@NotNull Consumer<NodeDyanasisNamespace> action) {
            action.accept(this);
        }

        /**
         * @throws IllegalStateException when the namespace is not available
         */
        public void checkAvailable() {
            if (!available) {
                throw new IllegalStateException("This namespace " + name + " is not available (probably loose parent namespace)");
            }
        }

        public boolean isAvailable() {
            return available;
        }

        protected void removeThis() {
            available = false;
            @Nullable NodeDyanasisNamespace parent = this.parent;
            if (parent != null) {
                parent.removeChild(this.name);
            }
        }

        /**
         * Lets this {@linkplain NodeDyanasisNamespace} invalidate.
         *
         * @return the previous survival
         */
        protected boolean killThis() {
            boolean temp = available;
            available = false;
            return temp;
        }

        protected void killChildren() {
            for (var child : children.values()) {
                child.killThis();
                child.killChildren();
            }
        }

        protected void killThisAndChildren() {
            killThis();
            killChildren();
        }

        public @NotNull DyanasisNamespaceTree tree() {
            return DyanasisNamespaceTree.this;
        }

        @Override
        @NamingContract.Namespace
        public @NotNull String name() {
            return name;
        }

        public @NotNull String @NotNull [] path() {
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
            return path;
        }

        @Override
        public @Nullable DyanasisNamespace parent() {
            checkAvailable();
            return parent;
        }

        /**
         * Sets the new parent and sync data.
         *
         * @param parent the new parent
         */
        protected void syncParent(@Nullable DyanasisNamespaceTree.NodeDyanasisNamespace parent) {
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
        public @NotNull Map<String, NodeDyanasisNamespace> children() {
            checkAvailable();
            LinkedHashMap<String, NodeDyanasisNamespace> children = new LinkedHashMap<>();
            for (Map.Entry<String, NodeDyanasisNamespace> entry : this.children.entrySet()) {
                if (entry.getValue().isAvailable()) {
                    children.put(entry.getKey(), entry.getValue());
                }
            }
            return children;
        }

        @Override
        public @Nullable NodeDyanasisNamespace getChild(@NotNull String name) {
            checkAvailable();
            return children.get(name);
        }

        public @Nullable DyanasisNamespaceTree.NodeDyanasisNamespace addChild(@NotNull NodeDyanasisNamespace child) {
            checkAvailable();
            Validate.Arg.notNull(child, "child");
            String childName = child.name();
            Objects.requireNonNull(childName, "childName");
            return this.children.put(childName, child);
        }

        public @Nullable DyanasisNamespaceTree.NodeDyanasisNamespace removeChild(@NotNull String childName) {
            checkAvailable();
            Validate.Arg.notNull(childName, "childName");
            NodeDyanasisNamespace child = this.children.remove(childName);
            child.available = false;
            return child;
        }

        public @NotNull Map<String, NodeDyanasisNamespace> clearChildren() {
            checkAvailable();
            Map<String, NodeDyanasisNamespace> oldChildren = this.children;
            for (Map.Entry<String, NodeDyanasisNamespace> entry : oldChildren.entrySet()) {
                entry.getValue().available = false;
            }
            this.children = new LinkedHashMap<>();
            return oldChildren;
        }

        @Override
        public @NotNull DyanasisPropertyContainer<? extends NamespaceProperty> dyanasisProperties() {
            checkAvailable();
        }

        @Override
        public @NotNull DyanasisFunctionContainer<? extends NamespaceFunction> dyanasisFunctions() {
            checkAvailable();
        }

        @Override
        public @Nullable NamespaceDoc dyanasisDoc() {
            checkAvailable();
        }
    }
}
