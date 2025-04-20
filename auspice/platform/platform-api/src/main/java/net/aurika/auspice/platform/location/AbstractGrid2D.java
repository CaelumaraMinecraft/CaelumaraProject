package net.aurika.auspice.platform.location;

import net.aurika.common.data.string.DataStringRepresentation;
import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AbstractGrid2D implements Grid2D, DataStringRepresentation {

  private final int x;
  private final int z;
  public static final int CHUNK_SHIFTS = 4;
  public static final int CHUNK_SHIFTS_Y = 8;

  public AbstractGrid2D(int x, int z) {
    this.x = x;
    this.z = z;
  }

  @Override
  public int gridX() {
    return this.x;
  }

  @Override
  public int gridZ() {
    return this.z;
  }

  @NotNull
  public String asDataString() {
    Object[] var1 = new Object[]{this.gridX(), this.gridZ()};
    String var10000 = CommaDataSplitStrategy.toString(var1);
    Objects.requireNonNull(var10000);
    return var10000;
  }

  public int hashCode() {
    return this.gridZ() << 16 ^ this.gridX();
  }

  public boolean equals(@Nullable Object obj) {
    Grid2D var10000 = obj instanceof Grid2D ? (Grid2D) obj : null;
    if ((obj instanceof Grid2D ? (Grid2D) obj : null) == null) {
      return false;
    } else {
      Grid2D other = var10000;
      return this.gridX() == other.gridX() && this.gridZ() == other.gridZ();
    }
  }

  @NotNull
  public String toString() {
    return "BlockVector2(" + this.gridX() + ", " + this.gridZ() + ')';
  }

  @NotNull
  public static AbstractGrid2D of(@NotNull Grid2D other) {
    return of(other.gridX(), other.gridZ());
  }

  @NotNull
  public static AbstractGrid2D of(int x, int z) {
    return new AbstractGrid2D(x, z);
  }

  @NotNull
  public static AbstractGrid2D fromString(@NotNull String str) {
    Objects.requireNonNull(str);
    CommaDataSplitStrategy $this$fromString_u24lambda_u240 = new CommaDataSplitStrategy(str, 2);
    return new AbstractGrid2D($this$fromString_u24lambda_u240.nextInt(), $this$fromString_u24lambda_u240.nextInt());
  }

}
