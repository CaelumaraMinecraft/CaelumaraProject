package net.aurika.util.file.walker.visitors;

import net.aurika.util.file.walker.FileTreeWalker;
import net.aurika.util.file.walker.FileWalkerController;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public interface PathVisitor {

  @NotNull FileVisitResult onVisit(@NotNull PathVisit visit);

  default @NotNull List<Path> collect(@NotNull Path root) {
    Validate.Arg.notNull(root, "root");
    return (new CollectorPathVisitor(root, this)).getFiles();
  }

  default @NotNull Stream<PathVisit> stream(@NotNull Path root) {
    Validate.Arg.notNull(root, "root");
    AtomicReference<FileWalkerController> controller = new AtomicReference<>();
    Stream<PathVisit> var10000 = null;
    try {
      var10000 = FileTreeWalker.walk(root, new HashSet<>(), Integer.MAX_VALUE, controller).filter(visit -> {
        Objects.requireNonNull(visit);
        FileVisitResult result = this.onVisit(visit);
        controller.get().processResult(result);

        return switch (result) {
          case CONTINUE, SKIP_SIBLINGS -> true;
          default -> false;
        };
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Validate.Expr.notNull(var10000, "filter(...)");
    return var10000;
  }

}
