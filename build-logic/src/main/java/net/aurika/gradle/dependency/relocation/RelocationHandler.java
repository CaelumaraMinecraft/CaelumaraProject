package net.aurika.gradle.dependency.relocation;

import net.aurika.gradle.dependency.relocation.relocator.JarRelocator;
import net.aurika.gradle.dependency.relocation.relocator.SimpleRelocation;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RelocationHandler {

    public static void remap(@NotNull Path input, @NotNull Path output, @NotNull List<SimpleRelocation> relocations) {
        Map<String, String> mappings = new HashMap<>();

        for (SimpleRelocation relocation : relocations) {
            mappings.put(relocation.getPattern(), relocation.getRelocatedPattern());
        }

        File outputFile = output.toFile();

//        System.out.println("Can write: " + outputFile.canWrite());
//        System.out.println("Exists: " + outputFile.exists());
        if (!outputFile.exists()) {
            try {

                outputFile.createNewFile();

                JarRelocator instance = new JarRelocator(input.toFile(), output.toFile(), mappings);


                instance.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
