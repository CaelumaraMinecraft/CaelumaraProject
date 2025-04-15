package net.aurika.xariaz.yaml.data.provider;

import net.aurika.common.snakeyaml.node.NodeUtil;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Node;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class YamlMappingDataProvider implements DataProvider {

  protected final @NotNull MappingNode root;
  protected final @Nullable String name;

  public YamlMappingDataProvider(@NotNull MappingNode root) {
    this(root, null);
  }

  public YamlMappingDataProvider(@NotNull MappingNode root, @Nullable String name) {
    Validate.Arg.notNull(root, "root");
    this.root = root;
    this.name = name;
  }

  @Contract(pure = true)
  protected final @Nullable Node findNode() {
    return NodeUtil.getNode(root, name);
  }

  protected final @NotNull Node getNode() throws IllegalDataStructException {
    Node node = findNode();
    if (node == null) {
      throw new IllegalDataStructException("No such node: " + name);
    } else {
      return node;
    }
  }

  @Override
  public boolean getBoolean() throws IllegalDataStructException {
    // TODO
  }

  @Override
  public @NotNull Number getNumber() throws IllegalDataStructException {
    return null;
  }

  @Override
  public @NotNull String getString() throws IllegalDataStructException {
    return "";
  }

  @Override
  public <E, C extends Collection<E>> @NotNull C getCollection(@NotNull C collection, BiConsumer<@NotNull C, DataGetter> elementHandler) throws IllegalDataStructException {
    return null;
  }

  @Override
  public <K, V, M extends Map<K, V>> @NotNull M getMap(@NotNull M map, @NotNull MappingGetterHandler<M> entryHandler) throws IllegalDataStructException {
    return null;
  }

  @Override
  public void setBoolean(boolean value) throws IllegalDataStructException {

  }

  @Override
  public void setInt(int value) throws IllegalDataStructException {

  }

  @Override
  public void setLong(long value) throws IllegalDataStructException {

  }

  @Override
  public void setDouble(double value) throws IllegalDataStructException {

  }

  @Override
  public void setString(@NotNull String value) throws IllegalDataStructException {

  }

  @Override
  public void setUUID(@NotNull UUID value) throws IllegalDataStructException {

  }

  @Override
  public void setEnum(@NotNull Enum<?> value) throws IllegalDataStructException {

  }

  @Override
  public <E> void setCollection(@NotNull Collection<E> value, BiConsumer<@NotNull DataSetter, E> elementHandler) throws IllegalDataStructException {

  }

  @Override
  public <K, V> void setMap(@NotNull Map<K, V> value, @NotNull MappingSetterHandler<K, V> entryHandler) throws IllegalDataStructException {

  }

  @Override
  public @NotNull SectionDataProvider asSection() throws DataNotSectionableException {
    return null;
  }

}
