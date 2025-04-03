package net.aurika.auspice.text.compiler.pieces;

import net.aurika.auspice.configs.messages.placeholders.context.PlaceholderContextBuilder;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.PlaceholderTranslationContext;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.text.compiler.TextCompilerSettings;
import net.aurika.auspice.text.compiler.builders.TextObjectBuilder;
import net.aurika.auspice.text.compiler.builders.context.ComplexTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.builders.context.HTMLTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.builders.context.PlainTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.builders.context.TextBuilderContextProvider;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.auspice.text.context.TextContext;
import net.aurika.auspice.utils.Pair;
import net.aurika.auspice.utils.chat.BaseColorUtils;
import net.aurika.auspice.utils.chat.TextFormatCodes;
import net.aurika.auspice.utils.compiler.condition.ConditionCompiler;
import net.aurika.auspice.utils.conditions.ConditionProcessor;
import net.aurika.validate.Validate;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

import static net.aurika.auspice.text.compiler.builders.context.ComplexTextBuilderContextProvider.*;

public abstract class TextPiece implements AbstractTextPiece {

  protected TextPiece() {
  }

  public void build(TextBuilderContextProvider builderProvider) {
    if (builderProvider instanceof ComplexTextBuilderContextProvider complex) this.build(complex);
    if (builderProvider instanceof PlainTextBuilderContextProvider plain) this.build(plain);
    if (builderProvider instanceof HTMLTextBuilderContextProvider html) this.build(html);
    throw new UnsupportedOperationException(
        "Unsupported " + TextBuilderContextProvider.class.getSimpleName() + ": " + builderProvider);
  }

  public abstract void build(ComplexTextBuilderContextProvider builderProvider);

  public abstract void build(PlainTextBuilderContextProvider builderProvider);

  public abstract void build(HTMLTextBuilderContextProvider builderProvider);

  public abstract int length();

  public abstract int jsonLength();

  public static final class Hover extends TextPiece {

    private final ClickEvent.Action action;
    private final TextPiece[] normalMessage;
    private final TextPiece[] hoverMessage;
    private final TextPiece[] clickAction;
    private static final int json_template = """
        {
          "clickEvent": {
            "action": "ACTION",
            "value": "VALUE"
          },
          "hoverEvent": {
            "action": "show_text",
            "contents": []
          },
          "extra": [],
          "text": ""
        },
        """
        .replace(" ", "").length();

    public Hover(ClickEvent.Action action, TextPiece[] normalMessage, TextPiece[] hoverMessage, TextPiece[] clickAction) {
      this.action = action;
      this.normalMessage = Objects.requireNonNull(normalMessage, "Normal message in a hover message must be provided");
      this.hoverMessage = hoverMessage;
      this.clickAction = clickAction;
    }

    public void build(ComplexTextBuilderContextProvider builderProvider) {
      PlainTextBuilderContextProvider clickActionBuilder = new PlainTextBuilderContextProvider(
          builderProvider.settings());

      for (TextPiece textPiece : this.clickAction) {
        textPiece.build(clickActionBuilder);
      }

      ClickEvent clickEvent = this.action == null ? null : ClickEvent.clickEvent(
          this.action, clickActionBuilder.merge());

      // 复用文本为空的 TextComponent.Builder
      TextComponent.Builder plainTextBuilder = builderProvider.getCurrentComponentBuilder().content().isEmpty() ? builderProvider.getCurrentComponentBuilder() : builderProvider.newComponentBuilder(
          FORMATTING_MERGES);

      ComplexTextBuilderContextProvider normalMessageBuilder = new ComplexTextBuilderContextProvider(
          builderProvider, plainTextBuilder, builderProvider.settings());

      for (TextPiece normalMessage : this.normalMessage) {
        normalMessageBuilder.build(normalMessage);
      }

      normalMessageBuilder.appendRemaining();
      ComplexTextBuilderContextProvider hoverMessageBuilder = new ComplexTextBuilderContextProvider(
          builderProvider, builderProvider.settings());

      for (TextPiece hoverMessage : this.hoverMessage) {
        hoverMessageBuilder.build(hoverMessage);
      }

      hoverMessageBuilder.appendRemaining();
      HoverEvent<Component> hoverEvent = HoverEvent.hoverEvent(
          HoverEvent.Action.SHOW_TEXT, hoverMessageBuilder.getCurrentComponentBuilder().build());  // TODO validate

      plainTextBuilder.hoverEvent(hoverEvent);
      if (clickEvent != null) {
        plainTextBuilder.clickEvent(clickEvent);
      }

      List<Component> var18 = normalMessageBuilder.getCurrentComponentBuilder().children();  // TODO validate

      for (Component var14 : var18) {
        plainTextBuilder.append(var14);
      }

      // 终止当前 Hover 对后面的 TextPiece 的影响
      builderProvider.newComponentBuilder(FORMATTING_MERGES);
    }

