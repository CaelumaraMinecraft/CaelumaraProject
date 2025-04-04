package net.aurika.configuration.translator;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface MapTranslator<K, V> extends Translator<Map<K, V>> {

  @NotNull
  @Override
  default Class<? super Map<K, V>> getOutputType() {
    return Map.class;
  }

  ;
}
