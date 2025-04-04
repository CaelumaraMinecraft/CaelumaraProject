package net.aurika.configuration.profile.managers;

@FunctionalInterface
public interface FileWatcher {

  void handle(FileWatchEvent event);

}
