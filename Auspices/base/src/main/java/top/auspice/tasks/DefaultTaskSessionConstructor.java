package top.auspice.tasks;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.tasks.annotations.TaskSessionConstructor;
import top.auspice.tasks.container.LocalTaskSession;
import top.auspice.tasks.context.TaskContext;
import top.auspice.utils.reflection.Reflect;

import java.lang.reflect.Constructor;
import java.util.*;

public final class DefaultTaskSessionConstructor<C extends TaskContext> implements TaskSessionConstructor<C> {
    @NotNull
    private final Constructor<?>[] hierarchy;

    public DefaultTaskSessionConstructor(@NotNull Class<? extends LocalTaskSession> clazz1) {
        Objects.requireNonNull(clazz1);
        Class<?>[] var10000 = Reflect.getClassHierarchy(clazz1, true);
        Intrinsics.checkNotNullExpressionValue(var10000, "getClassHierarchy(...)");
        int var6 = 0;

        for(int var7 = var10000.length; var6 < var7; ++var6) {
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
            for(var6 = var10000.length; var14 < var6; ++var14) {
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

        this.hierarchy = (Constructor<?>[]) ((Collection<Constructor<?>>) constructorHierarchy).toArray(new Constructor[0]);
    }

    @NotNull
    public Constructor<?>[] getHierarchy() {
        return this.hierarchy;
    }

    @NotNull
    public LocalTaskSession createSession(@NotNull C context) {
        Intrinsics.checkNotNullParameter(context, "context");
        LocalTaskSession lastInstance = null;
        Class<?> enclosing = null;

        try {
            Constructor<?>[] var4 = this.hierarchy;
            int var5 = 0;

            for(int var6 = var4.length; var5 < var6; ++var5) {
                Constructor<?> constructor = var4[var5];
                Class clazz = constructor.getDeclaringClass();
                LocalTaskSession instance = context.getSession().getInstances().get(clazz);
                if (instance == null) {
                    LocalTaskSession var12;
                    Object var10000;
                    if (enclosing == null) {
                        if (this.hierarchy.length > 1) {
                            throw new RuntimeException("The enclosing (" + constructor + ") instance was not found");
                        }

                        var10000 = constructor.newInstance();
                        Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type top.auspice.tasks.adapters.LocalTaskSession");
                        var12 = (LocalTaskSession)var10000;
                    } else {
                        Object[] var10 = new Object[1];
                        Intrinsics.checkNotNull(lastInstance);
                        var10[0] = lastInstance;
                        var10000 = constructor.newInstance(var10);
                        Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type top.auspice.tasks.adapters.LocalTaskSession");
                        var12 = (LocalTaskSession)var10000;
                    }

                    instance = var12;
                    Map<Class<? extends LocalTaskSession>, LocalTaskSession> var13 = context.getSession().getInstances();
                    Intrinsics.checkNotNull(clazz, "null cannot be cast to non-null type java.lang.Class<top.auspice.tasks.adapters.LocalTaskSession>");
                    var13.put(clazz, instance);
                }

                lastInstance = instance;
                enclosing = clazz;
            }
        } catch (Throwable var11) {
            StringBuilder var10002 = (new StringBuilder()).append("Failed to create session for ");
            String var10003 = Arrays.toString(this.hierarchy);
            Intrinsics.checkNotNullExpressionValue(var10003, "toString(...)");
            throw new RuntimeException(var10002.append(var10003).append(' ').append(enclosing).append(" | ").append(lastInstance).toString(), var11);
        }

        Intrinsics.checkNotNull(lastInstance);
        return lastInstance;
    }
}
