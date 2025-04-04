package net.aurika.config.yaml.part.adapter;

import net.aurika.config.yaml.part.YamlConfigScalar;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.ScalarNode;

import java.util.Objects;
import java.util.Optional;

public class DefaultYamlConfigScalarAdapter extends DefaultYamlConfigPartAdapter<YamlConfigScalar> implements YamlConfigScalarAdapter<YamlConfigScalar> {

  protected static @NotNull ScalarNode getScalar(@NotNull YamlConfigScalar configScalar) {
    Validate.Arg.notNull(configScalar, "configScalar");
    return configScalar.yamlNode();
  }

  public DefaultYamlConfigScalarAdapter(@NotNull YamlConfigAdapterSettings settings) {
    super(YamlConfigScalar.class, settings);
  }

  @Override
  public boolean isNull(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return scalarNode.isPlain() && Objects.equals(scalarNode.getValue(), "~");
  }

  @Override
  public boolean getBoolean(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((Boolean) settings.constructor.constructSingleDocument(Optional.of(scalarNode)));
  }

  @Override
  public char getChar(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((Character) settings.constructor.constructSingleDocument(Optional.of(scalarNode)));
  }

  @Override
  public byte getByte(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((Number) settings.constructor.constructSingleDocument(Optional.of(scalarNode))).byteValue();
  }

  @Override
  public short getShort(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((Number) settings.constructor.constructSingleDocument(Optional.of(scalarNode))).shortValue();
  }

  @Override
  public int getInt(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((Number) settings.constructor.constructSingleDocument(Optional.of(scalarNode))).intValue();
  }

  @Override
  public long getLong(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((Number) settings.constructor.constructSingleDocument(Optional.of(scalarNode))).longValue();
  }

  @Override
  public float getFloat(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((Number) settings.constructor.constructSingleDocument(Optional.of(scalarNode))).floatValue();
  }

  @Override
  public double getDouble(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((Number) settings.constructor.constructSingleDocument(Optional.of(scalarNode))).doubleValue();
  }

  @Override
  public @NotNull Number getNumber(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((Number) settings.constructor.constructSingleDocument(Optional.of(scalarNode)));
  }

  @Override
  public @NotNull String getString(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    return ((CharSequence) settings.constructor.constructSingleDocument(Optional.of(scalarNode))).toString();
  }

  @Override
  public void setNull(@NotNull YamlConfigScalar configScalar) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setBoolean(@NotNull YamlConfigScalar configScalar, boolean value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setChar(@NotNull YamlConfigScalar configScalar, char value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setByte(@NotNull YamlConfigScalar configScalar, byte value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setShort(@NotNull YamlConfigScalar configScalar, short value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setInt(@NotNull YamlConfigScalar configScalar, int value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setLong(@NotNull YamlConfigScalar configScalar, long value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setFloat(@NotNull YamlConfigScalar configScalar, float value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setDouble(@NotNull YamlConfigScalar configScalar, double value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setNumber(@NotNull YamlConfigScalar configScalar, @NotNull Number value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

  @Override
  public void setString(@NotNull YamlConfigScalar configScalar, @NotNull String value) {
    ScalarNode scalarNode = getScalar(configScalar);
    // TODO \n\1234
  }

}
