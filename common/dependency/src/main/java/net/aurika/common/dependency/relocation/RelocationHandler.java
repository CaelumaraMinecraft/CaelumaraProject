//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.aurika.common.dependency.relocation;

import net.aurika.common.dependency.DependencyManager;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelocationHandler {
    private static final String JAR_RELOCATOR_CLASS = JarRelocator.class.getName();
    private static final String JAR_RELOCATOR_RUN_METHOD = "run";

    public RelocationHandler(DependencyManager dependencyManager) {
    }

    public void remap(Path input, Path output, List<SimpleRelocation> relocations) throws Exception {
        Map<String, String> mappings = new HashMap<>();

        for (SimpleRelocation relocation : relocations) {
            mappings.put(relocation.getPattern(), relocation.getRelocatedPattern());
        }

        JarRelocator instance = new JarRelocator(input.toFile(), output.toFile(), mappings);
        instance.run();
    }
}
