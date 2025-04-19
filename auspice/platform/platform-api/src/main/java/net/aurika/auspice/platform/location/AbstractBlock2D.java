package net.aurika.auspice.platform.location;

import net.aurika.common.data.string.DataStringRepresentation;
import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AbstractBlock2D implements Block2D, DataStringRepresentation {

  private final int x;
  private final int z;
  public static final int CHUNK_SHIFTS = 4;
  public static final int CHUNK_SHIFTS_Y = 8;

  public AbstractBlock2D(int x, int z) {
    this.x = x;
    this.z = z;
  }

  @Override
  public int intX() {
    return this.x;
  }

  @Override
  public int intZ() {
    return this.z;
  }

  @NotNull
  public String asDataString() {
    Object[] var1 = new Object[]{this.intX(), this.intZ()};
    String var10000 = CommaDataSplitStrategy.toString(var1);
    Objects.requireNonNull(var10000);
    return var10000;
  }

  public int hashCode() {
    return this.intZ() << 16 ^ this.intX();
  }

  public boolean equals(@Nullable Object obj) {
    Block2D var10000 = obj instanceof Block2D ? (Block2D) obj : null;
    if ((obj instanceof Block2D ? (Block2D) obj : null) == null) {
      return false;
    } else {
      Block2D other = var10000;
      return this.intX() == other.intX() && this.intZ() == other.intZ();
    }
  }

  @NotNull
  public String toString() {
    return "BlockVector2(" + this.intX() + ", " + this.intZ() + ')';
  }

  @NotNull
  public static AbstractBlock2D of(@NotNull Block2D other) {
    return of(other.intX(), other.intZ());
  }

  @NotNull
  public static AbstractBlock2D of(int x, int z) {
    return new AbstractBlock2D(x, z);
  }

  @NotNull
  public static AbstractBlock2D fromString(@NotNull String str) {
    Objects.requireNonNull(str);
    CommaDataSplitStrategy $this$fromString_u24lambda_u240 = new CommaDataSplitStrategy(str, 2);
    return new AbstractBlock2D($this$fromString_u24lambda_u240.nextInt(), $this$fromString_u24lambda_u240.nextInt());
  }

}
