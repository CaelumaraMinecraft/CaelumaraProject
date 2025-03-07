package net.aurika.gradle.dependency.relocation.relocator;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;

final class RelocatingClassVisitor extends ClassRemapper {
    private final String packageName;

    RelocatingClassVisitor(ClassWriter writer, RelocatingRemapper remapper, String name) {
        super(589824, writer, remapper);
        this.packageName = name.substring(0, name.lastIndexOf('/') + 1);
    }

    public void visitSource(String source, String debug) {
        if (source == null) {
            super.visitSource(null, debug);
        } else {
            String name = this.packageName + source;
            String mappedName = super.remapper.map(name);
            String mappedFileName = mappedName.substring(mappedName.lastIndexOf(47) + 1);
            super.visitSource(mappedFileName, debug);
        }
    }
}
