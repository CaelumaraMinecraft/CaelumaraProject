package net.aurika.common.snakeyaml.validation;

import net.aurika.common.snakeyaml.nodes.NodesKt;
import net.aurika.common.snakeyaml.validation.ValidationFailure.Severity;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.ScalarNode;
import org.snakeyaml.engine.v2.nodes.Tag;

import java.util.Objects;

public class StandardValidator implements NodeValidator {

  public static final StandardValidator NULL;
  private final Type type;
  private final int minLen;
  private final int maxLen;

  public StandardValidator(Type type, int minLen, int maxLen) {
    if (minLen != 0 || maxLen != 0) {
      if (minLen >= maxLen) {
        throw new IllegalArgumentException(
            "Validation range cannot be equal or smaller one greater than the bigger one: " + minLen + " - " + maxLen);
      }

      if (type != StandardValidator.Type.INT && type != StandardValidator.Type.DECIMAL) {
        throw new IllegalArgumentException("Cannot have range validation for type: " + type);
      }
    }

    this.type = Objects.requireNonNull(type);
    this.minLen = minLen;
    this.maxLen = maxLen;
  }

  public String toString() {
    return "StandardValidator<" + this.type + '>' + (this.maxLen == 0 && this.minLen == 0 ? "" : "{" + this.minLen + '-' + this.maxLen + '}');
  }

  static Type getTypeFromTag(Tag tag) {
    if (tag == Tag.STR) {
      return StandardValidator.Type.STR;
    } else if (tag == Tag.INT) {
      return StandardValidator.Type.INT;
    } else if (tag == Tag.FLOAT) {
      return StandardValidator.Type.DECIMAL;
    } else if (tag == Tag.BOOL) {
      return StandardValidator.Type.BOOL;
    } else {
      return tag == Tag.NULL ? StandardValidator.Type.NULL : null;
    }
  }

  public ValidationFailure validate(ValidationContext context) {
    if (!(context.getNode() instanceof ScalarNode)) {
      return context.fail(new ValidationFailure(
          Severity.ERROR, context.getNode(), context.getRelatedKey().getStartMark().get(),
          "Wrong type, expected '" + this.type.name + "' but got '" + context.getNode().getTag().getValue() + '\''
      ));
    } else if (this.type == StandardValidator.Type.ANY) {                                                                     //TODO isPresent()
      return null;
    } else {
      ScalarNode scalarNode = (ScalarNode) context.getNode();
      Type type = getTypeFromTag(scalarNode.getTag());
      if (type == null) {
        return context.err("Expected " + this.type.name + ", but got '" + scalarNode.getTag() + "'");
      } else if (type == StandardValidator.Type.NULL) {
        return null;
      } else {
        if (type != this.type) {
          if (this.type == StandardValidator.Type.STR) {
            if (scalarNode.getScalarStyle() == ScalarStyle.PLAIN) {
              context.warn(
                  "Expected a text here, got '" + type.name + "' instead. If this was intended, surround the value with single or double quotes.");
            }

            NodesKt.cacheConstructed(context.getNode(), scalarNode.getValue());
          } else if (this.type != StandardValidator.Type.DECIMAL || type != StandardValidator.Type.INT) {
            return context.err("Wrong type, expected '" + this.type.name + "' but got '" + type.name + '\'');
          }
        }

        scalarNode.setTag(type.getTag());
        if (this.minLen != 0 || this.maxLen != 0) {
          int length;
          if (this.type == StandardValidator.Type.STR) {
            length = NodesKt.parsed(scalarNode).toString().length();
          } else {
            length = ((Number) NodesKt.parsed(scalarNode)).intValue();
          }

          if (length < this.minLen) {
            return context.err("Value's length must be greater than " + this.minLen);
          }

          if (length > this.maxLen) {
            return context.err("Value's length must be less than " + this.maxLen);
          }
        }

        return null;
      }
    }
  }

  public String getName() {
    return this.type.name;
  }

  public static Type getStandardType(String str) {
    return switch (str) {
      case "int" -> Type.INT;
      case "decimal" -> Type.DECIMAL;
      case "bool" -> Type.BOOL;
      case "str" -> Type.STR;
      case "null" -> Type.NULL;
      case "any" -> Type.ANY;
      default -> null;
    };
  }

  static {
    NULL = new StandardValidator(StandardValidator.Type.NULL, 0, 0);
  }

  public static enum Type {
    INT("integer", Tag.INT),
    DECIMAL("decimal", Tag.FLOAT),
    BOOL("boolean", Tag.BOOL),
    STR("text", Tag.STR),
    NULL("null", Tag.NULL),
    ANY("any scalar", (Tag) null);

    private final String name;
    private final Tag tag;

    private Type(String name, Tag tag) {
      this.name = name;
      this.tag = tag;
    }

    public Tag getTag() {
      return this.tag;
    }

    public String getName() {
      return this.name;
    }
  }

}
