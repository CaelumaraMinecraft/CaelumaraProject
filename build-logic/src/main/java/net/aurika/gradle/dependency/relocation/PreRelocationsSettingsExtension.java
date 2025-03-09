package net.aurika.gradle.dependency.relocation;

import org.gradle.api.Action;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.plugins.ExtensionAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class PreRelocationsSettingsExtension {
    private @NotNull File relocatedFolder;

    public PreRelocationsSettingsExtension(@NotNull File relocatedFolder) {
        this.relocatedFolder = relocatedFolder;
    }

    public void addPreRelocatedDependency(@NotNull Dependency dependency, @NotNull Action<? super PreRelocationsExtension> action) {
        @Nullable PreRelocationsExtension depExt = ((ExtensionAware) dependency).getExtensions().findByType(PreRelocationsExtension.class);
        if (depExt == null) {
            depExt = ((ExtensionAware) dependency).getExtensions().create(
                    PreRelocationsExtension.class,
                    PreRelocationsExtension.EXTENSION_NAME,
                    PreRelocationsExtension.class,
                    new Object[]{this}
            );
        }
        action.execute(depExt);
    }

    public @NotNull File getRelocatedFolder() {
        return relocatedFolder;
    }

    public void setRelocatedFolder(@NotNull File relocatedFolder) {
        this.relocatedFolder = relocatedFolder;
    }
}
