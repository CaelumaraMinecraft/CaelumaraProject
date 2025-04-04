package net.aurika.auspice.utils.string;

import net.aurika.auspice.configs.messages.MessageHandler;
import net.aurika.configuration.sections.ConfigSection;
import net.aurika.util.stacktrace.StackTraces;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.comments.CommentLine;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.nodes.*;

import java.util.*;
import java.util.stream.Collectors;

public final class ConfigPrinter {

  private ConfigPrinter() {
  }

  public static void printConfig(ConfigurationSection var0) {
    if (var0 == null) {
      MessageHandler.sendConsolePluginMessage("&4Attempt to print null config section");
      StackTraces.printStackTrace();
    } else {
      MessageHandler.sendConsolePluginMessage("&4" + var0.getName() + " &7->");
      printConfig(var0, "");
    }
  }

  public static void printConfig(ConfigurationSection var0, String var1) {
    Iterator<Map.Entry<String, Object>> var5 = var0.getValues(false).entrySet().iterator();

    while (true) {
      while (var5.hasNext()) {
        Map.Entry<String, Object> var2 = var5.next();
        Object var3 = var2.getValue();
        if (var3 instanceof ConfigurationSection) {
          MessageHandler.sendConsolePluginMessage(var1 + "&6" + var2.getKey() + "&8:");
          printConfig((ConfigurationSection) var3, var1 + ' ');
        } else {
          String var4 = Objects.toString(var3);
          if (var3 == null) {
            var4 = "&4null";
          } else if (var3 instanceof String) {
            if (!var4.contains(" ") && Strings.isEnglish(var4)) {
              var4 = "&f" + var4;
            } else {
              var4 = "&f\"" + var4 + "&f\"";
            }
          } else if (var3 instanceof Integer) {
            var4 = "&9" + var4;
          } else if (var3 instanceof Number) {
            var4 = "&5" + var4;
          } else if (var3 instanceof Boolean) {
            var4 = ((Boolean) var3 ? "&a" : "&c") + var4;
          } else if (var3 instanceof List) {
            String[] var6 = (String[]) ((List<String>) var3).stream().map(Object::toString).toArray(String[]::new);
            var4 = "&3[&b" + String.join("&7, &b", var6) + "&3]";
          } else {
            var4 = "&e" + var4;
          }

          MessageHandler.sendConsolePluginMessage(var1 + "&6" + var2.getKey() + "&8: " + var4);
        }
      }

      return;
    }
  }

