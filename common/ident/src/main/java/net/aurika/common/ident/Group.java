package net.aurika.common.ident;

import net.aurika.common.data.string.DataStringRepresentation;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static net.aurika.common.ident.Path.checkPathArrayProblem;
import static net.aurika.common.ident.Path.checkPathStringProblem;

public interface Group extends PathAware, GroupAware, DataStringRepresentation {

  String ALLOWED_SECTION_CHARS = "abcdefghijklmnopqrstuvwxyz";
  char SEPARATOR = '.';

  static @NotNull Group group(@KeyPatterns.Group final @NotNull String groupString) {
    Validate.Arg.notNull(groupString, "groupString");
    @Nullable String problem = checkPathStringProblem(groupString, SEPARATOR, ALLOWED_SECTION_CHARS);
    if (problem != null) {
      throw new IllegalArgumentException("Group path string ' " + groupString + " ' has problem: " + problem);
    }
    return new GroupImpl(Path.pathUnchecked(groupString, SEPARATOR));
  }

  static @NotNull Group group(@NotNull String @NotNull [] groupArray) {
    Validate.Arg.notNull(groupArray, "groupArray");
    @Nullable String problem = checkPathArrayProblem(groupArray, ALLOWED_SECTION_CHARS);
    if (problem != null) {
      throw new IllegalArgumentException(
          "Group path array " + Arrays.toString(groupArray) + " has problem: " + problem);
    }
    return new GroupImpl(Path.pathUnchecked(groupArray));
  }

  @Override
  @NotNull Path path();

  @Override
  default @NotNull Group group() { return this; }

  @Override
  @NotNull String asDataString();

  int hashCode();

}

class GroupImpl implements Group {

  private final @NotNull Path path;

  GroupImpl(@NotNull Path path) {
    Validate.Arg.notNull(path, "path");
    this.path = path;
  }

  @Override
  public @NotNull Path path() {
    return path;
  }

  @Override
  public @NotNull Group group() {
    return Group.super.group();
  }

  @Override
  public @NotNull String asDataString() {
    return path.asString(SEPARATOR);
  }

  @Override
  public int hashCode() {
    return path.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Group && path.equals(((Group) obj).path());
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return Group.class.getSimpleName() + "[" + path + "]";
  }

}
