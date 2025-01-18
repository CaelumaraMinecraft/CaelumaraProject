package top.auspice.configs.texts.compiler.builders.context;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style.Merge;
import org.jetbrains.annotations.Unmodifiable;
import top.auspice.configs.texts.compiler.pieces.TextPiece;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.utils.Checker;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ComplexTextBuilderContextProvider extends TextBuilderContextProvider {
    public static final @Unmodifiable Set<Merge> NO_MERGES = Set.of();
    public static final @Unmodifiable Set<Merge> ALL_MERGES = Set.of(Merge.COLOR, Merge.SHADOW_COLOR, Merge.DECORATIONS, Merge.EVENTS, Merge.INSERTION, Merge.FONT);
    public static final @Unmodifiable Set<Merge> EXCEPT_COLOR_MERGES = Set.of(Merge.DECORATIONS, Merge.EVENTS, Merge.INSERTION, Merge.FONT);
    public static final @Unmodifiable Set<Merge> EXCEPT_DECORATIONS_MERGES = Set.of(Merge.COLOR, Merge.SHADOW_COLOR, Merge.EVENTS, Merge.INSERTION, Merge.FONT);
    public static final @Unmodifiable Set<Merge> FORMATTING_MERGES = Set.of(Merge.COLOR, Merge.SHADOW_COLOR, Merge.DECORATIONS, Merge.INSERTION, Merge.FONT);
    public static final @Unmodifiable Set<Merge> EVENT_MERGES = Set.of(Merge.EVENTS);

    public static final int MAXIMUM_JSON_CHAT_PACKET_SIZE = 262144;
    public static final int SUGGESTED_JSON_CHAT_PACKET_SIZE_RENEWAL = 200000;

    protected LinkedList<TextComponent> texts = new LinkedList<>();
    private TextComponent.Builder currentBuilder;
    private boolean merged = false;
    private int packetSize;
    private final ComplexTextBuilderContextProvider parent;

    public ComplexTextBuilderContextProvider(TextPlaceholderProvider settings) {
        this(null, settings);
    }

    public ComplexTextBuilderContextProvider(ComplexTextBuilderContextProvider parent, TextPlaceholderProvider settings) {
        this(parent, Component.text(), settings);
    }

    public ComplexTextBuilderContextProvider(ComplexTextBuilderContextProvider parent, TextComponent.Builder currentBuilder, TextPlaceholderProvider settings) {
        super(settings);
        this.parent = parent;
        this.currentBuilder = currentBuilder;
    }

    public TextComponent.Builder getCurrentComponentBuilder() {
        return this.currentBuilder;
    }


//    public void newComponent(Component[] var1) {
//        Component lastComponent = var1[var1.length - 1];
//        TextComponent var3 = Component.textOfChildren(lastComponent);
//        var3.content("");
//        AComponentBuilder.copyFormatting(var3, lastComponent, ComponentBuilder.FormatRetention.FORMATTING);
//        this.a.append(var3, ComponentBuilder.FormatRetention.NONE);
//
//        for (Component var5 : var1) {
//            this.a.append(var5, ComponentBuilder.FormatRetention.NONE);
//            this.addPacketSize(var5);
//            this.checkPacketSize();
//        }
//
//        this.currentTextBuilder = var3;
//    }

    public TextComponent.Builder newComponentBuilder(Merge... merges) {
        return this.newComponentBuilder(Set.of(merges));
    }

    public TextComponent.Builder newComponentBuilder(Set<Merge> merges) {
        Checker.Argument.checkNotNull(merges, "merges");
        //
        //
        TextComponent oldText = this.currentBuilder.build();
        this.texts.add(oldText);
        TextComponent.Builder newTextBuilder = Component.text();
        newTextBuilder.mergeStyle(oldText, merges);
        return this.currentBuilder = newTextBuilder;
    }

    public void appendRemaining() {
    }

    public TextComponent merge() {
        int length = this.texts.size() + 1;
        TextComponent[] components = this.texts.toArray(new TextComponent[length]);
        components[length - 1] = this.currentBuilder.build();
        return Component.textOfChildren(components);
    }

//    public void checkMerged() {
//        if (this.merged) {
//            throw new IllegalStateException("This message was already merged and cannot be accessed anymore");
//        }
//    }

    private void addParentPacketSize(int size) {  // TODO rename method
        if (this.parent != null) {
            this.parent.packetSize += size;
        } else {
            this.packetSize += size;
        }
    }

    public void checkPacketSize() {  // TODO json package size
        if (this.packetSize >= SUGGESTED_JSON_CHAT_PACKET_SIZE_RENEWAL) {
            if (this.parent != null) {
                throw new IllegalStateException("Message with parent packet size exceeded");
            }

            this.a.newPacket();
            this.packetSize = 0;
        }
    }

    public void addPacketSize(Component component) {
        this.addParentPacketSize(TextPiece.Plain.JSON_LEN);
        if (component instanceof TextComponent text) {
            this.addParentPacketSize(text.content() == null ? 0 : text.content().length());
            HoverEvent<?> hoverEvent = text.hoverEvent();
            if (hoverEvent != null) {
                this.addParentPacketSize(hoverEvent.value().length());
            }

            if (text.clickEvent() != null) {
                this.addParentPacketSize(text.clickEvent().value().length());
            }

            if (text.font() != null) {
                this.addParentPacketSize(text.font().namespace().length());
            }

            if (text.insertion() != null) {
                this.addParentPacketSize(text.insertion().length());
            }
        }

        List<Component> var3 = component.children();
        if (var3 != null) {
            var3.forEach(this::addPacketSize);
        }
    }

    public void build(TextPiece piece) {
        piece.build(this);
        this.addParentPacketSize(piece.jsonLength());
        this.checkPacketSize();
    }
}

