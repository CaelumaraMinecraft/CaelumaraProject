package net.aurika.util.string;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import net.aurika.util.unsafe.fn.Fn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PrettyStringContext {

  @NotNull
  private final StringBuilder string;
  private final int nestLevel;

  public PrettyStringContext(@NotNull StringBuilder string, int nestLevel) {
    Objects.requireNonNull(string, "string");
    this.string = string;
    this.nestLevel = nestLevel;
  }

  @NotNull
  public StringBuilder getString() {
    return this.string;
  }

  public int getNestLevel() {
    return this.nestLevel;
  }

  public int getLine() {
    String var10000 = this.string.toString();
    Intrinsics.checkNotNullExpressionValue(var10000, "toString(...)");
    char[] var1 = new char[]{'\n'};
    return (StringsKt.split(var10000, var1, false, -1)).size();
  }

  public int getColumn() {
    String var10000 = this.string.toString();
    Intrinsics.checkNotNullExpressionValue(var10000, "toString(...)");
    return StringsKt.substringAfterLast(var10000, '\n', var10000).length();
  }

  public void delegate(@Nullable Object obj) {
    if (obj == null) {
      this.string.append("null");
    } else {
      PrettyString specialized = PrettyStringFactory.findSpecialized(obj.getClass());
      if (specialized != null) {
        PrettyStringContext newContext = new PrettyStringContext(this.string, this.nestLevel + 1);
        Object var10001 = Fn.cast(obj);
        Intrinsics.checkNotNullExpressionValue(var10001, "cast(...)");
        specialized.toPrettyString(var10001, newContext);
      } else {
        final String space = StringsKt.repeat(" ", (this.nestLevel + 1) * 2);
        StringBuilder var10000 = this.string.append(obj);
        Intrinsics.checkNotNullExpressionValue(var10000, "append(...)");
        CharSequence var6 = var10000;
        char[] var4 = new char[]{'\n'};

        final class NamelessClass_1 extends Lambda<CharSequence> implements Function1<String, CharSequence> {

          NamelessClass_1() {
            super(1);
          }

          public CharSequence invoke(String it) {
            Objects.requireNonNull(it, "it");
            return space + '\n';
          }

        }

        CollectionsKt.joinToString(
            StringsKt.split(var6, var4, false, -1), ", ", "", "", -1, "...", (new NamelessClass_1()));
      }
    }
  }

}
