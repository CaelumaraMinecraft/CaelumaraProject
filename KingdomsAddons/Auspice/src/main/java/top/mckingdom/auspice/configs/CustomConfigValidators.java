package top.mckingdom.auspice.configs;

import org.kingdoms.libs.snakeyaml.nodes.NodeType;
import org.kingdoms.libs.snakeyaml.nodes.Tag;
import org.kingdoms.libs.snakeyaml.validation.NodeValidator;
import org.kingdoms.libs.snakeyaml.validation.ValidationContext;
import org.kingdoms.libs.snakeyaml.validation.ValidationFailure;
import org.kingdoms.utils.config.adapters.YamlContainer;
import top.mckingdom.auspice.AuspiceAddon;

public class CustomConfigValidators {

    private static final Tag BILL = new Tag("Bill");
    private static final NodeValidator b = YamlContainer.parseValidator(AuspiceAddon.get().getResource("schemas/bill.yml"), "Bill");

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
                return context.getNode().getNodeType() != NodeType.MAPPING ? context.err("Expected an bill, instead got " + tag) : b.validate(context);
            }
        }
    }
}
