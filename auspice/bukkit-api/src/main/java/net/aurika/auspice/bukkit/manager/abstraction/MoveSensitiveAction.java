package net.aurika.auspice.bukkit.manager.abstraction;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.function.Function;

public class MoveSensitiveAction {

  public Function<PlayerMoveEvent, Boolean> onMove;
  public Function<PlayerDeathEvent, Boolean> onDeath;
  public Function<EntityDamageEvent, Boolean> onDamage;

  public MoveSensitiveAction() {
  }

  public void onMove(Function<PlayerMoveEvent, Boolean> action) {
    this.onMove = action;
  }

  public void onDeath(Function<PlayerDeathEvent, Boolean> action) {
    this.onDeath = action;
  }

  public void onDamage(Function<EntityDamageEvent, Boolean> action) {
    this.onDamage = action;
  }

  public void onAnyMove(Function<Player, Boolean> action) {
    this.onMove = (event) -> (Boolean) action.apply(event.getPlayer());
    this.onDeath = (event) -> (Boolean) action.apply(event.getEntity());
    this.onDamage = (event) -> (Boolean) action.apply((Player) event.getEntity());
  }

}