package net.aurika.config.profile.managers;

@FunctionalInterface
public interface FileWatcher {

  void handle(FileWatchEvent event);

}
