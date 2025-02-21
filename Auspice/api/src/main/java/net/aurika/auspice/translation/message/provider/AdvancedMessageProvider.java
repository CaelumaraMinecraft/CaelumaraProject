package net.aurika.auspice.translation.message.provider;

import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.config.sections.ConfigSection;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.main.Auspice;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.server.entity.Player;

import java.util.Objects;

public class AdvancedMessageProvider extends SingleMessageProvider {
    @Nullable
    protected final TextObject actionbar;
    @Nullable
    protected final Titles titles;
    @Nullable
    protected XSound.Record sound;

    public AdvancedMessageProvider(TextObject message, @Nullable TextObject actionbar, @Nullable Titles titles) {
        super(message);
        this.actionbar = actionbar;
        this.titles = titles;
    }

    public AdvancedMessageProvider(ConfigSection configSection) {
        super((configSection.getString(new String[]{"message"})) == null ? null : TextCompiler.compile(configSection.getString(new String[]{"message"})));
        Objects.requireNonNull(configSection, "Config accessor is null");
        String actionbar = configSection.getString(new String[]{"actionbar"});
        this.actionbar = actionbar == null ? null : TextCompiler.compile(actionbar);
        String sound = configSection.getString(new String[]{"sound"});
        this.sound = sound == null ? null : XSound.parse(sound);
        ConfigSection titles = configSection.getSubSection("titles");
        this.titles = titles != null ? Titles.parseTitle(configSection.toBukkitConfigurationSection()) : null;
    }

    public AdvancedMessageProvider withSound(XSound.Record sound) {
        this.sound = sound;
        return this;
    }

    public AdvancedMessageProvider err() {
        this.sound = XSound.parse(AuspiceGlobalConfig.ERROR_SOUND.getString());
        return this;
    }

    public void handleExtraServices(CommandSender messageReceiver, MessageContextImpl placeholderProvider) {
        if (messageReceiver instanceof Player player) {
            if (this.sound != null) {
                this.sound.soundPlayer().forPlayers(player).play();
            }

            if (this.actionbar != null) {
                ActionBar.sendActionBar(Auspice.get(), player, this.actionbar.build(placeholderProvider));
            }

            if (this.titles != null) {
                Titles titles = this.titles.clone();
                if (titles.getTitle() != null) {
                    titles.setTitle(TextCompiler.compile(titles.getTitle()).build(placeholderProvider));
                }

                if (titles.getSubtitle() != null) {
                    titles.setSubtitle(TextCompiler.compile(titles.getSubtitle()).build(placeholderProvider));
                }

                titles.send(player);
            }
        }

    }
}