    public void build(PlainTextBuilderContextProvider builderProvider) {
      Arrays.stream(this.normalMessage).forEach(piece -> piece.build(builderProvider));
    }

    public void build(HTMLTextBuilderContextProvider builderProvider) {
      HTMLTextBuilderContextProvider newBuilder = new HTMLTextBuilderContextProvider(builderProvider.settings());

      for (TextPiece hoverMessage : this.hoverMessage) {
        newBuilder.build(hoverMessage);
      }

      builderProvider.getCurrentElement().hover = newBuilder.getHTML().toString();
    }

    public ClickEvent.Action getAction() {
      return this.action;
    }

    public TextPiece[] getNormalMessage() {
      return this.normalMessage;
    }

    public TextPiece[] getHoverMessage() {
      return this.hoverMessage;
    }

    public TextPiece[] getClickAction() {
      return this.clickAction;
    }

    public int length() {
      return Arrays.stream(this.normalMessage).mapToInt(TextPiece::length).sum();
    }

    public int jsonLength() {
      return json_template + Arrays.stream(this.normalMessage).mapToInt(TextPiece::jsonLength).sum() + Arrays.stream(
          this.hoverMessage).mapToInt(TextPiece::jsonLength).sum() + Arrays.stream(this.clickAction).mapToInt(
          TextPiece::jsonLength).sum();
    }

    public String toString() {
      String var1 = "No Action";
      if (this.action != null) {
        var1 = this.action + " -> " + Arrays.toString(this.clickAction);
      }

      return "Hover{ length=" + this.length() + " | " + Arrays.toString(this.normalMessage) + ';' + Arrays.toString(
          this.hoverMessage) + ';' + var1 + " }";
    }

  }

  public static final class ColorAccessor extends Color {

    private final int range;  // TODO name?
    private final Variable variable;

    public ColorAccessor(int var1, Variable variable) {
      this.range = var1;
      this.variable = variable;
    }

    public List<TextPiece> getLastColors(TextContext var1) {
      ArrayList<TextPiece> var2 = new ArrayList<>(3);
      Object placeholder = this.variable.getVariable(var1);
      if (placeholder == null) {
        return Collections.singletonList(
            new Plain("{" + this.variable.value.asString(true) + " & " + this.range + '}'));
      } else {
        TextObject var4;
        if (placeholder instanceof TextObject) {
          var4 = (TextObject) placeholder;
        } else {
          if (!(placeholder instanceof PlaceholderTranslationContext)) {
            var2.add(new Plain("{" + this.variable.value.asString(
                true) + " & " + this.range + " (this special placeholder which is of type" + placeholder.getClass() + " -> " + placeholder + " is not supported for chat accessors}"));
            return var2;
          }

          var4 = this.variable.getCompiled((PlaceholderTranslationContext) placeholder);
        }

        return var4.findColorPieces(Math.abs(this.range), this.range < 0);
      }
    }

    public void build(ComplexTextBuilderContextProvider builderProvider) {

      for (TextPiece vapiece3 : this.getLastColors(builderProvider.settings())) {
        builderProvider.build(vapiece3);
      }
    }

    public void build(PlainTextBuilderContextProvider builderProvider) {

      for (TextPiece var3 : this.getLastColors(builderProvider.settings())) {
        builderProvider.build(var3);
      }
    }

    public void build(HTMLTextBuilderContextProvider builderProvider) {

      for (TextPiece var3 : this.getLastColors(builderProvider.settings())) {
        builderProvider.build(var3);
      }
    }

    public int jsonLength() {
      return 5;
    }

    public int length() {
      return 4;
    }

    public String toString() {
      return "ColorAccessor{ " + this.variable.toString() + " & " + this.range + " }";
    }

  }

  public static final class Variable extends TextPiece {

    private final Placeholder value;

    public Variable(Placeholder value) {
      this.value = value;
    }

