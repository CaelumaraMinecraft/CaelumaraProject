package top.auspice.api.user;

import net.aurika.annotations.UtilMethod;
import net.aurika.namespace.NSKey;
import net.aurika.namespace.NSedKey;
import net.aurika.namespace.nested.NestedNamespace;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.LanguageManager;
import top.auspice.configs.texts.messenger.DefinedMessenger;
import top.auspice.constants.metadata.AuspiceMetadataRegistry;
import top.auspice.diversity.Diversity;
import top.auspice.scheduler.TaskScheduleProvider;

import java.lang.annotation.*;
import java.util.Map;

/**
 * AuspiceAPI 使用者
 */
public interface AuspiceUser {

    @NotNull
    @AuspiceUserName
    String getAuspiceUserName();

    @NotNull
    @NSKey.Namespace
    String getNamespace();

    @NotNull Diversity getDefaultDiversity();

    void onLoad();

    void onEnable();

    void onReload();

    void onDisable();

    @NotNull State getState();

    @UtilMethod
    default void registerAuspiceUser() {
        AuspiceUserRegistry.register(this);
    }

    @UtilMethod
    default Map<NSedKey, DefinedMessenger[]> getDefaultMessages() {
        return LanguageManager.getGroupedDefaultMessages(this);
    }

    @UtilMethod
    default @NotNull NestedNamespace getTopNestedNamespace() {
        return AuspiceUser.getTopNestedNamespace(this);
    }

    static AuspiceMetadataRegistry getMetadataRegistry() {
        return AuspiceMetadataRegistry.INSTANCE;
    }

    static TaskScheduleProvider taskScheduler() {
        return Container.taskScheduler;
    }

    @ApiStatus.Internal
    static void setTaskScheduler(TaskScheduleProvider taskScheduler) {
        Container.taskScheduler = taskScheduler;
    }

    static @NotNull @NSKey.Namespace String getNamespaceChecked(@NotNull AuspiceUser user) {
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

    static @NotNull NestedNamespace getTopNestedNamespace(AuspiceUser au) {
        Checker.Arg.notNull(au, "au");
        String ns = au.getNamespace();
        Checker.Expr.notEmpty(ns, "AuspiceUser.getNamespace()");
        return new NestedNamespace(new String[]{au.getNamespace()});
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

    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.METHOD, ElementType.PARAMETER})
    @org.intellij.lang.annotations.Pattern("^[A-Za-z0-9_\\.-]+$")
    @interface AuspiceUserName {
    }
}
