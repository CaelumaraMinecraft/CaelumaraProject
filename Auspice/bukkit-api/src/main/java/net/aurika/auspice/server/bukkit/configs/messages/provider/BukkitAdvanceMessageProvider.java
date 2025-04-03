package net.aurika.auspice.server.bukkit.configs.messages.provider;

import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.configs.messages.MessageObject;
import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.server.bukkit.config.sections.ConfigSectionExtension;
import net.aurika.auspice.server.bukkit.loader.PluginAuspiceLoader;
import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.entity.Player;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.translation.message.provider.AdvanceMessageProvider;
import net.aurika.auspice.translation.message.provider.SingleMessageProvider;
import net.aurika.config.sections.ConfigSection;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BukkitAdvanceMessageProvider extends SingleMessageProvider implements AdvanceMessageProvider {

  protected final @Nullable TextObject actionbar;
  protected final @Nullable Titles titles;
  @Nullable
  protected XSound.Record sound;

  public BukkitAdvanceMessageProvider(MessageObject message, @Nullable TextObject actionbar, @Nullable Titles titles) {
    super(message);
    this.actionbar = actionbar;
    this.titles = titles;
  }

  public BukkitAdvanceMessageProvider(ConfigSection configSection) {
    super((configSection.getString(new String[]{"message"})) == null ? null : TextCompiler.compile(
        configSection.getString(new String[]{"message"})));
    Objects.requireNonNull(configSection, "Config accessor is null");
    String actionbar = configSection.getString(new String[]{"actionbar"});
    this.actionbar = actionbar == null ? null : TextCompiler.compile(actionbar);
    String sound = configSection.getString(new String[]{"sound"});
    this.sound = sound == null ? null : XSound.parse(sound);
    ConfigSection titles = configSection.getSubSection("titles");
    this.titles = titles != null ? Titles.parseTitle(
        ConfigSectionExtension.toBukkitConfigurationSection(configSection)) : null;
  }

  public BukkitAdvanceMessageProvider withSound(XSound.Record sound) {
    this.sound = sound;
    return this;
  }

  public BukkitAdvanceMessageProvider err() {
    this.sound = XSound.parse(AuspiceGlobalConfig.ERROR_SOUND.getString());
    return this;
  }

  public void handleExtraServices(CommandSender messageReceiver, MessageContextImpl placeholderProvider) {
    if (messageReceiver instanceof Player player) {
      if (this.sound != null) {
        this.sound.soundPlayer().forPlayers((org.bukkit.entity.Player) player.getRealPlayer()).play();
      }

      if (this.actionbar != null) {
        ActionBar.sendActionBar(
            PluginAuspiceLoader.get(), (org.bukkit.entity.Player) player, this.actionbar.build(placeholderProvider));
      }

      if (this.titles != null) {
        Titles titles = this.titles.clone();
        if (titles.getTitle() != null) {
          titles.setTitle(TextCompiler.compile(titles.getTitle()).build(placeholderProvider));
        }

        if (titles.getSubtitle() != null) {
          titles.setSubtitle(TextCompiler.compile(titles.getSubtitle()).build(placeholderProvider));
        }

        titles.send((org.bukkit.entity.Player) player);
      }
    }
  }

}
