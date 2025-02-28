package net.aurika.dyanasis.declaration.namespace;

import net.aurika.dyanasis.NamingContract;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractDyanasisNamespace implements DyanasisNamespace {
    @NamingContract.Namespace
    private final @NotNull String name;
    protected @Nullable AbstractDyanasisNamespace parent;
    protected @NotNull Map<String, AbstractDyanasisNamespace> children;
    protected boolean available = true;

    public AbstractDyanasisNamespace(@NamingContract.Namespace final @NotNull String name) {
        this(name, null, new LinkedHashMap<>());
    }

    public AbstractDyanasisNamespace(@NamingContract.Namespace final @NotNull String name,
                                     @Nullable AbstractDyanasisNamespace parent,
                                     @NotNull Map<String, AbstractDyanasisNamespace> children
    ) {
        Validate.Arg.notNull(name, "name");
        this.name = name;
        this.parent = parent;
        this.children = children;
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
            ns = this.parent();
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
    public void syncParent(@Nullable AbstractDyanasisNamespace parent) {
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
    public @NotNull Map<String, AbstractDyanasisNamespace> children() {
        checkAvailable();
        LinkedHashMap<String, AbstractDyanasisNamespace> children = new LinkedHashMap<>();
        for (Map.Entry<String, AbstractDyanasisNamespace> entry : this.children.entrySet()) {
            if (entry.getValue().isAvailable()) {
                children.put(entry.getKey(), entry.getValue());
            }
        }
        return children;
    }

    public @Nullable AbstractDyanasisNamespace addChild(@NotNull AbstractDyanasisNamespace child) {
        checkAvailable();
        Validate.Arg.notNull(child, "child");
        String childName = child.name();
        Objects.requireNonNull(childName, "childName");
        return this.children.put(childName, child);
    }

    public @Nullable AbstractDyanasisNamespace removeChild(@NotNull String childName) {
        checkAvailable();
        Validate.Arg.notNull(childName, "childName");
        AbstractDyanasisNamespace child = this.children.remove(childName);
        child.available = false;
        return child;
    }

    public @NotNull Map<String, AbstractDyanasisNamespace> clearChildren() {
        checkAvailable();
        Map<String, AbstractDyanasisNamespace> temp = this.children;
        for (Map.Entry<String, AbstractDyanasisNamespace> entry : temp.entrySet()) {
            entry.getValue().available = false;
        }
        this.children = new LinkedHashMap<>();
        return temp;
    }
}
