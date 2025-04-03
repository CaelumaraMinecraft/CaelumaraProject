package net.aurika.kingdoms.auspice.util;

import org.kingdoms.constants.group.Kingdom;

import java.util.Objects;

public class RelatedKingdoms {

  private final Kingdom active;
  private final Kingdom passive;

  public RelatedKingdoms(Kingdom active, Kingdom passive) {
    this.active = active;
    this.passive = passive;
  }

  public Kingdom getActiveKingdom() {
    return this.active;
  }

  public Kingdom getPassiveKingdom() {
    return this.passive;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RelatedKingdoms that = (RelatedKingdoms) o;
    return Objects.equals(active, that.active) && Objects.equals(passive, that.passive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(active, passive);
  }

}
