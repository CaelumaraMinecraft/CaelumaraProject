package net.aurika.util.scheduler;

public interface Cancellable {

  boolean cancel();

  boolean isCancelled();

}
