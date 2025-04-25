package net.aurika.auspice.event.bukkit.test;

import net.aurika.auspice.event.bukkit.NativeBukkitEvent;
import org.bukkit.event.player.PlayerChatEvent;

@NativeBukkitEvent(PlayerChatEvent.class)
public interface TestBukkitPlayerChatEvent extends TestBukkitPlayerEvent { }
