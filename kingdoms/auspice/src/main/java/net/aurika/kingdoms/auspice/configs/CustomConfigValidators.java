package net.aurika.kingdoms.auspice.configs;

import net.aurika.kingdoms.auspice.AuspiceAddon;
import org.kingdoms.utils.config.adapters.YamlContainer;
import org.snakeyaml.nodes.NodeType;
import org.snakeyaml.nodes.Tag;
import org.snakeyaml.validation.NodeValidator;
import org.snakeyaml.validation.ValidationContext;
import org.snakeyaml.validation.ValidationFailure;

public class CustomConfigValidators {

  private static final Tag BILL = new Tag("Bill");
  private static final NodeValidator b = YamlContainer.parseValidator(
      AuspiceAddon.get().getResource("schemas/bill.yml"), "Bill");

  public static void init() {
    org.kingdoms.utils.config.CustomConfigValidators.register(BILL.getValue(), new c());
  }

  private static final class c implements NodeValidator {

    @Override
    public ValidationFailure validate(ValidationContext context) {
      Tag tag = context.getNode().getTag();
      if (tag == BILL) {
        return null;
      } else {
        return context.getNode().getNodeType() != NodeType.MAPPING ? context.err(
            "Expected an bill, instead got " + tag) : b.validate(context);
      }
    }

  }

}
