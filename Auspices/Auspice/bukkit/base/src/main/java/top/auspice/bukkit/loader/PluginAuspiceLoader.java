package top.auspice.bukkit.loader;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import top.auspice.api.user.AuspiceUser;
import top.auspice.loader.AuspiceLoader;
import top.auspice.main.Auspice;

public interface PluginAuspiceLoader extends AuspiceLoader, Plugin {

    @Override
    default void init() {
        AuspiceLoader.super.init();
        Auspice.get().setDataFolder(this.getDataFolder());
        top.auspice.bukkit.loader.Container.INSTANCE = this;
    }

    @NotNull AuspiceUser.State getState();

    @Deprecated
    default Plugin getLoader() {
        return this.getLoaderPlugin();
    }

    Plugin getLoaderPlugin();

    static PluginAuspiceLoader get() {
        PluginAuspiceLoader instance = top.auspice.bukkit.loader.Container.INSTANCE;
        if (instance == null) {
            throw new IllegalStateException("Auspice loader not initialized");
        }
        return instance;
    }
}
