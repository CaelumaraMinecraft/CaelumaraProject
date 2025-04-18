package net.aurika.wands.constants.spells.types;

import net.aurika.auspice.config.sections.interpreter.MapInterpreter;
import net.aurika.auspice.config.sections.interpreter.SectionInterpreter;
import net.aurika.auspice.config.translator.NamespaceTranslator;
import net.aurika.auspice.key.NSedKey;
import net.aurika.wands.configs.WandsGlobalConfig;

import java.util.Map;

public interface CountableSpellType extends SpellType {

  int getDefaultCountNoCfg();  //TODO rename

  /**
   * 获取该法术默认的剩余发射数, 若要无限发射, 则返回 {@code -1}
   */
  default int getDefaultCount() {  //TODO rename
    try {
      Map<NSedKey, Integer> cts = WandsGlobalConfig.SPELL_SHOOT_COUNTS.getManager().getSection().getSection().asObject(
          new MapInterpreter<>(NamespaceTranslator.INSTANCE, SectionInterpreter.INTEGER));
      if (cts == null) {
        return this.getDefaultCountNoCfg();
      } else {
        return cts.get(this.getNamespacedKey());
      }
    } catch (Exception e) {
      return this.getDefaultCountNoCfg();
    }
  }

}
