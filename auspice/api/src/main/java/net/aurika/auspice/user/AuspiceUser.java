package net.aurika.auspice.user;

import net.aurika.auspice.constants.metadata.AuspiceMetadataRegistry;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.common.annotation.UtilMethod;
import net.aurika.common.ident.Group;
import net.aurika.common.ident.Grouped;
import net.aurika.util.scheduler.TaskScheduleProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * AuspiceAPI 使用者
 */
public interface AuspiceUser extends Grouped {

  @Override
  @NotNull Group group();

  @AuspiceUserName
  @NotNull String auspiceUserName();

  /**
   * Gets the default translation.
   */
  @NotNull Diversity defaultDiversity();

  void onLoad();

  void onEnable();

  void onReload();

  void onDisable();

  @NotNull State state();

  @UtilMethod
  default void registerAuspiceUser() {
    AuspiceUserRegistry.register(this);
  }

  static AuspiceMetadataRegistry metadataRegistry() {
    return AuspiceMetadataRegistry.INSTANCE;
  }

  static TaskScheduleProvider taskScheduler() {
    return Container.taskScheduler;
  }

  @ApiStatus.Internal
  static void setTaskScheduler(TaskScheduleProvider taskScheduler) {
    Container.taskScheduler = taskScheduler;
  }

  enum State {
    /**
     * When constructor
     */
    INITIATING,
    /**
     * When finished constructor
     */
    INITIATED,
    /**
     * When {@link #onLoad()}
     */
    LOADING,
    /**
     * When finished {@link #onLoad()}
     */
    LOADED,
    /**
     * When {@link #onEnable()}
     */
    ENABLING,
    /**
     * When finished {@link #onEnable()}
     */
    ENABLED,
    /**
     * When {@link #onDisable()}
     */
    DISABLING,
    /**
     * When finished {@link #onDisable()}
     */
    DISABLED,
    /**
     * When {@link #onReload()}
     */
    RELOADING,
    /**
     * When finished {@link #onReload()}
     */
    RELOADED,
  }

}
