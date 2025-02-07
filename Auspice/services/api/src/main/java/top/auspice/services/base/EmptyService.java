package top.auspice.services.base;

public final class EmptyService implements Service {
    public static final Service INSTANCE = new EmptyService();

    private EmptyService() {
    }
}