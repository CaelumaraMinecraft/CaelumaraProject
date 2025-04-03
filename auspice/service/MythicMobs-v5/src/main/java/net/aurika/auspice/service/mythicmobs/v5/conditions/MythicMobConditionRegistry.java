package net.aurika.auspice.service.mythicmobs.v5.conditions;

public final class MythicMobConditionRegistry {

  public static void register(String name, SimpleRelationalChecker checker) {
    String id = "auspice_" + name;
    KingdomsMythicMobConditionListener.CONDITIONS.put(id, new RelationalMythicMobSkillCondition(id, checker));
  }

}
