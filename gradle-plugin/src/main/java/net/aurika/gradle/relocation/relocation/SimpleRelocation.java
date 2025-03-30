package net.aurika.gradle.relocation.relocation;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class SimpleRelocation {

  private final String pattern;
  private final String relocatedPattern;

  public static @NotNull SimpleRelocation of(@NotNull String pattern, @NotNull String relocatedPattern) {
    return new SimpleRelocation(pattern, relocatedPattern);
  }

  public SimpleRelocation(String pattern, String relocatedPattern) {
    this.pattern = pattern;
    this.relocatedPattern = relocatedPattern;
  }

  public String getPattern() {
    return this.pattern;
  }

  public String getRelocatedPattern() {
    return this.relocatedPattern;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o != null && this.getClass() == o.getClass()) {
      SimpleRelocation that = (SimpleRelocation) o;
      return Objects.equals(this.pattern, that.pattern) && Objects.equals(this.relocatedPattern, that.relocatedPattern);
    } else {
      return false;
    }
  }

  public int hashCode() {
    return Objects.hash(this.pattern, this.relocatedPattern);
  }

}
