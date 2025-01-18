package top.auspice.api.user;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import top.auspice.api.annotations.UtilMethod;
import top.auspice.configs.texts.LanguageManager;
import top.auspice.configs.texts.messenger.DefinedMessenger;
import top.auspice.constants.metadata.AuspiceMetadataRegistry;
import top.auspice.diversity.Diversity;
import top.auspice.key.NSKey;
import top.auspice.key.NSedKey;
import top.auspice.key.nested.NestedNamespace;
import top.auspice.scheduler.TaskScheduleProvider;

import java.lang.annotation.*;
import java.util.Map;

/**
 * AuspiceAPI 使用者
 */
public interface AuspiceUser {

    public @NotNull @AuspiceUserName String getAuspiceUserName();

    public @NotNull @NSKey.Namespace String getNamespace();

    public @NotNull Diversity getDefaultDiversity();

    public void onLoad();

    public void onEnable();

    public void onReload();

    public void onDisable();

    public @NotNull State getState();

    @UtilMethod
    default public void registerAuspiceUser() {
        AuspiceUserRegistry.register(this);
    }

    @UtilMethod
    default public Map<NSedKey, DefinedMessenger[]> getDefaultMessages() {
        return LanguageManager.getGroupedDefaultMessages(this);
    }

    @UtilMethod
    default public @NotNull NestedNamespace getTopNestedNamespace() {
        return NestedNamespace.topOfAuspiceUser(this);
    }

    public static AuspiceMetadataRegistry getMetadataRegistry() {
        return AuspiceMetadataRegistry.INSTANCE;
    }

    public static TaskScheduleProvider taskScheduler() {
        return Container.taskScheduler;
    }

    @ApiStatus.Internal
    public static void setTaskScheduler(TaskScheduleProvider taskScheduler) {
        Container.taskScheduler = taskScheduler;
    }

    public static @NotNull @NSKey.Namespace String getNamespaceChecked(@NotNull AuspiceUser user) {
        String ns = user.getNamespace();
        // noinspection ConstantValue
        if (ns == null) {
            throw new AuspiceUserException("The namespace of auspice user returns null");
        }
        if (!NSKey.NAMESPACE_PATTERN.matcher(ns).matches()) {
            throw new AuspiceUserException("The namespace of auspice user returns an invalid namespace, it must matches: " + NSKey.ALLOWED_NAMESPACE);
        }
        return ns;
    }

    public static enum State {
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

    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.METHOD, ElementType.PARAMETER})
    @org.intellij.lang.annotations.Pattern("^[A-Za-z0-9_\\.-]+$")
    public static @interface AuspiceUserName {
    }
}
