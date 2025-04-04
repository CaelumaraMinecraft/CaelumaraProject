package net.aurika.config.profile;

import org.jetbrains.annotations.NotNull;

public interface ProfileReference {

  @NotNull Profile profile() throws ProfileReferenceInvalidException;

}
