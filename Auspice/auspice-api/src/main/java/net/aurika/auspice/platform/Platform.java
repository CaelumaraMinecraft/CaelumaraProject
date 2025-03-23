package net.aurika.auspice.platform;

public enum Platform {
    BUKKIT(CrossPlatformManager.isRunningBukkit()),
    SPIGOT(CrossPlatformManager.isRunningSpigot()),
    FOLIA(CrossPlatformManager.isRunningFolia()),
    PAPER(CrossPlatformManager.isRunningPaper()),
    FORGE(CrossPlatformManager.isRunningForge()),
    BEDROCK(CrossPlatformManager.isRunningGeyser());

    private final boolean available;
    public static final Platform[] VALUES = values();

    Platform(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return this.available;
    }
}
