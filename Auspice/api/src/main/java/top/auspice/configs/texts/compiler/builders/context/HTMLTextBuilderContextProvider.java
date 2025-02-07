package top.auspice.configs.texts.compiler.builders.context;

import top.auspice.configs.texts.compiler.TextCompiler;
import top.auspice.configs.texts.compiler.TextCompilerSettings;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.compiler.PlaceholderTranslationContext;
import top.auspice.configs.texts.compiler.builders.MessageObjectBuilder;
import top.auspice.configs.texts.compiler.pieces.TextPiece;
import top.auspice.configs.texts.compiler.placeholders.Placeholder;
import top.auspice.configs.texts.compiler.placeholders.PlaceholderParser;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.texts.placeholders.context.PlaceholderProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class HTMLTextBuilderContextProvider extends TextBuilderContextProvider {
    public final List<HTMLElement> elements = new ArrayList<>();
    private HTMLElement htmlElement = new HTMLElement();

    public static void addHandler(TextPlaceholderProvider var0) {
        var0.addChild("html", new StyleHandler(var0));
        var0.addChild("htmlStyle", new PlaceholderHandler(var0));
    }

    public HTMLTextBuilderContextProvider(TextPlaceholderProvider var1) {
        super(var1);
    }

    public HTMLElement getCurrentElement() {
        return this.htmlElement;
    }

    public void newElement(boolean inheritOldElement) {
        this.elements.add(this.htmlElement);
        HTMLElement oldElement = this.htmlElement;
        this.htmlElement = new HTMLElement();
        if (inheritOldElement) {
            this.htmlElement.color = oldElement.color;
            this.htmlElement.italic = oldElement.italic;
            this.htmlElement.bold = oldElement.bold;
            this.htmlElement.strikethrough = oldElement.strikethrough;
            this.htmlElement.underlined = oldElement.underlined;
            this.htmlElement.obfuscated = oldElement.obfuscated;
        }
    }

    public StringBuilder getHTML() {
        this.newElement(false);
        StringBuilder var1 = new StringBuilder(this.elements.size() * 30);

        for (HTMLElement element : this.elements) {
            HTMLElement var3;
            if ((var3 = element) == null) {
                var1.append("<br>");
            } else {
                var1.append(var3);
            }
        }

        return var1;
    }

    public void newLine() {
        if (!this.htmlElement.isEmpty()) {
            this.newElement(true);
        }

        this.elements.add(null);
    }

    public void build(TextPiece piece) {
        piece.build(this);
    }

    private static final class StyleHandler implements PlaceholderProvider {
        private final TextPlaceholderProvider a;

        private StyleHandler(TextPlaceholderProvider var1) {
            this.a = var1;
        }

        public Object providePlaceholder(String name) {
            Placeholder var3 = PlaceholderParser.parse(name);
            TextPiece.Variable var4 = new TextPiece.Variable(var3);
            HTMLTextBuilderContextProvider var2 = new HTMLTextBuilderContextProvider(this.a);
            var2.build(var4);
            return var2.getHTML().toString();
        }
    }

    private static final class PlaceholderHandler implements PlaceholderProvider {
        private final TextPlaceholderProvider placeholderProvider;

        private PlaceholderHandler(TextPlaceholderProvider placeholderProvider) {
            this.placeholderProvider = placeholderProvider;
        }

        public Object providePlaceholder(String name) {
            Placeholder var3 = PlaceholderParser.parse(name);
            TextPiece.Variable variablePiece = new TextPiece.Variable(var3);
            Object var2 = variablePiece.getPlaceholder(this.placeholderProvider);
            if (var2 == null) {
                return null;
            } else {
                TextObject var5;
                if (var2 instanceof MessageObjectBuilder) {
                    var5 = ((MessageObjectBuilder) var2).evaluateDynamicPieces(this.placeholderProvider);
                } else if (!(var2 instanceof PlaceholderTranslationContext ptc)) {
                    var5 = TextCompiler.compile(var2.toString(), TextCompilerSettings.none().colorize().translatePlaceholders());
                } else {
                    var5 = variablePiece.getCompiled(ptc);
                }

                HTMLTextBuilderContextProvider var7 = new HTMLTextBuilderContextProvider(this.placeholderProvider);
                var5.build(var7);
                return var7.getCurrentElement().a();
            }
        }
    }

    public static final class HTMLElement {
        public String text;
        public String color;
        public boolean italic;
        public boolean bold;
        public boolean underlined;
        public boolean strikethrough;
        public boolean obfuscated;
        public String hover;

        public HTMLElement() {
        }

        public boolean isEmpty() {
            return this.text == null || this.text.isEmpty();
        }

        String a() {
            StringJoiner var1 = new StringJoiner(";");
            if (this.color != null) {
                var1.add("chat: " + this.color);
            }

            if (this.italic) {
                var1.add("font-format: italic");
            }

            if (this.bold) {
                var1.add("font-weight: bold");
            }

            if (this.underlined) {
                var1.add("text-decoration: underline");
            }

            if (this.strikethrough) {
                var1.add("text-decoration-line: line-through");
            }

            return var1.toString();
        }

        public String toString() {
            if (this.text != null && !this.text.isEmpty()) {
                return this.hover == null
                        ?
                        "<span class=\"auspice-element\" format=\"" + this.a() + "\">" + this.text + "</span>"
                        :
                        "<div class=\"auspice-element auspice-tooltip\" format=\"" + this.a() + "\">" + this.text + "  <span class=\"auspice-element auspice-tooltip-text\">Tooltip text</span></div>";
            } else {
                return "";
            }
        }
    }
}
