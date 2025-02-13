package top.auspice.server.location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.util.enumeration.Enums;

import java.util.Collection;
import java.util.Map;
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

    public static final Direction @NotNull [] VALUES = values();
    private static final Map<String, Direction> MAPPINGS = Enums.createMapping(values());
    private final @NotNull Type type;
    private final int x;
    private final int y;
    private final int z;
    private final float yaw;
    private final float pitch;

    @NotNull
    public final Type getType() {
        return this.type;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final int getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    Direction(int modX, int modY, int modZ, @NotNull Type type) {
        Objects.requireNonNull(type);
        this.x = modX;
        this.y = modY;
        this.z = modZ;
        this.type = type;
        Directional directional = LocationUtils.fromDirection(Vector3.of(modX, modY, modZ));
        if (modX == 0 && modZ == 0) {
            this.yaw = 0.0F;
            this.pitch = directional.getPitch();
        } else if (modY == 0) {
            this.yaw = (directional.getYaw() + 180.0F) % (float) 360;
            this.pitch = 0.0F;
        } else {
            this.yaw = (directional.getYaw() + 180.0F) % (float) 360;
            this.pitch = directional.getPitch();
        }
    }

    Direction(Direction face1, Direction face2, Type type) {
        this(face1.x + face2.x, face1.y + face2.y, face1.z + face2.z, type);
    }

    public final boolean isCartesian() {
        return switch (this) {
            case NORTH, EAST, SOUTH, WEST, UP, DOWN -> true;
            default -> false;
        };
    }

    @NotNull
    public final Direction getOppositeFace() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case UP -> DOWN;
            case DOWN -> UP;
            case NORTH_EAST -> SOUTH_WEST;
            case NORTH_WEST -> SOUTH_EAST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH_WEST -> NORTH_EAST;
            case WEST_NORTH_WEST -> EAST_SOUTH_EAST;
            case NORTH_NORTH_WEST -> SOUTH_SOUTH_EAST;
            case NORTH_NORTH_EAST -> SOUTH_SOUTH_WEST;
            case EAST_NORTH_EAST -> WEST_SOUTH_WEST;
            case EAST_SOUTH_EAST -> WEST_NORTH_WEST;
            case SOUTH_SOUTH_EAST -> NORTH_NORTH_WEST;
            case SOUTH_SOUTH_WEST -> NORTH_NORTH_EAST;
            case WEST_SOUTH_WEST -> EAST_NORTH_EAST;
        };
    }

    @Nullable
    public static Direction fromString(@NotNull String name) {
        Objects.requireNonNull(name);
        return Direction.MAPPINGS.get(name);
    }

    @Nullable
    public static Direction findClosest(@NotNull Vector3 immutableVector, @NotNull Collection<? extends Type> allowedTypes) {
        Objects.requireNonNull(immutableVector);
        Objects.requireNonNull(allowedTypes);
        Vector3 vector = immutableVector;
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

