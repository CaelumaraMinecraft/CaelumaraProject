package net.aurika.auspice.asm;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import static org.objectweb.asm.Opcodes.*;

public class ASMEventExecutorGenerator {

  private static final AtomicInteger EVENT_ID_GEN = new AtomicInteger(1);

  public static byte @NotNull [] generateEventExecutor(@NotNull Method m, @NotNull String name) {
    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
    writer.visit(
        V1_8, ACC_PUBLIC, name.replace('.', '/'), null, Type.getInternalName(Object.class),
        new String[]{Type.getInternalName(EventExecutor.class)}
    );

    // Generate constructor
    GeneratorAdapter methodGenerator = new GeneratorAdapter(
        writer.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null), ACC_PUBLIC, "<init>", "()V");
    methodGenerator.loadThis();
    methodGenerator.visitMethodInsn(
        INVOKESPECIAL, Type.getInternalName(Object.class), "<init>", "()V",
        false
    ); // Invoke the super class (Object) constructor
    methodGenerator.returnValue();
    methodGenerator.endMethod();
    // Generate the execute method
    methodGenerator = new GeneratorAdapter(
        writer.visitMethod(
            ACC_PUBLIC, "execute", "(Lorg/bukkit/event/Listener;Lorg/bukkit/event/Event;)V", null, null),
        ACC_PUBLIC, "execute", "(Lorg/bukkit/event/Listener;Lorg/bukkit/event/Listener;)V"
    );
    methodGenerator.loadArg(0);
    methodGenerator.checkCast(Type.getType(m.getDeclaringClass()));
    methodGenerator.loadArg(1);
    methodGenerator.checkCast(Type.getType(m.getParameterTypes()[0]));
    methodGenerator.visitMethodInsn(
        m.getDeclaringClass().isInterface() ? INVOKEINTERFACE : INVOKEVIRTUAL,
        Type.getInternalName(m.getDeclaringClass()), m.getName(), Type.getMethodDescriptor(m),
        m.getDeclaringClass().isInterface()
    );
    if (m.getReturnType() != void.class) {
      methodGenerator.pop();
    }
    methodGenerator.returnValue();
    methodGenerator.endMethod();
    writer.visitEnd();
    return writer.toByteArray();
  }

  public static @NotNull String generateName() {
    int id = EVENT_ID_GEN.getAndIncrement();
    return "net.aurika.auspice.generated.GeneratedExecutor" + id;
  }

}