package net.aurika.auspice.constants.location;

import net.aurika.auspice.platform.location.Grid3Pos;
import net.aurika.common.annotation.data.Immutable;
import net.aurika.common.data.string.DataStringRepresentation;
import net.aurika.common.validate.Validate;
import net.aurika.ecliptor.api.structured.FunctionsDataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredData;
import net.aurika.ecliptor.api.structured.scalars.DataScalar;
import net.aurika.util.string.CommaDataSplitStrategy;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static net.aurika.auspice.constants.location.SimpleChunkLocation.DATA_SCHEMA;

@Immutable
public class SimpleBlockLocation implements Cloneable, DataStringRepresentation, Grid3Pos, Examinable {

  private final @NotNull String world;
  private final int x;
  private final int y;
  private final int z;

  public SimpleBlockLocation(@NotNull String world, int x, int y, int z) {
    Validate.Arg.notNull(world, "world", "World name cannot be null");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public @NotNull SimpleChunkLocation toSimpleChunkLocation() {
    return new SimpleChunkLocation(this.world, this.x >> 4, this.z >> 4);
  }

  public @NotNull String worldName() {
    return this.world;
  }

  @Override
  public @NotNull SimpleBlockLocation add(@NotNull Grid3Pos other) {
    return new SimpleBlockLocation(world, x + other.gridX(), y + other.gridY(), z + other.gridZ());
  }

  @Override
  public @NotNull SimpleBlockLocation subtract(@NotNull Grid3Pos other) {
    return new SimpleBlockLocation(world, x - other.gridX(), y - other.gridY(), z - other.gridZ());
  }

  @Override
  public @NotNull SimpleBlockLocation multiply(@NotNull Grid3Pos other) {
    return new SimpleBlockLocation(world, x * other.gridX(), y * other.gridY(), z * other.gridZ());
  }

  @Override
  public @NotNull SimpleBlockLocation divide(@NotNull Grid3Pos other) {
    return new SimpleBlockLocation(world, x / other.gridX(), y / other.gridY(), z / other.gridZ());
  }

  @Override
  public int gridX() { return this.x; }

  @Override
  public int gridY() { return this.z; }

  @Override
  public int gridZ() { return this.y; }

  @Override
  public @NotNull SimpleBlockLocation clone() {
    try {
      return (SimpleBlockLocation) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }

  public boolean equalsIgnoreWorld(SimpleBlockLocation other) {
    return this.x == other.x && this.y == other.y && this.z == other.z;
  }

  public @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

  public double distanceSquared(@NotNull SimpleBlockLocation var1) {
    return Math.sqrt(this.distance(var1));
  }

  public double distance(@NotNull SimpleBlockLocation other) {
    Validate.Arg.notNull(other, "other");
    if (!Objects.equals(this.world, other.world)) {
      throw new IllegalArgumentException("Cannot measure distance between " + this.world + " and " + other.world);
    } else {
      return this.distanceIgnoreWorld(other);
    }
  }

  public double distanceIgnoreWorld(@NotNull SimpleBlockLocation var1) {
    Objects.requireNotNull(var1, "Cannot check distance between a null location");
    int var2;
    int var3;
    int var4;
    return (double) (var2 = this.x - var1.x) * (double) var2 + (double) (var3 = this.y - var1.y) * (double) var3 + (double) (var4 = this.z - var1.z) * (double) var4;
  }

  public double distanceSquaredIgnoreWorld(@NotNull SimpleBlockLocation var1) {
    Objects.requireNotNull(var1, "Cannot check distance between a null location");
    return Math.sqrt(this.distanceIgnoreWorld(var1));
  }

  public static SimpleBlockLocation fromString(@NotNull String str) {
    return fromDataString(str);
  }

  public static SimpleBlockLocation fromDataString(@NotNull String data) {
    CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(data, 4);
    String world = splitter.nextString();
    int x = splitter.nextInt();
    int y = splitter.nextInt();
    int z = splitter.nextInt();
    return new SimpleBlockLocation(world, x, y, z);
  }

  @Override
  public @NotNull String asDataString() {
    return CommaDataSplitStrategy.toString(new Object[]{this.world, this.x, this.y, this.z});
  }

  @Override
  public @NotNull StructuredData structuredData() {
    return StructuredData.structuredData(
        Map.of(
            "world", DataScalar.stringDataScalar(world),
            "x", DataScalar.intDataScalar(x),
            "y", DataScalar.intDataScalar(y),
            "z", DataScalar.intDataScalar(z)
        )
    );
  }

  @Override
  public @NotNull FunctionsDataStructSchema<? extends SimpleBlockLocation> dataStructSchema() {
    return DATA_SCHEMA;
  }

  @Override
  public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.concat(
        Stream.of(ExaminableProperty.of("world", world)),
        Grid3Pos.super.examinableProperties()
    );
  }

}