    public void build(ComplexTextBuilderContextProvider builderProvider) {
      Object placeholder = this.getVariable(builderProvider.settings());
      if (placeholder == null) {  // 构建占位符失败
        builderProvider.newComponentBuilder(ALL_MERGES).content(this.value.asString(true));
      } else {
//                builderProvider.settings().usePrefix(false);
        if (placeholder instanceof TextObjectBuilder) {
          ((TextObjectBuilder) placeholder).build(builderProvider);
        } else if (!(placeholder instanceof PlaceholderTranslationContext)) {
          builderProvider.newComponentBuilder(ALL_MERGES).content(placeholder.toString());
        } else {
          this.getCompiled((PlaceholderTranslationContext) placeholder).build(builderProvider);
        }
      }
    }

    public Placeholder getPlaceholder() {
      return this.value;
    }

    public TextObject getCompiled(PlaceholderTranslationContext ptc) {
      Object var1 = ptc.getValue();
      TextCompilerSettings var3 = ptc.settings();
      return TextCompiler.compile(var1.toString(), var3);
    }

    public void build(PlainTextBuilderContextProvider builderProvider) {
      Object placehholder = this.getVariable(builderProvider.settings());
      if (placehholder == null) {
        builderProvider.getCurrentLine().append(this.value.asString(true));
      } else {
//                builderProvider.settings().usePrefix(false);
        if (placehholder instanceof TextObjectBuilder) {
          ((TextObjectBuilder) placehholder).build(builderProvider);
        } else if (!(placehholder instanceof PlaceholderTranslationContext)) {
          builderProvider.getCurrentLine().append(placehholder);
        } else {
          this.getCompiled((PlaceholderTranslationContext) placehholder).build(builderProvider);
        }
      }
    }

    public void build(HTMLTextBuilderContextProvider builderProvider) {
      Object placeholder = this.getVariable(builderProvider.settings());
      if ((placeholder) == null) {
        builderProvider.getCurrentElement().text = this.value.asString(true);
      } else {
//                builderProvider.settings().usePrefix(false);
        if (placeholder instanceof TextObjectBuilder) {
          ((TextObjectBuilder) placeholder).build(builderProvider);
        } else if (!(placeholder instanceof PlaceholderTranslationContext)) {
          builderProvider.getCurrentElement().text = placeholder.toString();
        } else {
          this.getCompiled((PlaceholderTranslationContext) placeholder).build(builderProvider);
        }
      }
    }

    public Object getVariable(PlaceholderContextBuilder context) {
      Object var2 = this.value.request(context);
      return PlaceholderTranslationContext.unwrapContextualPlaceholder(this.value.applyModifiers(var2), context);
    }

    public int length() {
      return this.value.asString(true).length();
    }

    public int jsonLength() {
      return 10;
    }

    public String toString() {
      return "Variable{ " + this.value + " }";
    }

  }

  public static final class NewLine extends TextPiece {

    public NewLine() {
    }

    public void build(ComplexTextBuilderContextProvider builderProvider) {
      TextComponent.Builder textBuilder = builderProvider.getCurrentComponentBuilder();
      textBuilder.content(textBuilder.content() + '\n');
    }

    public void build(PlainTextBuilderContextProvider builderProvider) {
      builderProvider.newLine();
    }

    public void build(HTMLTextBuilderContextProvider builderProvider) {
      builderProvider.newLine();
    }

    public int length() {
      return 1;
    }

    public int jsonLength() {
      return 0;
    }

    public String toString() {
      return "Newline";
    }

  }

  public static final class HexTextColor extends Color {

    private final java.awt.Color color;
    private TextFormatCodes approximateCode;
    private static final int c = 11 + 6;

    public java.awt.Color getColor() {
      return this.color;
    }

    public HexTextColor(TextColor color) {
      this(new java.awt.Color(color.value()));
    }

    public HexTextColor(java.awt.Color color) {
      this.color = color;
    }

    public void build(ComplexTextBuilderContextProvider builderProvider) {
      TextComponent.Builder newText = builderProvider.newComponentBuilder(EXCEPT_COLOR_MERGES);
      newText.color(TextColor.color(this.color.getRGB()));
    }

    public void build(PlainTextBuilderContextProvider builderProvider) {
      if (this.approximateCode == null) {
        TextFormatCodes namedFormat = BaseColorUtils.hexColorToStd(this.color);
        (new SimpleFormat(namedFormat)).build(builderProvider);
      } else {
        builderProvider.getCurrentLine().append(this.approximateCode);
      }
    }

