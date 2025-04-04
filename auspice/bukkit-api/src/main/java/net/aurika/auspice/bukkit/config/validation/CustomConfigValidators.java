package net.aurika.auspice.bukkit.config.validation;

import com.cryptomorin.xseries.XEnchantment;
import net.aurika.configuration.sections.ConfigSection;
import net.aurika.configuration.sections.label.Label;
import net.aurika.configuration.validation.ConfigValidator;
import net.aurika.configuration.validation.ConfigValidators;
import net.aurika.configuration.validation.ValidationContext;
import net.aurika.configuration.validation.ValidationFailure;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;

import java.util.Locale;
import java.util.Optional;

public final class CustomConfigValidators {

  public static final Label ENCHANT = ConfigValidators.register("Enchant", EnchantValidator.INSTANCE);

  public static final class EnchantValidator implements ConfigValidator {

    public static final EnchantValidator INSTANCE = new EnchantValidator();

    private EnchantValidator() {
    }

    public ValidationFailure validate(ValidationContext context) {
      ConfigSection section = context.getSection();
      Label label = section.getLabel();
      if (label == ENCHANT) {
        return null;
      } else if (label != Label.AUTO && label != Label.STR) {
        return context.err("Expected an enchantment");
      } else {
        String str = section.getConfigureString();

        Optional<XEnchantment> xEnchantment = XEnchantment.matchXEnchantment(str);

        if (xEnchantment.isEmpty()) {
          NamespacedKey encNsKey;
          int sep = str.indexOf(':');
          if (sep == -1) {
            encNsKey = NamespacedKey.minecraft(str.toLowerCase(Locale.ENGLISH));
          } else {
            String namespace = str.substring(0, sep);
            String key = str.substring(sep + 1);
            encNsKey = new NamespacedKey(namespace, key);
          }
          Enchantment enchantment = Registry.ENCHANTMENT.get(encNsKey);
          if (enchantment == null) {
            return context.err("Unknown enchantment '" + str + '\'');
          } else {
            section.setParsedValue(enchantment);
          }
          return null;   //TODO return value
        } else {
          section.setLabel(ENCHANT);
          section.setParsedValue(xEnchantment.get().getEnchant());
          return null;
        }
      }
    }

  }

}

