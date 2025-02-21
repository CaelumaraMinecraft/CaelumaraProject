package net.aurika.auspice.service.mythicmobs.v5.conditions;

import org.bukkit.entity.Entity;

public interface SimpleRelationalChecker {
    boolean check(Entity caster, Entity target);
}
