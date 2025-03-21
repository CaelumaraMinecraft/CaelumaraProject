package net.aurika.gradle.relocation.relocation;

import org.jetbrains.annotations.NotNull;

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
    private final @NotNull File input;
    private final @NotNull File output;
    private final @NotNull RelocatingRemapper remapper;
    private final @NotNull AtomicBoolean used = new AtomicBoolean(false);

    public JarRelocator(@NotNull File input, @NotNull File output, Collection<Relocation> relocations) {
        this.input = input;
        this.output = output;
        this.remapper = new RelocatingRemapper(relocations);
    }

    public JarRelocator(@NotNull File input, @NotNull File output, @NotNull Map<String, String> relocations) {
        this.input = input;
        this.output = output;
        Collection<Relocation> c = new ArrayList<>(relocations.size());

        for (Map.Entry<String, String> entry : relocations.entrySet()) {
            c.add(new Relocation(entry.getKey(), entry.getValue()));
        }

        this.remapper = new RelocatingRemapper(c);
    }

    public void run() throws IOException {
        if (this.used.getAndSet(true)) {
            throw new IllegalStateException("#run has already been called on this instance");
        } else {
            try (JarOutputStream out = new JarOutputStream(new BufferedOutputStream(Files.newOutputStream(this.output.toPath())))) {
                try (JarFile in = new JarFile(this.input)) {
                    JarRelocatorTask task = new JarRelocatorTask(this.remapper, out, in);
                    task.processEntries();
                }
            }
        }
    }
}
