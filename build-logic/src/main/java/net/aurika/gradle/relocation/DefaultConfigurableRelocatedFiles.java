package net.aurika.gradle.relocation;

import net.aurika.gradle.relocation.relocation.SimpleRelocation;
import org.gradle.api.file.Directory;
import org.gradle.api.file.FileCollection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DefaultConfigurableRelocatedFiles extends DefaultRelocatedFiles implements ConfigurableRelocatedFiles {
    public DefaultConfigurableRelocatedFiles(@NotNull FileCollection files, @NotNull List<SimpleRelocation> relocations, @NotNull Directory targetDirectory) {
        super(files, relocations, targetDirectory);
    }

    @Override
    public void addRelocation(@NotNull SimpleRelocation relocation) {
        relocations.add(relocation);
    }

    @Override
    public void setTargetDirectory(@NotNull Directory targetDirectory) {
        this.targetDirectory = targetDirectory;
    }
}
