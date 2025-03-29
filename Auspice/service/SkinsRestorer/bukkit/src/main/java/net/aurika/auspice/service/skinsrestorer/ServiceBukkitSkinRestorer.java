package net.aurika.auspice.service.skinsrestorer;

import net.aurika.auspice.service.api.BukkitService;
import net.aurika.validate.Validate;
import net.skinsrestorer.api.exception.DataRequestException;
import net.skinsrestorer.api.exception.MineSkinException;
import net.skinsrestorer.api.property.SkinProperty;
import net.skinsrestorer.api.property.SkinVariant;
import net.skinsrestorer.api.storage.PlayerStorage;
import net.skinsrestorer.api.storage.SkinStorage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class ServiceBukkitSkinRestorer implements BukkitService, ServiceSkinsRestorer {

  public void changeSkin(@NotNull Player player, @NotNull SkinValueType valueType, @NotNull String value) {
    Validate.Arg.notNull(player, "player");
    Validate.Arg.notNull(valueType, "valueType");
    Validate.Arg.notNull(value, "value");
    // Example plugin: https://github.com/SkinsRestorer/SkinsRestorerAPIExample/blob/main/src/main/java/net/skinsrestorer/apiexample/SkinsRestorerAPIExample.java
    SkinProperty prop;
    try {
      PlayerStorage playerStorage = API_HOLDER.get().getPlayerStorage();
      Optional<SkinProperty> property = playerStorage.getSkinForPlayer(player.getUniqueId(), player.getName());

      SkinStorage skinStorage = API_HOLDER.get().getSkinStorage();
      if (valueType == SkinValueType.URL) {
        prop = skinStorage.findOrCreateSkinData(value, SkinVariant.CLASSIC).get().getProperty();
      } else prop = property.get();
    } catch (DataRequestException | MineSkinException e) {
      throw new RuntimeException(
          "Error while attemptin gto change " + player + "'s skin to '" + value + "' value of " + valueType, e);
    }
    API_HOLDER.get().getSkinApplier(Player.class).applySkin(player, prop);
  }

}
