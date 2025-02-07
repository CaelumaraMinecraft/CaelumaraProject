package top.auspice.server.location;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Directional {
    float getYaw();

    float getPitch();

    @NotNull
    default Vector3 getDirection() {
        double rotX = this.getYaw();
        double rotY = this.getPitch();
        double xz = Math.cos(Math.toRadians(rotY));
        return Vector3.of(-xz * Math.sin(Math.toRadians(rotX)), -Math.sin(Math.toRadians(rotY)), xz * Math.cos(Math.toRadians(rotX)));
    }

    @NotNull
    static Directional of(@NotNull Number yaw, @NotNull Number pitch) {
        Objects.requireNonNull(yaw);
        Objects.requireNonNull(pitch);
        return new AbstractDirectional(pitch.floatValue(), yaw.floatValue());
    }
}
