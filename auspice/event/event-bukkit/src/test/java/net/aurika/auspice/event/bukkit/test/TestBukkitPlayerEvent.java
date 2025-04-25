package net.aurika.auspice.event.bukkit.test;

import net.aurika.auspice.event.bukkit.NativeBukkitEvent;
import net.aurika.auspice.event.bukkit.BukkitEvent;
import org.bukkit.event.player.PlayerEvent;

@NativeBukkitEvent(PlayerEvent.class)
public interface TestBukkitPlayerEvent extends BukkitEvent { }

class TestBukkitPlayerEvent$Companion { }
