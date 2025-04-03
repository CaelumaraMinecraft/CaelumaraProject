package net.aurika.config.yaml.part;

import net.aurika.config.part.ConfigPart;
import net.aurika.config.part.ConfigSection;
import net.aurika.config.part.annotation.NamedPart;
import net.aurika.config.part.exception.ConfigPartAlreadyExistException;
import net.aurika.config.part.exception.ConfigPartTypeNotSupportedException;
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
