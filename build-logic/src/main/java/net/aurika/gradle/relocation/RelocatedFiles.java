package net.aurika.gradle.relocation;

import net.aurika.gradle.relocation.relocation.SimpleRelocation;
import org.gradle.api.file.Directory;
import org.gradle.api.file.FileCollection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface RelocatedFiles {
    /**
     * Gets the files that to relocate.
     *
     * @return the files
     */
    @NotNull FileCollection getFiles();

    /**
     * Gets the relocation rules.
     *
     * @return the relocation rules
     */
    @NotNull List<SimpleRelocation> getRelocations();

    @NotNull Directory getTargetDirectory();

    void relocate();
}
