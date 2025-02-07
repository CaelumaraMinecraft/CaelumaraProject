package net.aurika.tasks;

import net.aurika.tasks.annotations.TaskSessionConstructor;
import net.aurika.tasks.container.LocalTaskSession;
import net.aurika.tasks.context.TaskContext;
import net.aurika.utils.checker.Checker;
import net.aurika.utils.reflection.Reflect;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class DefaultTaskSessionConstructor<C extends TaskContext> implements TaskSessionConstructor<C> {

    private final @NotNull Constructor<?>[] hierarchy;

    public DefaultTaskSessionConstructor(@NotNull Class<? extends LocalTaskSession> clazz1) {
        Objects.requireNonNull(clazz1);
        Class<?>[] var10000 = Reflect.getClassHierarchy(clazz1, true);
        Checker.Expr.notNull(var10000, "getClassHierarchy(...)");
        int var6 = 0;

        for (int var7 = var10000.length; var6 < var7; ++var6) {
            Class<?> it = (var10000)[var6];
            if (!LocalTaskSession.class.isAssignableFrom(it)) {
                String var19 = "All classes must be a LocalTaskSession, " + it + " was not.";
                throw new IllegalStateException(var19);
            }
        }

        List<Constructor<?>> constructorHierarchy = new ArrayList<>();
        Class<?> lastEnclosingClass = null;
        int var14 = 0;

        try {
            for (var6 = var10000.length; var14 < var6; ++var14) {
                Class<?> clazz = var10000[var14];

                if (lastEnclosingClass == null) {
                    constructorHierarchy.add(clazz.getConstructor());
                } else {
                    Class<?>[] var18 = new Class[]{lastEnclosingClass};
                    constructorHierarchy.add(clazz.getConstructor(var18));
                }

                lastEnclosingClass = clazz;
            }
        } catch (NoSuchMethodException ignored) {

        }

        this.hierarchy = (Constructor<?>[]) constructorHierarchy.toArray(new Constructor[0]);
    }

    public @NotNull Constructor<?>[] getHierarchy() {
        return this.hierarchy;
    }

    public @NotNull LocalTaskSession createSession(@NotNull C context) {
        Checker.Arg.notNull(context, "context");
        LocalTaskSession lastInstance = null;
        Class<?> enclosing = null;

        try {
            Constructor<?>[] var4 = this.hierarchy;
            int var5 = 0;

            for (int var6 = var4.length; var5 < var6; ++var5) {
                Constructor<?> constructor = var4[var5];
                Class clazz = constructor.getDeclaringClass();
                LocalTaskSession instance = context.getSession().getInstances().get(clazz);
                if (instance == null) {
                    LocalTaskSession var12;
                    if (enclosing == null) {
                        if (this.hierarchy.length > 1) {
                            throw new RuntimeException("The enclosing (" + constructor + ") instance was not found");
                        }

                        var12 = (LocalTaskSession) constructor.newInstance();
                    } else {
                        Object[] var10 = new Object[1];
                        Objects.requireNonNull(lastInstance);
                        var10[0] = lastInstance;
                        var12 = (LocalTaskSession) constructor.newInstance(var10);
                    }

                    instance = var12;
                    context.getSession().getInstances().put(clazz, instance);
                }

                lastInstance = instance;
                enclosing = clazz;
            }
        } catch (Throwable th) {
            String var10002 = "Failed to create session for " +
                    Arrays.toString(this.hierarchy) + ' ' + enclosing + " | " + lastInstance;
            throw new RuntimeException(var10002, th);
        }

        Objects.requireNonNull(lastInstance);
        return lastInstance;
    }
}
