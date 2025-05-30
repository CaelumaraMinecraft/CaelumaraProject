package net.aurika.common.dependency.relocation;

import java.util.Objects;

public final class SimpleRelocation {

  private static final String RELOCATION_PREFIX = "net.aurika.libs.";
  private final String pattern;
  private final String relocatedPattern;

  public static SimpleRelocation of(String id, String pattern) {
    return new SimpleRelocation(pattern.replace("{}", "."), RELOCATION_PREFIX + id.replace("{}", "."));
  }

  private SimpleRelocation(String pattern, String relocatedPattern) {
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
