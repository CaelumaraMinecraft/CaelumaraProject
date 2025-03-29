package net.aurika.auspice.service.mythicmobs.v5.conditions;

import io.lumine.mythic.api.skills.conditions.ISkillCondition;
import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class KingdomsMythicMobConditionListener implements Listener {

  static final Map<String, ISkillCondition> CONDITIONS = new HashMap<>();

  @EventHandler
  public void onConditionLoad(MythicConditionLoadEvent event) {
    String conditionName = event.getConditionName().toLowerCase(Locale.ENGLISH);
    ISkillCondition condition = CONDITIONS.get(conditionName);
    if (condition != null) event.register(condition);
  }

}
