package net.aurika.configuration.profile.groups;

import org.jetbrains.annotations.NotNull;

public enum ProfileGroups implements ProfileGroup {

  GLOBAL_CONFIG("global_config"),
  LOCAL_CONFIG("local_config"),
  MESSAGE("message"),
  ITEM("item"),
  GUI("gui"),


  ;

  public final String groupName;

  ProfileGroups(String groupName) {
    this.groupName = groupName;
  }

  @NotNull
  @Override
  public String getGroupName() {
    return this.groupName;
  }
}
