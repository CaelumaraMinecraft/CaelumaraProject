package net.aurika.util.file.walker.visitors;

import net.aurika.util.file.walker.FileTreeWalker;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CollectorPathVisitor implements PathVisitor {

  private final @NotNull Path root;
  private final @NotNull PathVisitor visitor;
  private final @NotNull List<Path> list;

  public CollectorPathVisitor(@NotNull Path root, @NotNull PathVisitor visitor) {
    Validate.Arg.notNull(root, "root");
    Validate.Arg.notNull(visitor, "visitor");
    this.root = root;
    this.visitor = visitor;
    this.list = new ArrayList<>();
  }

  public @NotNull Path root() {
    return root;
  }

  public @NotNull PathVisitor visitor() {
    return visitor;
  }

  public @NotNull List<Path> getList() {
    return list;
  }

  public @NotNull FileVisitResult onVisit(@NotNull PathVisit visit) {
    Validate.Arg.notNull(visit, "visit");
    FileVisitResult result = this.visitor.onVisit(visit);
    switch (result) {
      case CONTINUE:
      case SKIP_SIBLINGS:
        list.add(visit.path());
      default:
        return result;
    }
  }

  public @NotNull List<Path> getFiles() {
    FileTreeWalker.walkFileTree(root, new HashSet<>(), Integer.MAX_VALUE, this);
    return list;
  }

}