    public void build(HTMLTextBuilderContextProvider builderProvider) {
      if (builderProvider.getCurrentElement().isEmpty()) {
        builderProvider.newElement(false);
      }

      builderProvider.getCurrentElement().color = "#" + BaseColorUtils.toHexString(this.color);
    }

    public TextFormatCodes getApproximateCode() {
      if (this.approximateCode == null) {
        this.approximateCode = BaseColorUtils.hexColorToStd(this.color);
      }
      return this.approximateCode;
    }

    public int length() {
      return 12;
    }

    public int jsonLength() {
      return c;
    }

    public String toString() {
      return "Hex{ " + BaseColorUtils.toHexString(this.color) + " }";
    }

  }

  /**
   * Such as &2, &l, §r, &a
   */
  public static final class SimpleFormat extends Color {

    private final TextFormatCodes code;
    private static final int b = 11;

    public SimpleFormat(TextFormatCodes code) {
      this.code = code;
    }

    public boolean equals(Object obj) {
      if (!(obj instanceof SimpleFormat)) {
        return false;
      } else {
        return this.code == ((SimpleFormat) obj).code;
      }
    }

    public void build(ComplexTextBuilderContextProvider builderProvider) {
      if (this.code.isColor()) {
        // 合并除颜色以外的其他样式
        TextComponent.Builder newBuilder = builderProvider.newComponentBuilder(EXCEPT_COLOR_MERGES);
        newBuilder.color((TextColor) this.code.value());
      }
      if (this.code.isReset()) {
        builderProvider.newComponentBuilder(NO_MERGES);
      }
      if (this.code.isDecoration()) {
        TextComponent.Builder newBuilder = builderProvider.newComponentBuilder(EXCEPT_DECORATIONS_MERGES);
        newBuilder.decoration((TextDecoration) this.code.value(), TextDecoration.State.TRUE);
      }
    }

    public void build(PlainTextBuilderContextProvider builderProvider) {
      builderProvider.getCurrentLine().append('§').append(this.code.code());
    }

    public void build(HTMLTextBuilderContextProvider builderProvider) {
      if (this.code.isColor()) {
        if (!builderProvider.getCurrentElement().isEmpty()) {
          builderProvider.newElement(false);
        }

        builderProvider.getCurrentElement().color = "#" + BaseColorUtils.toHexString(this.code.getRGBValue());
      } else if (this.code == TextFormatCodes.RESET) {
        builderProvider.newElement(false);
      } else {
        if (!builderProvider.getCurrentElement().isEmpty()) {
          builderProvider.newElement(true);
        }

        switch (this.code) {
          case BOLD:
            builderProvider.getCurrentElement().bold = true;
            return;
          case ITALIC:
            builderProvider.getCurrentElement().italic = true;
            return;
          case UNDERLINED:
            builderProvider.getCurrentElement().underlined = true;
            return;
          case STRIKETHROUGH:
            builderProvider.getCurrentElement().strikethrough = true;
            return;
          case OBFUSCATED:
            builderProvider.getCurrentElement().obfuscated = true;
            return;
          default:
            throw new AssertionError();
        }
      }
    }

    public TextFormatCodes getColor() {
      return this.code;
    }

    public int length() {
      return 2;
    }

    public int jsonLength() {
      return b + this.code.name().length();
    }

    public String toString() {
      return "SimpleColor{ " + this.code.name() + " }";
    }

  }

  public abstract static class Color extends TextPiece {

    protected Color() {
    }

  }

  /**
   * 普通消息片段.
   * <p>将会完全继承之前的消息片段的格式
   */
  public static final class Plain extends TextPiece {

    public static final Plain EMPTY = new Plain("");
    public static final int JSON_LEN = 9 + 3;

    private final String value;

    public Plain(String value) {
      this.value = value;
    }

    public String getMessage() {
      return this.value;
    }

    public void build(ComplexTextBuilderContextProvider builderProvider) {
      TextComponent.Builder text = builderProvider.getCurrentComponentBuilder();
      text.content(text.content() + this.value);
    }

    public void build(PlainTextBuilderContextProvider builderProvider) {
      builderProvider.getCurrentLine().append(this.value);
    }

    public void build(HTMLTextBuilderContextProvider builderProvider) {
      builderProvider.getCurrentElement().text = this.value;
    }

