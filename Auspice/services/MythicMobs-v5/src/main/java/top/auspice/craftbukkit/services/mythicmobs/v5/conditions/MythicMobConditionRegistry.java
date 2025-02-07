package top.auspice.craftbukkit.services.mythicmobs.v5.conditions;

public final class MythicMobConditionRegistry {
    public static void register(String name, SimpleRelationalChecker checker) {
        String id = "auspice_" + name;
        KingdomsMythicMobConditionListener.CONDITIONS.put(id, new RelationalMythicMobSkillCondition(id, checker));
    }
}
