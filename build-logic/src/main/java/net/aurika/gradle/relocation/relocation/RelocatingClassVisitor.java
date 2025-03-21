package net.aurika.gradle.relocation.relocation;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;

final class RelocatingClassVisitor extends ClassRemapper {
    private final String packageName;

    RelocatingClassVisitor(ClassWriter writer, RelocatingRemapper remapper, @NotNull String name) {
        super(589824, writer, remapper);
        this.packageName = name.substring(0, name.lastIndexOf('/') + 1);
    }

    @Override
    public void visitSource(String source, String debug) {
        if (source == null) {
            super.visitSource(null, debug);
        } else {
            String name = this.packageName + source;
            String mappedName = super.remapper.map(name);
            String mappedFileName = mappedName.substring(mappedName.lastIndexOf(0x2f) + 1);
            super.visitSource(mappedFileName, debug);
        }
    }

//    private boolean visitedInterface;
//
//    @Override
//    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//        super.visit(version, access, name, signature, superName, interfaces);
//        visitedInterface = (access & Opcodes.ACC_INTERFACE) != 0;
//    }
//
//    @Override
//    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
//        if (visitedInterface && descriptor.endsWith("kotlin/Metadata;")) {
//            return null;
//        } else {
//            return super.visitAnnotation(descriptor, visible);
//        }
//    }
}
