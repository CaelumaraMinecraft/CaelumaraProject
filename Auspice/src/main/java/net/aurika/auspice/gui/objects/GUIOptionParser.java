package net.aurika.auspice.gui.objects;

import com.cryptomorin.xseries.XItemStack;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.google.common.base.Enums;
import com.google.common.base.Strings;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.configs.texts.MessageHandler;
import net.aurika.auspice.gui.objects.inventory.GUIInventoryConstructor;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.text.compiler.TextCompilerSettings;
import net.aurika.auspice.text.compiler.TextObject;
import net.aurika.auspice.text.compiler.pieces.TextPiece;
import net.aurika.auspice.utils.AuspiceLogger;
import net.aurika.auspice.utils.number.Numbers;
import net.aurika.auspice.utils.string.ConfigPrinter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.snakeyaml.engine.v2.exceptions.Mark;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class GUIOptionParser {

  private final String a;
  private final ConfigSection b;
  private final ConfigSection c;
  private final String d;
  private final GUIInventoryConstructor e;
  private TextObject f;
  private TextObject[] g;
  private TextObject h;
  private ItemStack i;
  private final ArrayList<GUIOptionObject> j;
  private final Map<Integer, String> k;
  private static final EnumMap<XMaterial, XMaterial> l;

  public GUIOptionParser(String var1, ConfigSection var2, ConfigSection var3, String var4, GUIInventoryConstructor var5, Map<Integer, String> var6, ArrayList<GUIOptionObject> var7) {
    this.a = var1;
    this.c = (ConfigSection) Objects.requireNonNull(
        var3, () -> {
          return "Null config: " + var1 + " | " + var4;
        }
    );
    this.b = (ConfigSection) Objects.requireNonNull(
        var2, () -> {
          return "Null root: " + var1 + " | " + var4;
        }
    );
    this.d = var4;
    this.e = var5;
    this.k = var6;
    this.j = var7;
  }

  public static XMaterial unsupportedMaterial(XMaterial var0) {
    return var0.isSupported() ? var0 : (XMaterial) l.getOrDefault(var0, XMaterial.STONE);
  }

  public static int[] rawSlotToXY(int var0) {
    return new int[]{var0 % 9 + 1, var0 / 9 + 1};
  }

  private ConditionalGUIOptionObject a(GUIOptionObject var1) {
    ArrayList var2 = new ArrayList(10);
    boolean var3 = false;
    Iterator var4 = this.c.getSections().entrySet().iterator();

    while (true) {
      Map.Entry var5;
      ConfigSection var6;
      do {
        if (!var4.hasNext()) {
          return new ConditionalGUIOptionObject(
              var1, (GUIOptionObjectCondition[]) var2.toArray(new GUIOptionObjectCondition[0]));
        }
      } while ((var6 = (ConfigSection) (var5 = (Map.Entry) var4.next()).getValue()) == null);

      String var7;
      ConditionCompiler.LogicalOperand var11;
      if (!Strings.isNullOrEmpty(var7 = var6.getString(new String[]{"condition"}))) {
        if (var3) {
          throw new IllegalStateException(
              "A conditional option found after the else condition '" + this.a + "' option in GUI '" + this.d + '\'');
        }

        try {
          var11 = ConditionCompiler.compile(var7).evaluate();
        } catch (Exception var8) {
          Mark var10 = ((ConfigSection) var5.getValue()).getNode("condition").getStartMark();
          KLogger.warn(
              "Failed to parse conditional '" + (String) var5.getKey() + "' for option '" + this.a + "' for GUI " + var10.getName() + ", line " + var10.getLine() + '\n' + var8.getMessage());
          continue;
        }
      } else {
        if (var3) {
          continue;
        }

        var3 = true;
        var11 = null;
      }

      GUIOptionParser var9 = new GUIOptionParser(
          (String) var5.getKey(), this.b, var6, this.d, this.e, this.k, this.extendParent(var1));
      var2.add(new GUIOptionObjectCondition(var11, var9.build()));
    }
  }

  public ArrayList<GUIOptionObject> extendParent(GUIOptionObject var1) {
    ArrayList var2;
    (var2 = (ArrayList) this.j.clone()).add(var1);
    return var2;
  }

  public GUIOptionBuilder build() {
    boolean var1 = false;
    String var2;
    if ((var2 = this.c.getString(new String[]{"[switch]"})) != null) {
      ConfigSection var12 = this.b.getSection(new String[]{"[" + var2 + ']'});
      ConditionCompiler.LogicalVariableOperand var10 = new ConditionCompiler.LogicalVariableOperand(var2);
      GUIOptionObject var4 = this.parsePure(this.a, this.c);
      ArrayList var9 = new ArrayList(10);
      Iterator var5 = var12.getSections().entrySet().iterator();

      GUIOptionParser var14;
      while (var5.hasNext()) {
        Map.Entry var6;
        String var7 = (String) (var6 = (Map.Entry) var5.next()).getKey();
        ConditionCompiler.BiLogicalOperator var8 = new ConditionCompiler.BiLogicalOperator(
            var10, ConditionCompiler.LogicalOperator.EQUALS, new ConditionCompiler.StringOperand(var7));
        var14 = new GUIOptionParser(
            var7, this.b, (ConfigSection) var6.getValue(), this.d, this.e, this.k, this.extendParent(var4));
        var9.add(new GUIOptionObjectCondition(var8, var14.build()));
      }

      ConfigSection var13;
      if ((var13 = var12.getSection(new String[]{"[else]"})) == null) {
        MessageHandler.sendConsolePluginMessage("&4Missing [else] option from GUI " + this.a + " | " + this.d);
      } else {
        var14 = new GUIOptionParser("[else]", this.b, var13, this.d, this.e, this.k, this.extendParent(var4));
        var9.add(new GUIOptionObjectCondition((ConditionCompiler.LogicalOperand) null, var14.build()));
      }

      return new ConditionalGUIOptionObject(
          var4, (GUIOptionObjectCondition[]) var9.toArray(new GUIOptionObjectCondition[0]));
    } else {

      for (ConfigSection section : this.c.getSubSections().values()) {
        if (!Strings.isNullOrEmpty(section.getString(new String[]{"condition"}))) {
          var1 = true;
          break;
        }
      }

      GUIOptionObject var11;
      if ((var11 = this.parsePure(this.a, this.c)) == null) {
        return null;
      } else {
        return (GUIOptionBuilder) (var1 ? this.a(var11) : var11);
      }
    }
  }

  private <T> T a(Predicate<GUIOptionObject> var1, Function<GUIOptionObject, T> var2) {
    ListIterator var4 = this.j.listIterator(this.j.size());

    GUIOptionObject var3;
    T var5;
    do {
      if (!var4.hasPrevious()) {
        return null;
      }

      var3 = (GUIOptionObject) var4.previous();
    } while ((var5 = var2.apply(var3)) == null);

    return var5;
  }

  public GUIOptionObject parsePure(String var1, ConfigSection var2) {
    ConfigSection var9 = var2;
    GUIOptionParser var6 = this;
    Function<GUIOptionObject, ?> var13;
    int var15;
    if ("AIR".equals(var2.getString(new String[]{"material"}))) {
      this.i = new ItemStack(Material.AIR);
    } else {
      label170:
      {
        try {
          var13 = (var0) -> {
            return var0.item;
          };
          ItemStack var10;
          if ((var10 = (ItemStack) var6.a((Predicate) null, (Function) var13)) == null) {
            var6.i = XItemStack.deserialize(
                var9.toBukkitConfigurationSection(), Function.identity(), var6.new a(var9, (byte) 0));
          } else {
            var6.i = XItemStack.edit(
                var10.clone(), var9.toBukkitConfigurationSection(), Function.identity(), var6.new a(var9, (byte) 0));
          }
        } catch (Exception var19) {
          Mark var11 = var2.getNode().getStartMark();
          AuspiceLogger.error(
              "Failed to parse item for option '" + this.a + "' for GUI " + this.d + ", line " + var11.getLine() + ":\n" + var19.getMessage());
          if (!(var19 instanceof XItemStack.MaterialCondition)) {
            var19.printStackTrace();
          }

          this.i = new ItemStack(Material.STONE);
          break label170;
        }

        ItemMeta var28;
        if ((var28 = this.i.getItemMeta()) != null) {
          String var31;
          if (!Strings.isNullOrEmpty(var31 = var2.getString(new String[]{"skull"})) && var31.contains("%")) {
            this.h = TextCompiler.compile(var31, TextCompilerSettings.none().translatePlaceholders());
            XSkull.of(var28).removeProfile();
          }

          var28.addItemFlags(XItemStack.ITEM_FLAGS);
          this.i.setItemMeta(var28);
          if (var28.hasDisplayName()) {
            this.f = TextCompiler.compile(
                var28.getDisplayName(), TextCompiler.defaultSettingsWithErrorHandler((var2x) -> {
                  Mark var3 = var2.getNode().getStartMark();
                  AuspiceLogger.warn(
                      "While parsing display name for '" + this.a + "' option in GUI '" + this.d + "', line " + var3.getLine() + ":\n" + var2x.joinExceptions());
                })
            );
          } else {
            var13 = (var0) -> {
              return var0.itemName;
            };
            this.f = (TextObject) this.a((Predicate) null, (Function) var13);
          }

          if (!var28.hasLore()) {
            var13 = (var0) -> {
              return var0.lore;
            };
            this.g = (TextObject[]) this.a((Predicate) null, (Function) var13);
          } else {
            List var12 = var28.getLore();
            List var14 = TextCompiler.compile(
                String.join("\n", var12), TextCompiler.defaultSettingsWithErroHandler((var2x) -> {
                  Mark var4 = var2.getNode().getStartMark();
                  int var3 = (int) var2x.getPieces().stream().filter((var0) -> {
                    return var0 instanceof TextPiece.NewLine;
                  }).count();
                  AuspiceLogger.warn("While parsing " + Numbers.toOrdinalNumeral(
                      var3) + " line of lore for '" + this.a + "' option in GUI '" + this.d + "', line " + var4.getLine() + ":\n" + var2x.joinExceptions());
                })
            ).splitBy(
                false, (var0) -> {
                  return var0 instanceof TextPiece.NewLine;
                }
            );
            this.g = new TextObject[var14.size()];

            for (var15 = 0; var15 < var14.size(); ++var15) {
              var6.g[var15] = (TextObject) var14.get(var15);
            }
          }
        }
      }
    }

    String var3 = var2.getString("sound");
    XSound.Record var20;
    if ("default".equalsIgnoreCase(var3)) {
      var20 = XSound.parse(AuspiceGlobalConfig.GUIS_DEFAULT_CLICK_SOUND.getString());
    } else if (var3 == null) {
      var13 = (var0) -> {
        return var0.sound;
      };
      var20 = (XSound.Record) this.a((Predicate) null, (Function) var13);
    } else {
      try {
        var20 = XSound.parse(var3);
      } catch (Exception var18) {
        var20 = null;
      }
    }

    List var4;
    if ((var4 = var2.getStringList(new String[]{"commands"})).isEmpty()) {
      var13 = (var0) -> {
        return var0.commands;
      };
      var4 = (List) this.a((Predicate) null, (Function) var13);
    }

    String var5;
    GUIInteractionType var21;
    if ((var5 = var2.getString(new String[]{"interaction"})) == null) {
      var13 = (var0) -> {
        return var0.interactionType;
      };
      if ((var21 = (GUIInteractionType) this.a((Predicate) null, (Function) var13)) == null) {
        var21 = GUIInteractionType.DISALLOW;
      }
    } else {
      com.google.common.base.Optional var22;
      if (!(var22 = Enums.getIfPresent(GUIInteractionType.class, var5)).isPresent()) {
        Mark var7 = var2.getNode("interaction").getStartMark();
        KLogger.warn(
            "While parsing the 'interaction' of " + var1 + "' option in GUI '" + this.d + "', line " + var7.getLine() + ":\nUnknown interaction type: " + var5);
      }

      var21 = (GUIInteractionType) var22.or(GUIInteractionType.DISALLOW);
    }

    TextObject var10000;
    String var23;
    if ((var23 = var2.getString(new String[]{"message"})) == null) {
      var13 = (var0) -> {
        return var0.message;
      };
      var10000 = (TextObject) this.a((Predicate) null, (Function) var13);
    } else {
      var10000 = TextCompiler.compile(
          var23, TextCompiler.defaultSettingsWithErrorHandler((var3x) -> {
            Mark var4 = var2.getNode("message").getStartMark();
            AuspiceLogger.warn(
                "While parsing the 'message' of " + var1 + "' option in GUI '" + this.d + "', line " + var4.getLine() + ":\n" + var3x.joinExceptions());
          })
      );
    }

    TextObject var25 = var10000;
    List<Integer> var24 = var2.getIntegerList(new String[]{"slots"});
    int[] var8 = null;
    boolean var30;
    if (!var24.isEmpty()) {
      int[] var29 = new int[(var24 = var24).size()];

      for (int var33 = 0; var33 < var29.length; ++var33) {
        var29[var33] = (Integer) var24.get(var33);
      }

      var8 = var29;
    } else {
      ConfigSection var26;
      int var27 = (var26 = this.c).getInteger(new String[]{"slot"});
      var30 = false;
      if (!var26.isSet(new String[]{"slot"})) {
        int var32 = 0;
        int var34 = 0;
        if (var26.isSet(new String[]{"posx"}) && var26.isSet(new String[]{"posy"})) {
          var32 = var26.getInteger(new String[]{"posx"});
          var34 = var26.getInteger(new String[]{"posy"});
        } else {
          var13 = (var0) -> {
            return var0.slots;
          };
          if ((var8 = (int[]) this.a((Predicate) null, (Function) var13)) != null) {
            var30 = true;
          }
        }

        if (!var30) {
          if (var32 <= 0 || var32 > 9) {
            MessageHandler.sendConsolePluginMessage(
                "&4Invalid GUI X position&8: &e" + var32 + " &7- &cMust be between 0 and 9");
            ConfigPrinter.printConfig(var26);
            return null;
          }

          if (var34 <= 0 || var34 > 6) {
            MessageHandler.sendConsolePluginMessage(
                "&4Invalid GUI Y position&8: &e" + var34 + " &7- &cMust be between 0 and 6");
            ConfigPrinter.printConfig(var26);
            return null;
          }

          var27 = var34 * 9 - (9 - var32) - 1;
        }
      }

      if (!var30) {
        var8 = new int[]{var27};
      }
    }

    var30 = !this.j.isEmpty();
    var6 = this;
    HashSet var35 = new HashSet(var8.length);
    boolean var36 = false;
    int[] var38 = var8;
    int var37 = var8.length;

    for (var15 = 0; var15 < var37; ++var15) {
      int var16 = var38[var15];
      if (!var35.add(var16)) {
        MessageHandler.sendConsolePluginMessage(
            "&4Repeated slot position &e" + var16 + " &4for option &e" + var6.a + " &4in GUI &e" + var6.d);
        var36 = true;
      } else if (var16 < 0) {
        MessageHandler.sendConsolePluginMessage(
            "&4Negative slot position &e" + var16 + " &4for option &e" + var6.a + " &4in GUI &e" + var6.d);
        var36 = true;
      } else if (var16 > var6.e.getMaxSize()) {
        MessageHandler.sendConsolePluginMessage(
            "&4Slot position &e" + var16 + " &4for option &e" + var6.a + " &4in GUI &e" + var6.d + " &4is greater than the allowed slot position in GUI of type &e" + var6.e.getType() + " &4with maximum slots of &e" + var6.e.getMaxSize());
        var36 = true;
      } else {
        String var17;
        if (!var30 && (var17 = (String) var6.k.get(var16)) != null) {
          MessageHandler.sendConsolePluginMessage(
              "&4Duplicated slot position &e" + var16 + " &4for option &e" + var6.a + " &4in GUI &e" + var6.d + " &4which is already taken by &e" + var17 + " &4option.");
          var36 = true;
        }
      }
    }

    if (!var36) {
      Arrays.stream(var8).forEach((var1x) -> {
        this.k.put(var1x, this.a);
      });
    }

    if (var36) {
      return null;
    } else {
      return new GUIOptionObject(var1, var8, var20, var4, var21, this.i, this.f, this.g, var25, this.h, var2);
    }
  }

  static {
    (l = new EnumMap(XMaterial.class)).put(XMaterial.LECTERN, XMaterial.CRAFTING_TABLE);
  }

  private final class a implements Consumer<Exception> {

    private final ConfigSection a;

    private a(ConfigSection var2) {
      this.a = var2;
    }

  }

}
