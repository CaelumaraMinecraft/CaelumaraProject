package net.aurika.configuration.validation;

import net.aurika.configuration.sections.ConfigSection;
import net.aurika.configuration.sections.label.Label;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Validator {

  public static List<ValidationFailure> validate(ConfigSection root, ConfigValidator schema, Map<String, ConfigValidator> customValidators) {
    Objects.requireNonNull(root);
    Objects.requireNonNull(schema);
    Objects.requireNonNull(customValidators);
    List<ValidationFailure> errors = new ArrayList<>();
    schema.validate(new ValidationContext(root, customValidators, errors));
    return errors;
  }

  static void removeComments(ConfigSection node) {   //todo
    boolean alreadyHaveNewLine = false;
    Iterator<CommentLine> iterator = node.getBlockComments().iterator();       //TODO node.getComments(CommentType.BLOCK_BEFORE).iterator();  可能有误

    while (iterator.hasNext()) {
      if (iterator.next().getValue().equals("\n")) {
        if (alreadyHaveNewLine) {
          iterator.remove();
        } else {
          alreadyHaveNewLine = true;
        }
      } else {
        iterator.remove();
      }
    }

    node.setInLineComments(null);
  }

//    static void implicitSchemaOf(MappingNode root) {     // 语义可能错误
//        NodeTuple noCommentsTuple;
//        Node newValue;
//        for (NodeTuple tuple : root.getValue()) {
//            noCommentsTuple = tuple;
//            Node valueNode = noCommentsTuple.getValueNode();
//            removeComments(noCommentsTuple.getKeyNode());
//            if (valueNode instanceof SequenceNode) {
//                MappingNode mappingNode = new MappingNode();
//                newValue = mappingNode;
//                mappingNode.getValue().add(new NodeTuple(new ScalarNode(Tag.STR, "(type)", null, null, ScalarStyle.PLAIN), new ScalarNode(Tag.STR, "list", null, null, ScalarStyle.PLAIN)));
//                mappingNode.getValue().add(new NodeTuple(new ScalarNode(Tag.STR, "(elements)", null, null, ScalarStyle.PLAIN), new ScalarNode(Tag.STR, "str", null, null, ScalarStyle.PLAIN)));
//            } else if (valueNode instanceof MappingNode) {
//                newValue = valueNode;
//                implicitSchemaOf((MappingNode) valueNode);
//            } else {
//                ScalarNode scalarNode = (ScalarNode) valueNode;
//                String tagVal = scalarNode.getTag().getValue();
//                if (scalarNode.getTag() == Tag.FLOAT) {
//                    tagVal = "decimal";
//                }
//
//                newValue = new ScalarNode(Tag.STR, tagVal, null, null, ScalarStyle.PLAIN);
//
//            }
//
//            root.getValue().remove(tuple);
//            noCommentsTuple.setValueNode(newValue);
//        }
//
//    }

  static void implicitSchemaOf(ConfigSection section) {
    Map.Entry<String, ConfigSection> entry;
    ConfigSection newValue;
    Map<String, ConfigSection> subSections = section.getSubSections();

    if (subSections != null) {

      for (Iterator<Map.Entry<String, ConfigSection>> iterator = section.getSubSections().entrySet().iterator(); iterator.hasNext(); subSections.remove(
          entry.getKey()), subSections.put(entry.getKey(), newValue)) {
        entry = iterator.next();
        ConfigSection valueSec = entry.getValue();
        removeComments(valueSec);
        if (valueSec.getLabel().equals(Label.SEQ)) {
          Map<String, ConfigSection> ssubSecs = new HashMap<>(2);

          newValue = new MemoryConfigSection(section, entry.getKey(), ssubSecs);

          ssubSecs.put("(type)", new MemoryConfigSection(section, "(type)", "list", Label.STR));
          ssubSecs.put("(elements)", new MemoryConfigSection(section, "(elements)", "str", Label.STR));
        } else if (valueSec.getLabel().equals(Label.MAP)) {
          newValue = valueSec;
          implicitSchemaOf(valueSec);
        } else {
          String scalarNode = valueSec.getConfigureString();
          String tagVal = valueSec.getLabel().getValue();
          if (valueSec.getLabel().equals(Label.FLOAT)) {
            tagVal = "decimal";
          }

          newValue = new ScalarNode(Tag.STR, tagVal, ScalarStyle.PLAIN);
        }
      }
    }
  }

  public static void implicitSchemaGenerator(ConfigSection root, Path to) {     //todo 可能的语义错误
    DumpSettings dumpSettings = DumpSettings.builder().build();
    Dump dumper = new Dump(dumpSettings);
    implicitSchemaOf(root);

    try {
      BufferedWriter writer = Files.newBufferedWriter(
          to, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

      try {
        dumper.dumpNode(root, new SimpleWriter(writer));   //todo choose writer
      } catch (Throwable throwable) {
        try {
          writer.close();
        } catch (Throwable suppressed) {
          throwable.addSuppressed(suppressed);
        }

        throw throwable;
      }

      writer.close();
    } catch (IOException ioExc) {
      ioExc.printStackTrace();
    }
  }

  static ConfigValidator parseStandardScalarType(Node option) {
    int minLen = 0;
    int maxLen = 0;
    String type;
    boolean optional;
    if (option instanceof ScalarNode) {
      type = ((ScalarNode) option).getValue();
      optional = type.endsWith("?");
      if (optional) {
        type = type.substring(0, type.length() - 1);
      }

      ConfigValidator mainValidator = null;
      if (type.startsWith("Enum<")) {
        String className = type.substring("Enum<".length(), type.length() - 1);

        Class enumerate;
        try {
          enumerate = Class.forName(className);
          if (!enumerate.isEnum()) {
            throw new IllegalStateException("Class '" + className + "' is not an enum");
          }
        } catch (ClassNotFoundException var9) {
          throw new IllegalStateException("Couldn't find class '" + className + '\'');
        }

        mainValidator = new EnumValidator(enumerate);
      } else {
        StandardSequenceValidator.Type standardList = StandardSequenceValidator.getStandardType(type);
        if (standardList != null) {
          mainValidator = new SequenceValidator(
              new StandardSequenceValidator(standardList, 0, 0),
              new net.aurika.common.snakeyaml.validation.StandardValidator(
                  net.aurika.common.snakeyaml.validation.StandardValidator.Type.STR, 0, 0)
          );
        }
      }

      if (mainValidator != null) {
        return new UnionValidator(
            new ConfigValidator[]{mainValidator, net.aurika.common.snakeyaml.validation.StandardValidator.NULL});
      }
    } else {
      if (!(option instanceof MappingNode)) {
        throw new IllegalStateException("Unexpected validation node: " + option.getStartMark());
      }

      NodeTuple typeNode = null;
      NodeTuple min = null;
      NodeTuple max = null;

      for (NodeTuple validatorSection : ((MappingNode) option).getValue()) {
        if (validatorSection.getValueNode() instanceof ScalarNode scalarNode) {
          switch (scalarNode.getValue()) {

            case "(type)" -> typeNode = validatorSection;
            case "(min)" -> min = validatorSection;
            case "(max)" -> max = validatorSection;
          }
        }
      }

      type = ((ScalarNode) typeNode.getValueNode()).getValue();
      if (min != null) {
//                minLen = (Integer) min.getValueNode().getParsed();
        minLen = (Integer) NodesKt.getParsed(min.getValueNode());
      }

      if (max != null) {
//                maxLen = (Integer) max.getValueNode().getParsed();
        maxLen = (Integer) NodesKt.getParsed(max.getValueNode());
      }

      if (maxLen == 0 && minLen != 0) {
        maxLen = Integer.MAX_VALUE;
      }

      if (minLen == 0 && maxLen < 0) {
        minLen = Integer.MIN_VALUE;
      }
    }

    optional = type.endsWith("?");
    if (optional) {
      type = type.substring(0, type.length() - 1);
    }

    net.aurika.common.snakeyaml.validation.StandardValidator.Type standardType = net.aurika.common.snakeyaml.validation.StandardValidator.getStandardType(
        type);
    ConfigValidator validator;
    if (standardType != null) {
      validator = new net.aurika.common.snakeyaml.validation.StandardValidator(standardType, minLen, maxLen);
    } else {
      validator = new net.aurika.auspice.config.yaml.snakeyaml.validation.ExternalConfigValidator(type);
    }

    return new UnionValidator(new ConfigValidator[]{validator, StandardValidator.NULL});
  }

  static ConfigValidator parseStandardSequenceType(Node option) {
    int minLen = 0;
    int maxLen = 0;
    String type = null;
    if (option instanceof ScalarNode) {
      type = ((ScalarNode) option).getValue();
    } else {
      if (!(option instanceof MappingNode)) {
        throw new IllegalStateException("Unexpected validation node: " + option.getStartMark());
      }

      NodeTuple typeNode;
      NodeTuple min;
      NodeTuple max;

      for (NodeTuple validatorTuple : ((MappingNode) option).getValue()) {
        if (validatorTuple.getKeyNode() instanceof ScalarNode scaNode) {
          String scaName = scaNode.getValue();
          if ("(type)".equals(scaName)) {
            typeNode = validatorTuple;
            type = (typeNode.getKeyNode().getAnchor().get().getValue());  //todo isPresent()
          }
          if ("(min)".equals(scaName)) {
            min = validatorTuple;
//                        minLen = (Integer) min.getValueNode().getParsed();
            minLen = (Integer) NodesKt.getParsed(min.getValueNode());
          }
          if ("(max)".equals(scaName)) {
            max = validatorTuple;
//                        maxLen = (Integer) max.getValueNode().getParsed();
            maxLen = (Integer) NodesKt.getParsed(max.getValueNode());
          }
        }
      }
    }

    StandardSequenceValidator.Type standardType = StandardSequenceValidator.getStandardType(type);
    return standardType != null ? new StandardSequenceValidator(
        standardType, minLen, maxLen) : new ExternalConfigValidator(type);
  }

  static Map<String, ConfigValidator> createMappedValidator(MappingNode node) {
    Map<String, ConfigValidator> repeatedValidator = new HashMap<>(node.getValue().size());
    Iterator<NodeTuple> var2 = node.getValue().iterator();

    while (true) {
      NodeTuple ruleTuple;
      String key;
      do {
        if (!var2.hasNext()) {
          return repeatedValidator;
        }

        ruleTuple = var2.next();
        key = ((ScalarNode) ruleTuple.getKeyNode()).getValue();
      } while (key.charAt(0) == '(' && key.charAt(key.length() - 1) == ')');

      Node rule = ruleTuple.getValueNode();
      ConfigValidator parsed = parseValidator(rule);
      repeatedValidator.put(key, parsed);
    }
  }

  static ConfigValidator parseStandardMapValidator(MappingNode mappingValidator) {
    ConfigValidator valuesValidator = null;
    ConfigValidator[] extendedValidators = null;
    ConfigValidator keyValidator = null;
    boolean isOptional = false;
    List<String> requiredKeys = null;
    List<String> valueValidatorKeys = null;

    for (NodeTuple validatorPair : mappingValidator.getValue()) {

      if (validatorPair.getKeyNode() instanceof ScalarNode scalarGeneralValidator) {
        if ("(values)".equals(scalarGeneralValidator.getValue())) {
          valuesValidator = parseValidator(validatorPair.getValueNode());
        }
        if ("(extends)".equals(scalarGeneralValidator.getValue())) {
          if (validatorPair.getValueNode() instanceof SequenceNode extendList) {
            List<ConfigValidator> extendedParsed = new ArrayList<>(extendList.getValue().size());
            for (Node node : extendList.getValue()) {
              extendedParsed.add(parseValidator(node));
            }
            extendedValidators = extendedParsed.toArray(new ConfigValidator[0]);
          } else {
            extendedValidators = new ConfigValidator[]{parseValidator(validatorPair.getValueNode())};
          }
        }
        if ("(keys)".equals(scalarGeneralValidator.getValue())) {
          keyValidator = parseValidator(validatorPair.getValueNode());
        }
        if ("(optional)".equals(scalarGeneralValidator.getValue())) {
//                    isOptional = validatorPair.getKeyNode().getParsed() == Boolean.TRUE;
          isOptional = NodesKt.getParsed(validatorPair.getKeyNode()) == Boolean.TRUE;
        }
        if ("(required)".equals(scalarGeneralValidator.getValue())) {
          if (!(validatorPair.getValueNode() instanceof SequenceNode seq)) {
            throw new IllegalStateException(
                "Expected a list here " + validatorPair.getValueNode().getStartMark() + validatorPair.getValueNode().getEndMark());
          }
          requiredKeys = new ArrayList<>();

          for (Node item : seq.getValue()) {
            requiredKeys.add(((ScalarNode) item).getValue());
          }
        }
        if ("(values-keys)".equals(scalarGeneralValidator.getValue())) {
          if (!(validatorPair.getValueNode() instanceof SequenceNode seq)) {
            throw new IllegalStateException(
                "Expected a list here " + validatorPair.getValueNode().getStartMark() + validatorPair.getValueNode().getEndMark());   //todo wholeMark
          }

          valueValidatorKeys = new ArrayList<>();

          for (Node item : seq.getValue()) {
            valueValidatorKeys.add(((ScalarNode) item).getValue());
          }
        }
      }
    }

    return new StandardMappingValidator(
        extendedValidators, keyValidator, valuesValidator, valueValidatorKeys, createMappedValidator(mappingValidator),
        requiredKeys, isOptional
    );
  }

  public static ConfigValidator parseSchema(MappingNode node) {
    return parseStandardMapValidator(node);
  }

  public static ConfigValidator parseValidator(Node localValidator) {
    if (localValidator instanceof ScalarNode) {
      return parseStandardScalarType(localValidator);
    } else if (!(localValidator instanceof SequenceNode seqNode)) {
      MappingNode mappingValidator = (MappingNode) localValidator;

      for (NodeTuple nodeTuple : mappingValidator.getValue()) {
        if (nodeTuple.getValueNode() instanceof ScalarNode scalarNode) {

          if ("(elements)".equals(scalarNode.getValue())) {

            ConfigValidator mainValidator = parseStandardSequenceType(localValidator);
            ConfigValidator elementsValidator = parseStandardScalarType(nodeTuple.getValueNode());
            return new SequenceValidator(mainValidator, elementsValidator);
          } else if ("(type)".equals(scalarNode.getValue())) {
            return parseStandardScalarType(localValidator);
          }
        }
      }

      return parseStandardMapValidator(mappingValidator);
    } else {
      Iterator<Node> var3;
      Node node;
      if (seqNode.getFlowStyle() == FlowStyle.FLOW) {
        Set<String> vals = new HashSet<>(seqNode.getValue().size());
        var3 = seqNode.getValue().iterator();

        while (var3.hasNext()) {
          node = var3.next();
          ScalarNode scalarItem = (ScalarNode) node;
          vals.add(scalarItem.getValue().toLowerCase(Locale.ENGLISH));
        }

        return new FixedValuedValidator(vals);
      } else {
        List<ConfigValidator> union = new ArrayList<>(seqNode.getValue().size());
        var3 = seqNode.getValue().iterator();

        while (var3.hasNext()) {
          node = var3.next();
          union.add(parseValidator(node));
        }

        return new UnionValidator(union.toArray(new ConfigValidator[0]));
      }
    }
  }

}