  public static void printConfig(ConfigSection section) {

    for (String var1 : printNode(section.getRoot(), "")) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', var1));
    }
  }

  public static List<String> printNode(Node var0, String var1) {
    ArrayList<String> var2 = new ArrayList(1);
    Iterator var3;
    if (!var0.getComments().isEmpty()) {
      var3 = var0.getComments().iterator();

      while (var3.hasNext()) {
        CommentLine var4 = (CommentLine) var3.next();
        switch (var4.getCommentType()) {
          case BLOCK_BEFORE:
            var2.add(var1 + "&8# " + var4.getValue());
            break;
          case BLANK_LINE:
            var2.add(var1);
        }
      }
    }

    Iterator var9;
    label73:
    switch (var0.getNodeType()) {
      case SCALAR:
        var2.addAll(a(null, var1, var0));
        break;
      case SEQUENCE:
        SequenceNode var12;
        List var15 = (var12 = (SequenceNode) var0).getValue().stream().flatMap((var1x) -> {
          return printNode(var1x, var1).stream();
        }).collect(Collectors.toList());
        switch (var12.getFlowStyle()) {
          case AUTO:
          case FLOW:
            var2.add(a(var12) + "&8[&6" + String.join("&7, &6", var15) + "&8]");
            return var2;
          case BLOCK:
            var2.add(var1 + a(var12));
            var9 = var15.iterator();

            while (var9.hasNext()) {
              String var14 = (String) var9.next();
              var2.add(var1 + "&8- &6" + var14);
            }
            break label73;
          default:
            return var2;
        }
      case MAPPING:
        MappingNode var10 = (MappingNode) var0;
        String var13 = a(var0);
        switch (var10.getFlowStyle()) {
          case AUTO:
          case BLOCK:
            List var18;
            for (var9 = var10.getValue().iterator(); var9.hasNext(); var2.addAll(var18)) {
              NodeTuple var11 = (NodeTuple) var9.next();
              List var16;
              if ((var16 = a("&f", var1, var11.getKeyNode())).size() != 1) {
                throw new IllegalStateException("Key size not 1: " + var16);
              }

              String var17 = var1 + var16.get(0) + "&8: " + var13;
              var18 = printNode(var11.getValueNode(), var1 + "  ");
              if (var11.getValueNode() instanceof MappingNode && ((MappingNode) var11.getValueNode()).getFlowStyle() != FlowStyle.FLOW) {
                var2.add(var17);
              } else {
                var18.set(0, var17 + var18.get(0));
              }
            }

            return var2;
          case FLOW:
            StringJoiner var8 = new StringJoiner("&7, ", var13 + "&8{ ", " &8}");
            var3 = var10.getValue().iterator();

            while (var3.hasNext()) {
              NodeTuple var5 = (NodeTuple) var3.next();
              List<String> var6 = a("&f", var1, var5.getKeyNode());
              if (var6.size() != 1) {
                throw new IllegalStateException("Key size not 1: " + var6);
              }

              String var7 = var6.get(0) + "&8: ";
              var13 = String.join("\n" + var1, printNode(var5.getValueNode(), var1));
              var8.add(var7 + var13);
            }

            var2.add(var8.toString());
            return var2;
          default:
            return var2;
        }
      default:
        throw new IllegalArgumentException("Unsupported node type " + var0.getNodeType());
    }

    return var2;
  }

  private static String a(Node var0) {
    NodeReference var2 = var0.getReference();
    if (var2 == null) {
      return "";
    } else if (var2 instanceof AnciasNodeReference var1) {
      switch (var1.getType()) {
        case ALIAS:
          return "&5*" + var1.getReference().getIdentifier() + ' ';
        case ANCHOR:
          return "&5&n&" + var1.getReference().getIdentifier() + ' ';
        default:
          throw new AssertionError("Unknown ref: " + var2);
      }
    } else if (var2 instanceof CrossNodeReference) {
      return "";
    } else {
      throw new AssertionError("Unknown ref " + var2);
    }
  }

  private static @NotNull List<String> a(String var0, String var1, Node var2) {
    String var3 = ((ScalarNode) var2).getValue();
    Tag var4 = var2.getTag();
    String var5 = "";
    if (var0 != null) {
      var3 = var0 + var3;
    } else if (var4 == Tag.INT) {
      var3 = "&2" + var3;
    } else if (var4 == Tag.FLOAT) {
      var3 = "&3" + var3;
    } else if (var4 == Tag.STR) {
      var3 = "&6" + var3;
    } else if (var4 == Tag.BOOL) {
      var3 = "&9" + var3;
    } else if (var4 == Tag.NULL) {
      var3 = "&c" + var3;
    } else {
      var5 = "&e&n!!" + var4.getValue() + ' ';
      var3 = "&6" + var3;
    }

    ArrayList var6 = new ArrayList();
    String var8 = var5 + a(var2);
    String[] var7;
    int var9;
    int var10;
    switch (((ScalarNode) var2).getScalarStyle()) {
      case SINGLE_QUOTED:
        var3 = var8 + "&e'" + var3 + "&e'";
        var6.add(var3);
        break;
      case DOUBLE_QUOTED:
        var3 = var8 + "&e\"" + var3 + "&e\"";
        var6.add(var3);
        break;
      case LITERAL:
        var6.add(var8 + "&e|");
        var10 = (var7 = var3.split("\n")).length;

        for (var9 = 0; var9 < var10; ++var9) {
          var5 = var7[var9];
          var6.add(var1 + "  " + var5);
        }

        return var6;
      case FOLDED:
        var6.add(var8 + "&e>");
        var10 = (var7 = var3.split("\n")).length;

        for (var9 = 0; var9 < var10; ++var9) {
          var5 = var7[var9];
          var6.add(var1 + "  " + var5);
        }

        return var6;
      default:
        var6.add(var8 + var3);
    }

    return var6;
  }

}