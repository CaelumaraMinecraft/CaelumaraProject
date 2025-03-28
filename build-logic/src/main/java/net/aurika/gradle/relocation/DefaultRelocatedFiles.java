package net.aurika.gradle.relocation;

import net.aurika.gradle.dependency.relocation.RelocationHandler;
import net.aurika.gradle.relocation.relocation.SimpleRelocation;
import org.gradle.api.file.Directory;
import org.gradle.api.file.FileCollection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class DefaultRelocatedFiles implements RelocatedFiles {

  protected @NotNull FileCollection files;
  protected @NotNull List<SimpleRelocation> relocations;
  protected @NotNull Directory targetDirectory;

  public DefaultRelocatedFiles(@NotNull FileCollection files, @NotNull List<SimpleRelocation> relocations, @NotNull Directory targetDirectory) {
    this.files = files;
    this.relocations = relocations;
    this.targetDirectory = targetDirectory;
  }

  @Override
  public @NotNull FileCollection getFiles() {
    return files;
  }

  @Override
  public @NotNull List<SimpleRelocation> getRelocations() {
    return relocations;
  }

  @Override
  public @NotNull Directory getTargetDirectory() {
    return targetDirectory;
  }

  @Override
  public void relocate() {
    Objects.requireNonNull(files);
    Objects.requireNonNull(relocations);
    Objects.requireNonNull(targetDirectory);

    for (File input : files) {
      String outputFileName = "relocated-" + input.getName();
      File outputFile = targetDirectory.file(outputFileName).getAsFile();

      if (!outputFile.exists()) {

        System.out.println(outputFile.getAbsolutePath());

        RelocationHandler.remap(input.toPath(), outputFile.toPath(), relocations);
      }
    }
  }

}
