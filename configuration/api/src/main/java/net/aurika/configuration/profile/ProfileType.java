package net.aurika.configuration.profile;

public interface ProfileType {

  YamlProfile YAML = new YamlProfile() {
  };

  XmlProfile XML = new XmlProfile() {
  };

  JsonProfile JSON = new JsonProfile() {
  };

  interface YamlProfile extends ProfileType {

  }

  interface XmlProfile extends ProfileType {

  }

  interface JsonProfile extends ProfileType {

  }

}
