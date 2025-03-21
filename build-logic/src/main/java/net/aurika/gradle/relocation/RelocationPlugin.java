package net.aurika.gradle.relocation;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class RelocationPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project project) {
        project.getExtensions().create(RelocateFilesExtension.class, RelocateFilesExtension.EXTENSION_NAME, RelocateFilesExtension.class);
    }
}
