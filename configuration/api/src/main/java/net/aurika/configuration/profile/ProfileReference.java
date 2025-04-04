package net.aurika.configuration.profile;

import org.jetbrains.annotations.NotNull;

public interface ProfileReference {

  @NotNull Profile profile() throws ProfileReferenceInvalidException;

}
