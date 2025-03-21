package net.aurika.gradle.relocation;

import net.aurika.gradle.relocation.relocation.SimpleRelocation;
import org.gradle.api.file.Directory;
import org.jetbrains.annotations.NotNull;

public interface ConfigurableRelocatedFiles extends RelocatedFiles {
    default void relocate(@NotNull String from, @NotNull String to) {
        this.addRelocation(new SimpleRelocation(from, to));
    }

    void addRelocation(@NotNull SimpleRelocation relocation);

    void setTargetDirectory(@NotNull Directory targetDirectory);
}
