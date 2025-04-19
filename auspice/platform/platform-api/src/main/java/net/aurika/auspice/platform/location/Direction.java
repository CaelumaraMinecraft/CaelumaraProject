package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

public enum Direction implements Directional {

  NORTH(0, 0, -1, Type.CARDINAL),
  EAST(1, 0, 0, Type.CARDINAL),
  SOUTH(0, 0, 1, Type.CARDINAL),
  WEST(-1, 0, 0, Type.CARDINAL),
  UP(0, 1, 0, Type.VERTICAL),
  DOWN(0, -1, 0, Type.VERTICAL),
  NORTH_EAST(NORTH, EAST, Type.ORDINAL),
  NORTH_WEST(NORTH, WEST, Type.ORDINAL),
  SOUTH_EAST(SOUTH, EAST, Type.ORDINAL),
  SOUTH_WEST(SOUTH, WEST, Type.ORDINAL),
  WEST_NORTH_WEST(WEST, NORTH_WEST, Type.SECONDARY_ORDINAL),
  NORTH_NORTH_WEST(NORTH, NORTH_WEST, Type.SECONDARY_ORDINAL),
  NORTH_NORTH_EAST(NORTH, NORTH_EAST, Type.SECONDARY_ORDINAL),
  EAST_NORTH_EAST(EAST, NORTH_EAST, Type.SECONDARY_ORDINAL),
  EAST_SOUTH_EAST(EAST, SOUTH_EAST, Type.SECONDARY_ORDINAL),
  SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST, Type.SECONDARY_ORDINAL),
  SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST, Type.SECONDARY_ORDINAL),
  WEST_SOUTH_WEST(WEST, SOUTH_WEST, Type.SECONDARY_ORDINAL);

  private final @NotNull Type type;
  private final int x;
  private final int y;
  private final int z;
  private final float pitch;
  private final float yaw;

  public @NotNull Type getType() {
    return this.type;
  }

  public int modX() {
    return this.x;
  }

  public int modY() {
    return this.y;
  }

  public int modZ() {
    return this.z;
  }

  @Override
  public float yaw() {
    return this.yaw;
  }

  @Override
  public float pitch() {
    return this.pitch;
  }

  Direction(@NotNull Direction face1, @NotNull Direction face2, Type type) {
    this(face1.x + face2.x, face1.y + face2.y, face1.z + face2.z, type);
  }

  Direction(int modX, int modY, int modZ, @NotNull Type type) {
    Objects.requireNonNull(type, "type");
    this.x = modX;
    this.y = modY;
    this.z = modZ;
    this.type = type;
    Directional directional = LocationUtils.fromDirection(AbstractFloat3D.of(modX, modY, modZ));
    if (modX == 0 && modZ == 0) {
      this.yaw = 0.0F;
      this.pitch = directional.pitch();
    } else if (modY == 0) {
      this.yaw = (directional.yaw() + 180.0F) % (float) 360;
      this.pitch = 0.0F;
    } else {
      this.yaw = (directional.yaw() + 180.0F) % (float) 360;
      this.pitch = directional.pitch();
    }
  }

  public final boolean isCartesian() {
    switch (this) {
      case NORTH:
      case EAST:
      case SOUTH:
      case WEST:
      case UP:
      case DOWN:
        return true;
      default:
        return false;
    }
  }

  @NotNull
  public final Direction getOppositeFace() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case EAST:
        return WEST;
      case SOUTH:
        return NORTH;
      case WEST:
        return EAST;
      case UP:
        return DOWN;
      case DOWN:
        return UP;
      case NORTH_EAST:
        return SOUTH_WEST;
      case NORTH_WEST:
        return SOUTH_EAST;
      case SOUTH_EAST:
        return NORTH_WEST;
      case SOUTH_WEST:
        return NORTH_EAST;
      case WEST_NORTH_WEST:
        return EAST_SOUTH_EAST;
      case NORTH_NORTH_WEST:
        return SOUTH_SOUTH_EAST;
      case NORTH_NORTH_EAST:
        return SOUTH_SOUTH_WEST;
      case EAST_NORTH_EAST:
        return WEST_SOUTH_WEST;
      case EAST_SOUTH_EAST:
        return WEST_NORTH_WEST;
      case SOUTH_SOUTH_EAST:
        return NORTH_NORTH_WEST;
      case SOUTH_SOUTH_WEST:
        return NORTH_NORTH_EAST;
      case WEST_SOUTH_WEST:
        return EAST_NORTH_EAST;
      default:
        throw new IllegalArgumentException();
    }
  }

  @Nullable
  public static Direction findClosest(@NotNull AbstractFloat3D immutableVector, @NotNull Collection<? extends Type> allowedTypes) {
    Objects.requireNonNull(immutableVector);
    Objects.requireNonNull(allowedTypes);
    AbstractFloat3D vector = immutableVector;
    if (allowedTypes.contains(Type.VERTICAL)) {
      vector = immutableVector.withY(0.0);
    }

    vector = vector.normalize();
    Direction closest = null;
    double closestDot = -2.0;
    Direction[] var7 = Direction.values();
    int i = 0;

    for (int length = var7.length; i < length; ++i) {
      Direction direction = var7[i];
      if (allowedTypes.contains(direction.getType())) {

      }
    }

    return closest;
  }

  public static float normalizeMinecraftYaw(float yaw0) {
    float yaw = (yaw0 + 180.0F) % (float) 360;
    return yaw < 0.0F ? yaw + (float) 360 : yaw;
  }

  @NotNull
  public static Direction getPitchDirection(float pitch) {
    if (-90.0F <= pitch && pitch <= 0.0F) {
      return Direction.DOWN;
    } else {
      if (!(0.0F <= pitch && pitch <= 90.0F)) {
        throw new IllegalArgumentException("Unknown Minecraft pitch value: " + pitch);
      }

      return Direction.UP;
    }
  }

  @NotNull
  public static Direction cardinalDirectionFromYaw(float yaw) {
    float transformedYaw = normalizeMinecraftYaw(yaw);
    if (0.0F <= transformedYaw && transformedYaw <= 45.0F) {
      return Direction.NORTH;
    } else if (45.0F <= transformedYaw && transformedYaw <= 135.0F) {
      return Direction.EAST;
    } else if (135.0F <= transformedYaw && transformedYaw <= 225.0F) {
      return Direction.SOUTH;
    } else if (225.0F <= transformedYaw && transformedYaw <= 315.0F) {
      return Direction.WEST;
    } else {
      if (!(315.0F <= transformedYaw && transformedYaw <= 360.0F)) {
        throw new AssertionError("Unexpected yaw for cardinal direction: " + yaw + " -> " + transformedYaw);
      }

      return Direction.NORTH;
    }
  }

  @NotNull
  public static Direction fromYaw(float yaw0) {
    float yaw = yaw0 % 360.0F;
    if (yaw < 0.0F) {
      yaw += 360.0F;
    }

    if (337.5F <= yaw && yaw <= 360.0F) {
      return Direction.SOUTH;
    } else if (292.5F <= yaw && yaw <= 337.5F) {
      return Direction.NORTH_EAST;
    } else if (247.5F <= yaw && yaw <= 292.5F) {
      return Direction.EAST;
    } else if (202.5F <= yaw && yaw <= 247.5F) {
      return Direction.SOUTH_EAST;
    } else if (157.5F <= yaw && yaw <= 202.5F) {
      return Direction.NORTH;
    } else if (112.5F <= yaw && yaw <= 157.5F) {
      return Direction.NORTH_WEST;
    } else if (67.5F <= yaw && yaw <= 112.5F) {
      return Direction.WEST;
    } else if (22.5F <= yaw && yaw <= 67.5F) {
      return Direction.SOUTH_WEST;
    } else {
      if (!(0.0F <= yaw && yaw <= 22.5F)) {
        throw new AssertionError("Unexpected yaw for direction: " + yaw);
      }

      return Direction.SOUTH;
    }
  }

  public enum Type {
    /**
     *
     */
    CARDINAL,
    /**
     *
     */
    ORDINAL,
    /**
     *
     */
    SECONDARY_ORDINAL,
    /**
     *
     */
    VERTICAL
  }
}

