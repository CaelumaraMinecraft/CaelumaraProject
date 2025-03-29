package net.aurika.auspice.service.api;

public final class EmptyService implements Service {

  public static final Service INSTANCE = new EmptyService();

  private EmptyService() {
  }

}