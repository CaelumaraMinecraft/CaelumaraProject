package net.aurika.common.key;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

final class Util {

  public static @NotNull String @NotNull [] splitArray(@NotNull String string, char separator) {
    return splitArray(string, separator, false);
  }

  /**
   * 用某个字符分割字符串.
   * <p>
   * 保留空字符串:
   * <p>
   * 分割一个值为 {@code "..123..789.."} 的字符串, 分隔符为 {@code '.'}.
   * <p>
   * 若保留空字符串, 则会返回一个长度为 7 的数组 {@code [, , 123, , 789, , ]}
   * <p>
   * 若不保留空字符串, 则返回长为 2 的数组 {@code [123, 789]}
   *
   * @param string          要分割的字符串
   * @param separator       分隔符
   * @param keepEmptyString 是否保留分割出来的空字符串
   * @return 分割后的字符串数组
   */
  public static @NotNull String @NotNull [] splitArray(@NotNull String string, char separator, boolean keepEmptyString) {
    Validate.Arg.notNull(string, "string");

    int length = string.length();
    if (length == 0) {  // ""
      return keepEmptyString ? new String[]{""} : new String[0];
    } else {
      ArrayList<String> list = new ArrayList<>();
      int index = 0;
      int var6 = 0;
      boolean var7 = false;
      boolean var8 = false;


      while (index < length) {
        if (string.charAt(index) == separator) {
          if (var7 || keepEmptyString) {
            list.add(string.substring(var6, index));
            var7 = false;
            var8 = true;
          }

          ++index;
          var6 = index;
        } else {
          var8 = false;
          var7 = true;
          ++index;
        }
      }

      if (var7 || keepEmptyString && var8) {
        list.add(string.substring(var6, index));
      }

      return list.toArray(new String[0]);
    }
  }

}
