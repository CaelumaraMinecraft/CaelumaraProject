package net.aurika.auspice.platform.location;

import net.aurika.common.data.DataStringRepresentation;
import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Block2 implements Block2D, DataStringRepresentation {

  private final int x;
  private final int z;
  public static final int CHUNK_SHIFTS = 4;
  public static final int CHUNK_SHIFTS_Y = 8;

  public Block2(int x, int z) {
    this.x = x;
    this.z = z;
  }

  @Override
  public int x() {
    return this.x;
  }

  @Override
  public int z() {
    return this.z;
  }

  @NotNull
  public String asDataString() {
    Object[] var1 = new Object[]{this.x(), this.z()};
    String var10000 = CommaDataSplitStrategy.toString(var1);
    Objects.requireNonNull(var10000);
    return var10000;
  }

  public int hashCode() {
    return this.z() << 16 ^ this.x();
  }

  public boolean equals(@Nullable Object obj) {
    Block2D var10000 = obj instanceof Block2D ? (Block2D) obj : null;
    if ((obj instanceof Block2D ? (Block2D) obj : null) == null) {
      return false;
    } else {
      Block2D other = var10000;
      return this.x() == other.x() && this.z() == other.z();
    }
  }

  @NotNull
  public String toString() {
    return "BlockVector2(" + this.x() + ", " + this.z() + ')';
  }

  @NotNull
  public static Block2 of(@NotNull Block2D other) {
    return of(other.x(), other.z());
  }

  @NotNull
  public static Block2 of(int x, int z) {
    return new Block2(x, z);
  }

  @NotNull
  public static Block2 fromString(@NotNull String str) {
    Objects.requireNonNull(str);
    CommaDataSplitStrategy $this$fromString_u24lambda_u240 = new CommaDataSplitStrategy(str, 2);
    return new Block2($this$fromString_u24lambda_u240.nextInt(), $this$fromString_u24lambda_u240.nextInt());
  }

}
