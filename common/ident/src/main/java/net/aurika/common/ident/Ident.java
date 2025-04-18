package net.aurika.common.ident;

import net.aurika.common.data.string.DataStringRepresentation;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Ident extends Grouped, PathAware, DataStringRepresentation, IdentAware {

  char SEPARATOR = ':';
  char PATH_SEPARATOR = '/';
  String ALLOWED_PATH_CHARS = "abcdefghijklmnopqrstuvwxyz_-";

  /**
   * Create a {@linkplain Ident} from the ident string.
   * For example: {@code "net.aurika:messages/found/user"} {@code "org.city:city/create"}
   *
   * @param identString The full ident string
   * @throws IllegalArgumentException When the input string is not valid.
   */
  @SuppressWarnings("PatternValidation")
  static @NotNull Ident ident(@KeyPatterns.Ident final @NotNull String identString) {
    int sepIndex = identString.indexOf(SEPARATOR);
    if (sepIndex == -1) {
      throw new IllegalArgumentException(
          "'" + identString + "' is not a valid key, cannot find separator '" + SEPARATOR + "'");
    }
    return ident(identString.substring(0, sepIndex), identString.substring(sepIndex + 1));
  }

  static @NotNull Ident ident(@KeyPatterns.Group final @NotNull String groupString, @KeyPatterns.IdentPath final @NotNull String pathString) {
    Validate.Arg.notNull(groupString, "groupString");
    Validate.Arg.notNull(pathString, "pathString");
    return new IdentImpl(Group.group(groupString), Path.path(pathString, SEPARATOR, ALLOWED_PATH_CHARS));
  }

  static @NotNull Ident ident(@NotNull String @NotNull [] groupArray, @NotNull String @NotNull [] pathArray) {
    Validate.Arg.notNull(groupArray, "groupArray");
    Validate.Arg.notNull(pathArray, "pathArray");
    return new IdentImpl(Group.group(groupArray), Path.path(pathArray, ALLOWED_PATH_CHARS));
  }

  static @NotNull Ident ident(@NotNull Group group, @NotNull Path path) {
    Validate.Arg.notNull(group, "group");
    Validate.Arg.notNull(path, "path");
    return new IdentImpl(group, path);
  }

  @Override
  @NotNull Group group();

  @Override
  @NotNull Path path();

  @Override
  @NotNull String asDataString();

  @Override
  default @NotNull Ident ident() { return this; }

}

class IdentImpl implements Ident {

  private final @NotNull Group group;
  private final @NotNull Path path;

  IdentImpl(
      @NotNull Group group,
      @NotNull Path path
  ) {
    Validate.Arg.notNull(group, "group");
    Validate.Arg.notNull(path, "path");
    this.group = group;
    this.path = path;
  }

  @Override
  public @NotNull Group group() {
    return group;
  }

  @Override
  public @NotNull Path path() {
    return path;
  }

  @Override
  public @NotNull String asDataString() {
    return group.asDataString() + SEPARATOR + path.asString(PATH_SEPARATOR);
  }

  @Override
  public int hashCode() {
    int result = this.group.hashCode();
    result = (31 * result) + this.path.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Ident)) return false;
    Ident ident = (Ident) obj;
    return Objects.equals(group, ident.group()) && Objects.equals(path, ident.path());
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return Ident.class.getSimpleName() + "[group=" + group + ", path=" + path + "]";
  }

}
