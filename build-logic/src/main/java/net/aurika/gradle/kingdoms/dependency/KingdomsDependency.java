package net.aurika.gradle.kingdoms.dependency;

import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class KingdomsDependency {

  public static final @Unmodifiable Map<String, String> RELOCATES = Collections.unmodifiableMap(buildRelocates());

  public static Map<String, String> buildRelocates() {
    Map<String, String> map = new HashMap<>();
    // Kotlin
    map.put("kotlin", "org.kingdoms.libs.kotlin");
    // Annotations
    map.put("org.jetbrains", "org.kingdoms.libs.jetbrains");
    map.put("org.intellij", "org.kingdoms.libs.intellij");
    map.put("org.checkerframework", "org.kingdoms.libs.checkerframework");
    // Cache
    map.put("com.github.benmanes.caffeine", "org.kingdoms.libs.caffeine");
    // XSeries
    map.put("com.cryptomorin.xseries", "org.kingdoms.libs.xseries");
    // Yaml
    map.put("org.snakeyaml", "org.kingdoms.libs.snakeyaml");
    // ASM
    map.put("org.objectweb.asm", "org.kingdoms.libs.asm");
    // Database
    map.put("com.mongodb", "org.kingdoms.libs.mongodb");
    map.put("org.bson", "org.kingdoms.libs.bson");
    map.put("com.google", "org.kingdoms.libs.google");
    map.put("org.mariadb.jdbc", "org.kingdoms.libs.mariadb");
    map.put("com.mysql", "org.kingdoms.libs.mysql");
    map.put("org.postgresql", "org.kingdoms.libs.postgresql");
    map.put("com.zaxxer.hikari", "org.kingdoms.libs.hikari");
    return map;
  }

  public static Map<String, String> reverseRelocates() {
    Map<String, String> relocates = buildRelocates();
    Map<String, String> reverses = new HashMap<>(relocates.size());
    for (Map.Entry<String, String> entry : relocates.entrySet()) {
      reverses.put(entry.getValue(), entry.getKey());
    }
    return reverses;
  }

}
