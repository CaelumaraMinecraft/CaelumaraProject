package net.aurika.auspice.constants.metadata;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Metadatable {

  AuspiceMetadata getMetadata(AuspiceMetadataHandler handler);

  @NotNull Map<AuspiceMetadataHandler, AuspiceMetadata> getMetadata();

}
