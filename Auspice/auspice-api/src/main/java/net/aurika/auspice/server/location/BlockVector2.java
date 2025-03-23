package net.aurika.auspice.server.location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.ecliptor.object.DataStringRepresentation;
import net.aurika.auspice.utils.string.CommaDataSplitStrategy;

import java.util.Objects;

public class BlockVector2 implements BlockPoint2D, DataStringRepresentation {
    private final int x;
    private final int z;
    public static final int CHUNK_SHIFTS = 4;
    public static final int CHUNK_SHIFTS_Y = 8;

    public BlockVector2(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    @NotNull
    public String asDataString() {
        Object[] var1 = new Object[]{this.getX(), this.getZ()};
        String var10000 = CommaDataSplitStrategy.toString(var1);
        Objects.requireNonNull(var10000);
        return var10000;
    }

    public int hashCode() {
        return this.getZ() << 16 ^ this.getX();
    }

    public boolean equals(@Nullable Object obj) {
        BlockPoint2D var10000 = obj instanceof BlockPoint2D ? (BlockPoint2D) obj : null;
        if ((obj instanceof BlockPoint2D ? (BlockPoint2D) obj : null) == null) {
            return false;
        } else {
            BlockPoint2D other = var10000;
            return this.getX() == other.getX() && this.getZ() == other.getZ();
        }
    }

    @NotNull
    public String toString() {
        return "BlockVector2(" + this.getX() + ", " + this.getZ() + ')';
    }

    @NotNull
    public static BlockVector2 of(@NotNull BlockPoint2D other) {
        return of(other.getX(), other.getZ());
    }

    @NotNull
    public static BlockVector2 of(int x, int z) {
        return new BlockVector2(x, z);
    }

    @NotNull
    public static BlockVector2 fromString(@NotNull String str) {
        Objects.requireNonNull(str);
        CommaDataSplitStrategy $this$fromString_u24lambda_u240 = new CommaDataSplitStrategy(str, 2);
        return new BlockVector2($this$fromString_u24lambda_u240.nextInt(), $this$fromString_u24lambda_u240.nextInt());
    }

}

