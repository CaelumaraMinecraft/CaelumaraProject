package net.aurika.common.key;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Key extends Grouped, PathAware {

  char SEPARATOR = ':';
  char PATH_SEPARATOR = '/';
  String ALLOWED_PATH_CHARS = "abcdefghijklmnopqrstuvwxyz_-";

  /**
   * Create a {@linkplain Key} from the key string.
   * For example: {@code "net.aurika:messages/found/user"} {@code "org.city:city/create"}
   *
   * @param keyString The full ident string
   * @throws IllegalArgumentException When the input string is not valid.
   */
  static @NotNull Key key(@NotNull String keyString) {
    int sepIndex = keyString.indexOf(PATH_SEPARATOR);
    if (sepIndex == -1) {
      throw new IllegalArgumentException(keyString + " is not a valid key, cannot find separator '" + SEPARATOR + "'");
    }
    return key(keyString.substring(0, sepIndex), keyString.substring(sepIndex + 1));
  }

  static @NotNull Key key(@NotNull String groupString, @NotNull String pathString) {
    Validate.Arg.notNull(groupString, "groupString");
    Validate.Arg.notNull(pathString, "pathString");
    return new KeyImpl(Group.group(groupString), Path.path(pathString, SEPARATOR, ALLOWED_PATH_CHARS));
  }

  static @NotNull Key key(@NotNull String @NotNull [] groupArray, @NotNull String @NotNull [] pathArray) {
    Validate.Arg.notNull(groupArray, "groupArray");
    Validate.Arg.notNull(pathArray, "pathArray");
    return new KeyImpl(Group.group(groupArray), Path.path(pathArray, ALLOWED_PATH_CHARS));
  }

  static @NotNull Key key(@NotNull Group group, @NotNull Path path) {
    Validate.Arg.notNull(group, "group");
    Validate.Arg.notNull(path, "path");
    return new KeyImpl(group, path);
  }

  @Override
  @NotNull Group group();

  @Override
  @NotNull Path path();

  @NotNull String asString();

}

class KeyImpl implements Key {

  private final @NotNull Group group;
  private final @NotNull Path path;

  KeyImpl(
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
  public @NotNull String asString() {
    return group.asString() + SEPARATOR + path.asString(PATH_SEPARATOR);
  }

  @Override
  public int hashCode() {
    int result = this.group.hashCode();
    result = (31 * result) + this.path.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Key)) return false;
    Key key = (Key) obj;
    return Objects.equals(group, key.group()) && Objects.equals(path, key.path());
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return Key.class.getSimpleName() + "[group=" + group + ", path=" + path + "]";
  }

}
