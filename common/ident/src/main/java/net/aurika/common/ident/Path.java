package net.aurika.common.ident;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public interface Path {

  static @NotNull Path path(@NotNull String pathString, char separator, @NotNull String allowedEntryChars) {
    @Nullable String problem = checkPathStringProblem(pathString, separator, allowedEntryChars);
    if (problem != null) {
      throw new IllegalArgumentException("Path string ' " + pathString + " ' has problem: " + problem);
    }
    return new PathImpl(Util.splitArray(pathString, separator, true));
  }

  static @NotNull Path path(@NotNull String @NotNull [] pathArray, @NotNull String allowedEntryChars) {
    @Nullable String problem = checkPathArrayProblem(pathArray, allowedEntryChars);
    if (problem != null) {
      throw new IllegalArgumentException("Path string ' " + Arrays.toString(pathArray) + " ' has problem: " + problem);
    }
    return new PathImpl(pathArray);
  }

  static @NotNull Path pathUnchecked(@NotNull String pathString, char separator) {
    Validate.Arg.notNull(pathString, "pathString");
    return new PathImpl(Util.splitArray(pathString, separator));
  }

  static @NotNull Path pathUnchecked(@NotNull String @NotNull [] pathArray) {
    Validate.Arg.notNull(pathArray, "pathArray");
    return new PathImpl(pathArray);
  }

  /**
   * @return the problem
   */
  static @Nullable String checkPathStringProblem(@NotNull String pathString, char separator, @NotNull String allowedEntryChars) {
    Validate.Arg.notNull(pathString, "pathString");
    Validate.Arg.notNull(allowedEntryChars, "allowedEntryChars");
    if (pathString.isEmpty()) {
      return "empty";
    }
    if (pathString.charAt(0) == separator) {
      return "separator is at head";
    }
    // 检查两个相邻的分隔符
    int adjoiningSeparator = checkAdjoiningSeparator(pathString, separator);
    if (adjoiningSeparator != -1) {
      return "two adjoining separators at index " + adjoiningSeparator;
    }
    int secProblem = checkPathStringAliases(allowedEntryChars, separator, allowedEntryChars);
    if (secProblem != -1) {
      return "unexpected section char '" + pathString.charAt(secProblem) + "' at index " + secProblem;
    }

    // 若没问题了, 返回 null
    return null;
  }

  static @Nullable String checkPathArrayProblem(@NotNull String @NotNull [] pathArray, @NotNull String allowedEntryChars) {
    Validate.Arg.notNull(pathArray, "pathArray");
    Validate.Arg.notNull(allowedEntryChars, "allowedEntryChars");
    if (pathArray.length == 0) {
      return "empty";
    }

    for (int i = 0; i < pathArray.length; i++) {
      String entry = pathArray[i];
      @Nullable String entryProblem = checkPathEntryProblem(entry, allowedEntryChars);
      if (entryProblem != null) {
        return "problem at the entry at index " + i + ": " + entryProblem;
      }
    }

    return null;
  }

  static @Nullable String checkPathEntryProblem(@NotNull String pathEntry, @NotNull String allowedSectionChars) {
    Validate.Arg.notNull(pathEntry, "pathEntry");
    Validate.Arg.notNull(allowedSectionChars, "allowedSectionChars");
    if (pathEntry.isEmpty()) {
      return "empty";
    }
    for (int i = 0; i < pathEntry.length(); i++) {
      char c = pathEntry.charAt(i);
      if (allowedSectionChars.indexOf(c) == -1) {
        return "unexpected character '" + pathEntry.charAt(i) + "' at path entry index " + i;
      }
    }
    return null;
  }

  static int checkPathEntryAliases(@NotNull String pathEntry, @NotNull String allowedSectionChars) {
    Validate.Arg.notNull(pathEntry, "pathEntry");
    Validate.Arg.notNull(allowedSectionChars, "allowedSectionChars");
    char[] entryChars = pathEntry.toCharArray();
    for (int i = 0; i < entryChars.length; i++) {
      char entryChar = entryChars[i];
      if (allowedSectionChars.indexOf(entryChar) == -1) { // 不是允许的字符
        return i;
      }
    }
    return -1;
  }

  static int checkPathStringAliases(@NotNull String pathString, char separator, @NotNull String allowedEntryChars) {
    Validate.Arg.notNull(pathString, "pathString");
    Validate.Arg.notNull(allowedEntryChars, "allowedEntryChars");
    int k = 0;
    String[] sections = Util.splitArray(allowedEntryChars, separator, false);
    for (int si = 0; si < sections.length; si++) {
      String section = sections[si];
      char[] sectionChars = section.toCharArray();
      for (int ci = 0; ci < sectionChars.length; ci++) {
        char secChar = sectionChars[ci];
        if (allowedEntryChars.indexOf(secChar) == -1) { // 不是允许的字符
          return k;
        }
        k++; // k++
      }
      k++; // 别忘了分隔符
    }
    return -1;
  }

  /**
   * 检查字符串中是否有两个在一起的分隔符, 若有, 返回两个分隔符所在的位置, 否则返回 {@code -1}.
   *
   * @param pathString 要检查的字符串
   * @param separator  分隔符
   * @return 相邻分隔符的位置
   */
  static int checkAdjoiningSeparator(@NotNull String pathString, char separator) {
    Validate.Arg.notNull(pathString, "pathString");
    boolean prevIsSep = false;  // at index -1?
    for (int i = 0; i < pathString.length(); i++) {
      char c = pathString.charAt(i);
      boolean currentIsSep = c == separator;
      if (prevIsSep && currentIsSep) {
        return i - 1;
      }
      prevIsSep = currentIsSep;
    }
    return -1;
  }

  static @NotNull Path appendUnchecked(@NotNull Path original, @NotNull String section) {
    Validate.Arg.notNull(original, "original");
    Validate.Arg.notNull(section, "section");
    String[] oldArr = original.asArray();
    String[] newArr = new String[oldArr.length + 1];
    System.arraycopy(oldArr, 0, newArr, 0, oldArr.length);
    newArr[oldArr.length] = section;
    return new PathImpl(newArr);
  }

  @NotNull String entryAt(int index) throws IndexOutOfBoundsException;

  @NotNull String @NotNull [] asArray();

  @NotNull String asString(char separator);

  int hashCode();

}

final class PathImpl implements Path {

  private final @NotNull String @NotNull [] value;

  PathImpl(@NotNull String @NotNull [] value) {
    Validate.Arg.nonNullArray(value, "value");
    this.value = value.clone();
  }

  @Override
  public @NotNull String entryAt(int index) throws IndexOutOfBoundsException {
    return value[index];
  }

  @Override
  public @NotNull String @NotNull [] asArray() {
    return value.clone();
  }

  @Override
  public @NotNull String asString(char separator) {
    return String.join(String.valueOf(separator), value);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(value);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Path)) return false;
    Path path = (Path) o;
    return Objects.deepEquals(value, path.asArray());
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return Path.class.getSimpleName() + Arrays.toString(value);
  }

}
