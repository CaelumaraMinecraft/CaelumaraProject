package net.aurika.common.metadata;

import org.jetbrains.annotations.NotNull;
import sun.jvm.hotspot.oops.Metadata;

public interface MetadataContainer {

  Metadata getMetadata(@NotNull MetaKey key);

}
