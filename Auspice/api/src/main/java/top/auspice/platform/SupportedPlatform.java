package top.auspice.platform;

public interface SupportedPlatform {
    boolean isAvailable();

    boolean contains(SupportedPlatform platform);
}
