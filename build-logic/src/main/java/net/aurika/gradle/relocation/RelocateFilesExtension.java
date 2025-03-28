package net.aurika.gradle.relocation;

import org.gradle.api.Action;
import org.gradle.api.file.Directory;
import org.gradle.api.file.FileCollection;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;

public class RelocateFilesExtension {

  public static final String EXTENSION_NAME = "relocateFiles";

  @Inject
  public RelocateFilesExtension() { }

  public ConfigurableRelocatedFiles createRelocates(@NotNull FileCollection files, @NotNull Directory targetDirectory) {
    return new DefaultConfigurableRelocatedFiles(files, new ArrayList<>(), targetDirectory);
  }

  public void createRelocates(@NotNull FileCollection files, @NotNull Directory targetDirectory, @NotNull Action<? super ConfigurableRelocatedFiles> action) {
    action.execute(createRelocates(files, targetDirectory));
  }

//    public void createRelocates(@NotNull FileCollection files, @NotNull Directory targetDirectory, @NotNull Closure<? super ConfigurableRelocatedFiles> closure) {
//        ConfigurableRelocatedFiles relocates = createRelocates(files, targetDirectory);
//        closure.setDelegate(relocates);
//        closure.call();
//    }
}
