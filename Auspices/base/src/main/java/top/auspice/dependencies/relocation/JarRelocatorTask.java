package top.auspice.dependencies.relocation;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

final class JarRelocatorTask {
    private static final Pattern SIGNATURE_FILE_PATTERN = Pattern.compile("META-INF/(?:[^/]+\\.(?:DSA|RSA|SF)|SIG-[^/]+)");
    private static final Pattern SIGNATURE_PROPERTY_PATTERN = Pattern.compile(".*-Digest");
    private final RelocatingRemapper remapper;
    private final JarOutputStream jarOut;
    private final JarFile jarIn;
    private final Set<String> resources = new HashSet<>();

    JarRelocatorTask(RelocatingRemapper remapper, JarOutputStream jarOut, JarFile jarIn) {
        this.remapper = remapper;
        this.jarOut = jarOut;
        this.jarIn = jarIn;
    }

    void processEntries() throws IOException {
        Enumeration<JarEntry> entries = this.jarIn.entries();

        while(entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (!name.equals("META-INF/INDEX.LIST") && !entry.isDirectory() && !SIGNATURE_FILE_PATTERN.matcher(name).matches()) {
                InputStream entryIn = this.jarIn.getInputStream(entry);

                try {
                    this.processEntry(entry, entryIn);
                } catch (Throwable var8) {
                    if (entryIn != null) {
                        try {
                            entryIn.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }
                    }

                    throw var8;
                }

                if (entryIn != null) {
                    entryIn.close();
                }
            }
        }

    }

    private void processEntry(JarEntry entry, InputStream entryIn) throws IOException {
        String name = entry.getName();
        String mappedName = this.remapper.map(name);
        this.processDirectory(mappedName, true);
        if (name.endsWith(".class")) {
            this.processClass(name, entryIn);
        } else if (name.equals("META-INF/MANIFEST.MF")) {
            this.processManifest(name, entryIn, entry.getTime());
        } else if (!this.resources.contains(mappedName)) {
            this.processResource(mappedName, entryIn, entry.getTime());
        }

    }

    private void processDirectory(String name, boolean parentsOnly) throws IOException {
        int index = name.lastIndexOf(47);
        if (index != -1) {
            String parentDirectory = name.substring(0, index);
            if (!this.resources.contains(parentDirectory)) {
                this.processDirectory(parentDirectory, false);
            }
        }

        if (!parentsOnly) {
            JarEntry entry = new JarEntry(name + "/");
            this.jarOut.putNextEntry(entry);
            this.resources.add(name);
        }
    }

    private void processManifest(String name, InputStream entryIn, long lastModified) throws IOException {
        Manifest in = new Manifest(entryIn);
        Manifest out = new Manifest();
        out.getMainAttributes().putAll(in.getMainAttributes());

        for (Map.Entry<String, Attributes> stringAttributesEntry : in.getEntries().entrySet()) {
            Attributes outAttributes = new Attributes();

            for (Map.Entry<Object, Object> objectObjectEntry : stringAttributesEntry.getValue().entrySet()) {
                String key = objectObjectEntry.getKey().toString();
                if (!SIGNATURE_PROPERTY_PATTERN.matcher(key).matches()) {
                    outAttributes.put(objectObjectEntry.getKey(), objectObjectEntry.getValue());
                }
            }

            out.getEntries().put(stringAttributesEntry.getKey(), outAttributes);
        }

        JarEntry jarEntry = new JarEntry(name);
        jarEntry.setTime(lastModified);
        this.jarOut.putNextEntry(jarEntry);
        out.write(this.jarOut);
        this.resources.add(name);
    }

    private void processResource(String name, InputStream entryIn, long lastModified) throws IOException {
        JarEntry jarEntry = new JarEntry(name);
        jarEntry.setTime(lastModified);
        this.jarOut.putNextEntry(jarEntry);
        copy(entryIn, this.jarOut);
        this.resources.add(name);
    }

    private void processClass(String name, InputStream entryIn) throws IOException {
        ClassReader classReader = new ClassReader(entryIn);
        ClassWriter classWriter = new ClassWriter(0);
        RelocatingClassVisitor classVisitor = new RelocatingClassVisitor(classWriter, this.remapper, name);

        try {
            classReader.accept(classVisitor, 8);
        } catch (Throwable ex) {
            throw new RuntimeException("Error processing class " + name, ex);
        }

        byte[] renamedClass = classWriter.toByteArray();
        String mappedName = this.remapper.map(name.substring(0, name.indexOf(46)));
        this.jarOut.putNextEntry(new JarEntry(mappedName + ".class"));
        this.jarOut.write(renamedClass);
    }

    private static void copy(InputStream from, OutputStream to) throws IOException {
        byte[] buf = new byte[8192];

        while(true) {
            int n = from.read(buf);
            if (n == -1) {
                return;
            }

            to.write(buf, 0, n);
        }
    }
}
