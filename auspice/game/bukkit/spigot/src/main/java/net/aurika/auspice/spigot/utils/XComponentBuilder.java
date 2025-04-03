package net.aurika.auspice.spigot.utils;

import com.cryptomorin.xseries.reflection.XReflection;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class XComponentBuilder {

  public static final int MAXIMUM_JSON_CHAT_PACKET_SIZE = 262144;
  public static final int SUGGESTED_JSON_CHAT_PACKET_SIZE_RENEWAL = 200000;
  private final List<List<BaseComponent>> a;
  private List<BaseComponent> b;
  private BaseComponent c;

  public static TextComponent duplicate(TextComponent var0) {
    return XReflection.supports(15) ? var0.duplicate() : new TextComponent(var0);
  }

  public XComponentBuilder(String var1) {
    this();
    this.c = new TextComponent(var1);
  }

  public XComponentBuilder() {
    this.a = new ArrayList<>(1);
    this.b = new ArrayList<>();
    this.a.add(this.b);
  }

  public static void copyFormatting(BaseComponent var0, BaseComponent var1, FormatRetention var2) {
    if (var2 != FormatRetention.NONE) {
      if (var2 == FormatRetention.EVENTS || var2 == FormatRetention.ALL) {
        if (var0.getClickEvent() == null) {
          var0.setClickEvent(var1.getClickEvent());
        }

        if (var0.getHoverEvent() == null) {
          var0.setHoverEvent(var1.getHoverEvent());
        }
      }

      if (var2 == FormatRetention.FORMATTING || var2 == FormatRetention.ALL) {
        if (var0.getColorRaw() == null) {
          var0.setColor(var1.getColorRaw());
        }

        if (var0.isBoldRaw() == null) {
          var0.setBold(var1.isBoldRaw());
        }

        if (var0.isItalicRaw() == null) {
          var0.setItalic(var1.isItalicRaw());
        }

        if (var0.isUnderlinedRaw() == null) {
          var0.setUnderlined(var1.isUnderlinedRaw());
        }

        if (var0.isStrikethroughRaw() == null) {
          var0.setStrikethrough(var1.isStrikethroughRaw());
        }

        if (var0.isObfuscatedRaw() == null) {
          var0.setObfuscated(var1.isObfuscatedRaw());
        }

        if (var0.getInsertion() == null) {
          var0.setInsertion(var1.getInsertion());
        }

        if (XReflection.supports(16) && var0.getFontRaw() == null) {
          var0.setFont(var1.getFont());
        }
      }
    }
  }

  public XComponentBuilder append(BaseComponent var1) {
    return this.append(var1, FormatRetention.ALL);
  }

  public XComponentBuilder append(BaseComponent var1, FormatRetention var2) {
    Objects.requireNonNull(var1, "Cannot append null component");
    if (this.c == null) {
      this.c = var1;
      return this;
    } else {
      this.b.add(this.c);
      BaseComponent var3 = this.c;
      this.c = var1;
      copyFormatting(this.c, var3, var2);
      return this;
    }
  }

  public void newPacket() {
    this.a.add(this.b);
    this.b = new ArrayList<>(10);
  }

  public XComponentBuilder append(String var1) {
    return this.append(var1, FormatRetention.ALL);
  }

  public XComponentBuilder append(String var1, FormatRetention var2) {
    this.append(new TextComponent(var1), var2);
    return this;
  }

  public BaseComponent[] createSingular() {
    if (this.a.size() != 1) {
      throw new IllegalStateException("More than one packet: " + this.a.size());
    } else {
      return this.create()[0];
    }
  }

  public BaseComponent[][] create() {
    if (this.b.isEmpty()) {
      return this.c == null ? new BaseComponent[0][0] : new BaseComponent[][]{{this.c}};
    } else {
      Objects.requireNonNull(this.c);
      BaseComponent[][] var1 = new BaseComponent[this.a.size()][];

      for (int var2 = 0; var2 < this.a.size(); ++var2) {
        List<BaseComponent> var3 = this.a.get(var2);
        int var4 = var3.size();
        var1[var2] = var3.toArray(new BaseComponent[var4 + 1]);
        var1[var2][var4] = this.c;
      }

      return var1;
    }
  }

  public TextComponent buildTextComponent() {
    TextComponent var1 = new TextComponent();
    if (this.c != null) {
      this.b.add(this.c);
    }

    var1.setExtra(this.b);
    return var1;
  }

  @Deprecated
  public static BaseComponent[] fromLegacyText(String var0) {
    ArrayList<TextComponent> var1 = new ArrayList<>();
    StringBuilder var2 = new StringBuilder();
    TextComponent var3 = new TextComponent();

    for (int var4 = 0; var4 < var0.length(); ++var4) {
      int var5;
      if ((var5 = var0.charAt(var4)) == '§') {
        ++var4;
        if (var4 >= var0.length()) {
          break;
        }

        if ((var5 = var0.charAt(var4)) >= 'A' && var5 <= 'Z') {
          var5 = (char) (var5 + 32);
        }

        ChatColor var8;
        if (var5 == 'x' && var4 + 12 < var0.length()) {
          StringBuilder var6 = new StringBuilder("#");

          for (var5 = 0; var5 < 6; ++var5) {
            var6.append(var0.charAt(var4 + 2 + (var5 << 1)));
          }

          try {
            var8 = ChatColor.of(var6.toString());
          } catch (IllegalArgumentException var7) {
            var8 = null;
          }

          var4 += 12;
        } else {
          var8 = ChatColor.getByChar((char) var5);
        }

        if (var8 != null) {
          if (!var2.isEmpty()) {
            TextComponent var9 = var3;
            var3 = new TextComponent(var3);
            var9.setText(var2.toString());
            var2.setLength(0);
            var1.add(var9);
          }

          if (var8 == ChatColor.BOLD) {
            var3.setBold(Boolean.TRUE);
          } else if (var8 == ChatColor.ITALIC) {
            var3.setItalic(Boolean.TRUE);
          } else if (var8 == ChatColor.UNDERLINE) {
            var3.setUnderlined(Boolean.TRUE);
          } else if (var8 == ChatColor.STRIKETHROUGH) {
            var3.setStrikethrough(Boolean.TRUE);
          } else if (var8 == ChatColor.MAGIC) {
            var3.setObfuscated(Boolean.TRUE);
          } else if (var8 == ChatColor.RESET) {
            var8 = ChatColor.WHITE;
            (var3 = new TextComponent()).setColor(var8);
          } else {
            (var3 = new TextComponent()).setColor(var8);
          }
        }
      } else {
        var2.append((char) var5);
      }
    }

    var3.setText(var2.toString());
    var1.add(var3);
    return var1.toArray(new BaseComponent[0]);
  }

  public static void resetFormats(BaseComponent baseComponent) {
    baseComponent.setBold(Boolean.FALSE);
    baseComponent.setItalic(Boolean.FALSE);
    baseComponent.setUnderlined(Boolean.FALSE);
    baseComponent.setStrikethrough(Boolean.FALSE);
    baseComponent.setObfuscated(Boolean.FALSE);
  }

}

