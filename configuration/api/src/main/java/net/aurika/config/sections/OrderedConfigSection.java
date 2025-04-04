package net.aurika.config.sections;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;

public interface OrderedConfigSection extends ConfigSection {

  LinkedHashMap<String, ConfigSection> getSubSections();

  @Nullable ConfigSection getSubSection(int index);

  @Nullable ConfigSection removeSubSection(int index);

}
