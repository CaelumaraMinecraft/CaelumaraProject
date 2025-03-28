package top.mckingdom.civilizations.events;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.events.KingdomsEvent;
import top.mckingdom.civilizations.constants.civilization.Civilization;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMember;

public class CivilizationMemberLeaveEvent extends KingdomsEvent {
  private static final HandlerList handlers = new HandlerList();
  private final Civilization civilization;
  private final CivilizationMember<?> member;
  private final Reason reason;

  public CivilizationMemberLeaveEvent(Civilization civilization, CivilizationMember<?> member, Reason reason) {
    this.civilization = civilization;
    this.member = member;
    this.reason = reason;
  }

  @NotNull
  public static HandlerList getHandlerList() {
    return handlers;
  }

  @NotNull
  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public Civilization getCivilization() {
    return civilization;
  }

  public CivilizationMember<?> getMember() {
    return member;
  }

  public Reason getReason() {
    return reason;
  }

  public enum Reason {
    ADMIN,

    CUSTOM,
  }

}
