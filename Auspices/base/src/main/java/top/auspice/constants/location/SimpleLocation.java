package top.auspice.constants.location;

import top.auspice.api.annotations.data.Immutable;
import top.auspice.utils.Checker;

@Immutable
public class SimpleLocation {
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public SimpleLocation(String worldName, double x, double y, double z, float yaw, float pitch) {
        Checker.Argument.checkNotNull(worldName, "worldName");
        this.world = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
