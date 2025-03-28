package net.aurika.config.sections.format;

public class StandardConfigSectionFormat implements ConfigSectionFormat {

  public static final StandardConfigSectionFormat SCALAR = new StandardConfigSectionFormat(false);
  public static final StandardConfigSectionFormat LIST = new StandardConfigSectionFormat(false);
  public static final StandardConfigSectionFormat MAPPING = new StandardConfigSectionFormat(true);

  private final boolean isMap;

  public StandardConfigSectionFormat(boolean isMap) {
    this.isMap = isMap;
  }

  @Override
  public boolean isMap() {
    return this.isMap;
  }

  @Override
  public boolean equals(ConfigSectionFormat other) {
    return this == other;
  }

}
