package net.aurika.configuration.yaml.part;

import net.aurika.configuration.part.ConfigPart;
import net.aurika.configuration.part.ConfigSection;
import net.aurika.configuration.part.annotation.NamedPart;
import net.aurika.configuration.part.exception.ConfigPartAlreadyExistException;
import net.aurika.configuration.part.exception.ConfigPartTypeNotSupportedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.MappingNode;

public interface YamlConfigSection extends YamlConfigPart, ConfigSection {

  @Override
  @NamedPart
  @Nullable YamlConfigPart getSubPart(@NotNull String name);

  @Override
  @NamedPart
  @Nullable YamlConfigPart removeSubPart(@NotNull String name);

  @Override
  void addSubPart(@NotNull String name, @NamedPart @NotNull ConfigPart sub) throws ConfigPartAlreadyExistException, ConfigPartTypeNotSupportedException;

  @Override
  void forceAddSubPart(@NotNull String name, @NamedPart @NotNull ConfigPart sub) throws ConfigPartTypeNotSupportedException;

  @Override
  @NotNull MappingNode yamlNode();

}