    public int length() {
      return this.value.length();
    }

    public int jsonLength() {
      return JSON_LEN + this.length();
    }

    public String toString() {
      return "Plain{ \"" + this.value + "\", length=" + this.length() + " }";
    }

  }

  public static final class Null extends TextPiece {

    public static final Null INSTANCE = new Null();

    public static @NotNull Null instance() {
      return INSTANCE;
    }

    public static TextPiece @NotNull [] arrayInstance() {
      return new TextPiece[]{INSTANCE};
    }

    private Null() {
    }

    public void build(ComplexTextBuilderContextProvider builderProvider) {
    }

    public void build(PlainTextBuilderContextProvider builderProvider) {
    }

    public void build(HTMLTextBuilderContextProvider builderProvider) {
    }

    public int length() {
      return 0;
    }

    public int jsonLength() {
      return 0;
    }

    public String toString() {
      return "NullPiece";
    }

  }

  public static final class Conditional extends TextPiece {

    private final List<Pair<ConditionCompiler.LogicalOperand, TextPiece[]>> branches;

    public Conditional(List<Pair<ConditionCompiler.LogicalOperand, TextPiece[]>> branches) {
      this.branches = branches;
    }

    public TextPiece[] getPiece(PlaceholderContextBuilder var1) {
      Iterator<Pair<ConditionCompiler.LogicalOperand, TextPiece[]>> var2 = this.branches.iterator();

      Pair<ConditionCompiler.LogicalOperand, TextPiece[]> var3 = var2.next();
      do {
        if (!var2.hasNext()) {
          return TextPiece.Null.arrayInstance();
        }
      } while (!ConditionProcessor.process(var3.getKey(), var1));

      return var3.getValue();
    }

    public void addFor(TextBuilderContextProvider builderProvider) {
      TextPiece[] pieces = this.getPiece(builderProvider.settings());
      if (pieces != null) {

        for (TextPiece piece : pieces) {
          builderProvider.build(piece);
        }
      }
    }

    public void build(ComplexTextBuilderContextProvider builderProvider) {
      this.addFor(builderProvider);
    }

    public void build(PlainTextBuilderContextProvider builderProvider) {
      this.addFor(builderProvider);
    }

    public void build(HTMLTextBuilderContextProvider builderProvider) {
      this.addFor(builderProvider);
    }

    private int a(Function<TextPiece, Integer> var1) {
      return this.branches.stream().map(Pair::getValue).map(
          (var1x) -> Arrays.stream(var1x).map(var1).reduce(Integer::sum).orElse(0)).max(Integer::compareTo).orElse(0);
    }

    public int length() {
      return this.a(TextPiece::length);
    }

    public int jsonLength() {
      return this.a(TextPiece::jsonLength);
    }

    public String toString() {
      return "Conditional{statements=" + this.branches + '}';
    }

  }

  // TODO
  public static final class Mathematics extends TextPiece {

    @Override
    public void build(ComplexTextBuilderContextProvider builderProvider) {

    }

    @Override
    public void build(PlainTextBuilderContextProvider builderProvider) {

    }

    @Override
    public void build(HTMLTextBuilderContextProvider builderProvider) {

    }

    @Override
    public int length() {
      return 0;
    }

    @Override
    public int jsonLength() {
      return 0;
    }

  }

  /**
   * 暂定 {font:}
   */
  public static class Font extends TextPiece {

    private final @NotNull
    @KeyPattern String

        fontKey;

    public Font(@NotNull @KeyPattern String fontKey) {
      Validate.Arg.notNull(fontKey, "fontKey");
      if (!Key.parseable(fontKey)) {
        throw new IllegalArgumentException("Invalid font key: " + fontKey);
      }
      this.fontKey = fontKey;
    }

    public @NotNull @KeyPattern String getFontKey() {
      return this.fontKey;
    }

    @Override
    public void build(ComplexTextBuilderContextProvider builderProvider) {
      builderProvider.getCurrentComponentBuilder().font(Key.key(this.fontKey));
    }

    @Override
    public void build(PlainTextBuilderContextProvider builderProvider) {
    }

    @Override
    public void build(HTMLTextBuilderContextProvider builderProvider) {
      // TODO
    }

    @Override
    public int length() {
      return "{font:".length() + this.fontKey.length() + "}".length();
    }

    @Override
    public int jsonLength() {
      return 0;  // TODO
    }

  }

}
