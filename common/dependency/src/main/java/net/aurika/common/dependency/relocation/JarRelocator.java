package net.aurika.common.dependency.relocation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public final class JarRelocator {
    private final File input;
    private final File output;
    private final RelocatingRemapper remapper;
    private final AtomicBoolean used = new AtomicBoolean(false);

    public JarRelocator(File input, File output, Collection<Relocation> relocations) {
        this.input = input;
        this.output = output;
        this.remapper = new RelocatingRemapper(relocations);
    }

    public JarRelocator(File input, File output, Map<String, String> relocations) {
        this.input = input;
        this.output = output;
        Collection<Relocation> c = new ArrayList<>(relocations.size());

        for (Map.Entry<String, String> stringStringEntry : relocations.entrySet()) {
            c.add(new Relocation(stringStringEntry.getKey(), stringStringEntry.getValue()));
        }

        this.remapper = new RelocatingRemapper(c);
    }

    public void run() throws IOException {
        if (this.used.getAndSet(true)) {
            throw new IllegalStateException("#run has already been called on this instance");
        } else {
            JarOutputStream out = new JarOutputStream(new BufferedOutputStream(Files.newOutputStream(this.output.toPath())));

            try {
                JarFile in = new JarFile(this.input);

                try {
                    JarRelocatorTask task = new JarRelocatorTask(this.remapper, out, in);
                    task.processEntries();
                } catch (Throwable var7) {
                    try {
                        in.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }

                    throw var7;
                }

                in.close();
            } catch (Throwable var8) {
                try {
                    out.close();
                } catch (Throwable var5) {
                    var8.addSuppressed(var5);
                }

                throw var8;
            }

            out.close();
        }
    }
}
