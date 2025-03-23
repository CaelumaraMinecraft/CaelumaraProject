package net.aurika.auspice.game.bukkit.loader;

import net.aurika.auspice.user.AuspiceUser;
import net.aurika.auspice.loader.AuspiceLoader;
import net.aurika.auspice.user.Auspice;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface PluginAuspiceLoader extends AuspiceLoader, Plugin {

    @Override
    default void init() {
        AuspiceLoader.super.init();
        Auspice.get().setDataFolder(this.getDataFolder());
        Container.INSTANCE = this;
    }

    @NotNull AuspiceUser.State getState();

    @NotNull Plugin loaderPlugin();

    static @NotNull PluginAuspiceLoader get() {
        PluginAuspiceLoader instance = Container.INSTANCE;
        if (instance == null) {
            throw new IllegalStateException("Auspice loader not initialized");
        }
        return instance;
    }
}
