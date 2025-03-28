package net.aurika.config.sections.label;

import java.util.*;
import java.util.regex.Pattern;

public class StandardLabelResolver {

  public static final Pattern EMPTY = Pattern.compile("^$");
  @SuppressWarnings({"RegExpRedundantEscape", "RegExpUnnecessaryNonCapturingGroup"})
  public static final Pattern ENV_FORMAT = Pattern.compile("^\\$\\{\\s*(?:(\\w+)(?:(:?[-?])(\\w+)?)?)\\s*\\}$");
  public static final Pattern BOOL = Pattern.compile("^(?:true|True|TRUE|false|False|FALSE)$");
  public static final Pattern INT = Pattern.compile(
      "^([-+]?[0-9]+)" +                                           // (base 10)
          "|(0o[0-7]+)" +                                      // (base 8)
          "|(0x[0-9a-fA-F]+)$"                                 // (base 16)
  );
  public static final Pattern FLOAT = Pattern.compile(
      "^([-+]?(\\.[0-9]+|[0-9]+(\\.[0-9]*)?)([eE][-+]?[0-9]+)?)" + // float
          "|([-+]?\\.(?:inf|Inf|INF))" +                       // infinity
          "|(\\.(?:nan|NaN|NAN))$"                             // not a number
  );
  public static final Pattern NULL = Pattern.compile("^(?:~|null|Null|NULL| )$");

  protected static Map<Character, java.util.List<ResolverTuple>> yamlImplicitResolvers = new HashMap<>();

  public static Label standardObjectToLabel(Object obj) {
    if (obj == null) return Label.NULL;
    if (obj instanceof Map) return Label.MAP;
    if (obj instanceof List) return Label.SEQ;
    if (obj instanceof Set) return Label.SET;

    if (obj instanceof String) return resolveLabel(((String) obj));

    return Label.AUTO;
  }

  public static Label resolveLabel(String value) {
    if (value == null) {
      return Label.NULL;
    }
    java.util.List<ResolverTuple> resolvers;
    if (value.isEmpty()) {
      resolvers = yamlImplicitResolvers.get('\0');
    } else {
      resolvers = yamlImplicitResolvers.get(value.charAt(0));
    }
    if (resolvers != null) {
      for (ResolverTuple v : resolvers) {
        Label tag = v.getLabel();
        Pattern regexp = v.getRegexp();
        if (regexp.matcher(value).matches()) {
          return tag;
        }
      }
    }
    if (yamlImplicitResolvers.containsKey(null)) {
      for (ResolverTuple v : yamlImplicitResolvers.get(null)) {
        Label tag = v.getLabel();
        Pattern regexp = v.getRegexp();
        if (regexp.matcher(value).matches()) {
          return tag;
        }
      }
    }
    return Label.STR;
  }

  public static void addImplicitResolver(Label tag, Pattern regexp, String first) {
    if (first == null) {
      java.util.List<ResolverTuple> curr =
          yamlImplicitResolvers.computeIfAbsent(null, c -> new ArrayList<>());
      curr.add(new ResolverTuple(tag, regexp));
    } else {
      char[] chars = first.toCharArray();
      for (char aChar : chars) {
        Character theC = Character.valueOf(aChar);
        if (theC == 0) {
          // special case: for null
          theC = null;
        }
        List<ResolverTuple> curr = yamlImplicitResolvers.get(theC);
        if (curr == null) {
          curr = new ArrayList<>();
          yamlImplicitResolvers.put(theC, curr);
        }
        curr.add(new ResolverTuple(tag, regexp));
      }
    }
  }

  static {
    addImplicitResolver(Label.NULL, EMPTY, null);
    addImplicitResolver(Label.BOOL, BOOL, "tfTF");
    /*
     * INT must be before FLOAT because the regular expression for FLOAT matches INT
     */
    addImplicitResolver(Label.INT, INT, "-+0123456789");
    addImplicitResolver(Label.FLOAT, FLOAT, "-+0123456789.");
    addImplicitResolver(Label.NULL, NULL, "n\u0000");
    addImplicitResolver(Label.ENV_TAG, ENV_FORMAT, "$");
  }
}
