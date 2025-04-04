package net.aurika.configuration.sections.interpreter;

import net.aurika.configuration.sections.ConfigSection;
import net.aurika.configuration.translator.Translator;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MapInterpreter<K, V> implements SectionInterpreter<Map<K, V>> {

  protected final Translator<K> keyTranslator;
  protected final SectionInterpreter<V> valueInterpreter;

  public MapInterpreter(Translator<K> keyTranslator, SectionInterpreter<V> valueInterpreter) {
    this.keyTranslator = keyTranslator;
    this.valueInterpreter = valueInterpreter;
  }

  @Override
  public Map<K, V> parse(@NotNull SectionInterpretContext<Map<K, V>> context) throws ConfigInterpretException {
    ConfigSection section = context.getSection();
    if (section == null) {
      return null;
    }

    Map<String, ConfigSection> subSections = section.getSubSections();

    Map<K, V> out = new HashMap<>();

    for (Map.Entry<String, ConfigSection> subSecEntry : subSections.entrySet()) {  //TODO 将可能的多个问题合并到同一个问题中
      out.put(keyTranslator.translate(subSecEntry.getKey()), valueInterpreter.parse(subSecEntry.getValue()));
    }

    // https://github.com/Mc-Kingdom-Top/KingdomsThrone.git
    return out;
  }

}
