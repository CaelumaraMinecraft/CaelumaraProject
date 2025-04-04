package net.aurika.configuration.yaml.part;

import net.aurika.common.snakeyaml.nodes.NodeUtils;
import net.aurika.configuration.part.ConfigPart;
import net.aurika.configuration.part.annotation.NamedPart;
import net.aurika.configuration.part.exception.ConfigPartAlreadyExistException;
import net.aurika.configuration.part.exception.ConfigPartTypeNotSupportedException;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Node;

import java.util.IdentityHashMap;
import java.util.Map;

public class DefaultYamlConfigSection extends DefaultYamlConfigPart<MappingNode> implements YamlConfigSection {

  protected final Map<Node, @NamedPart YamlConfigPart> subPartsCache = new IdentityHashMap<>();

  public DefaultYamlConfigSection(@NotNull MappingNode node) {
    super(node);
  }

  public DefaultYamlConfigSection(@NotNull YamlConfigSequence parentSequence, @NotNull MappingNode node) {
    super(parentSequence, node);
  }

  public DefaultYamlConfigSection(@NotNull YamlConfigSection parentSection, @NotNull String name, @NotNull MappingNode node) {
    super(parentSection, name, node);
  }

  protected @NamedPart @NotNull YamlConfigPart getOrInitSubPartStorage(@NotNull String name, @NotNull Node node) {
    Validate.Arg.notNull(node, "node");
    @Nullable YamlConfigPart subPart = subPartsCache.get(node);
    if (subPart == null) {
      subPart = DefaultYamlConfigPart.createSectionSub(this, name, node);
      subPartsCache.put(node, subPart);
    }
    return subPart;
  }

  @Override
  public boolean hasSubPart(@NotNull String name) {
    Validate.Arg.notNull(name, "name");
    return NodeUtils.hasNode(node, name);
  }

  @Override
  public @NamedPart @Nullable YamlConfigPart getSubPart(@NotNull String name) {
    Validate.Arg.notNull(name, "name");
    @Nullable Node gotSubNode = NodeUtils.getNode(node, name);
    return gotSubNode == null ? null : getOrInitSubPartStorage(name, gotSubNode);
  }

  @Override
  public @NamedPart @Nullable YamlConfigPart removeSubPart(@NotNull String name) {
    Validate.Arg.notNull(name, "name");
    @Nullable Node removedSubNode = NodeUtils.removeNode(node, name);
    return removedSubNode == null ? null : getOrInitSubPartStorage(name, removedSubNode);
  }

  @Override
  public void addSubPart(@NotNull String name, @NamedPart @NotNull ConfigPart sub) throws ConfigPartAlreadyExistException, ConfigPartTypeNotSupportedException {
    if (NodeUtils.hasNode(node, name)) {
      throw new ConfigPartAlreadyExistException(name);
    } else {
      forceAddSubPart(name, sub);
    }
  }

  @Override
  public void forceAddSubPart(@NotNull String name, @NamedPart final @NotNull ConfigPart sub) throws ConfigPartTypeNotSupportedException {
    if (sub instanceof YamlConfigPart yamlSub) {
      NodeUtils.putNode(node, name, yamlSub.yamlNode());
      // dont init sub part storage?
    } else {
      throw new ConfigPartTypeNotSupportedException();
    }
  }

}
