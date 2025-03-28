package net.aurika.config.yaml.adapter;

import java.io.File;
import java.io.InputStream;

public class YamlFiledDefaults extends YamlWithDefaults {

  protected final File defaultsFile;

  public YamlFiledDefaults(File var1, File defaultsFile) {
    super(var1);
    this.defaultsFile = defaultsFile;
  }

  protected InputStream getDefaultsInputStream() {
    return inputStreamOf(this.defaultsFile);
  }

  protected String getDefaultsPath() {
    return this.defaultsFile.toString();
  }

  protected InputStream getSchemaInputStream() {
    return null;
  }

}
