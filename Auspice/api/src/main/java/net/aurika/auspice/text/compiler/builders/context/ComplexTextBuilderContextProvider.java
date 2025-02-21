package net.aurika.auspice.text.compiler.builders.context;

import net.aurika.auspice.text.compiler.pieces.TextPiece;
import net.aurika.auspice.text.context.TextContext;
import net.aurika.validate.Validate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style.Merge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.LinkedList;
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

    public ComplexTextBuilderContextProvider(TextContext settings) {
        this(null, settings);
    }

    public ComplexTextBuilderContextProvider(ComplexTextBuilderContextProvider parent, TextContext settings) {
        this(parent, Component.text(), settings);
    }

    public ComplexTextBuilderContextProvider(ComplexTextBuilderContextProvider parent, TextComponent.Builder currentBuilder, TextContext settings) {
        super(settings);
        this.parent = parent;
        this.currentBuilder = currentBuilder;
    }

    public TextComponent.Builder getCurrentComponentBuilder() {
        return this.currentBuilder;
    }

    public @NotNull TextComponent.Builder newComponentBuilder(@NotNull Merge @NotNull ... merges) {
        return this.newComponentBuilder(Set.of(merges));
    }

    public @NotNull TextComponent.Builder newComponentBuilder(@NotNull Set<Merge> merges) {
        Validate.Arg.notNull(merges, "merges");
        TextComponent oldText = this.currentBuilder.build();
        this.texts.add(oldText);
        TextComponent.Builder newTextBuilder = Component.text();
        newTextBuilder.mergeStyle(oldText, merges);
        return this.currentBuilder = newTextBuilder;
    }

    public void appendRemaining() {
        // TODO
    }

    public @NotNull TextComponent merge() {
        int length = this.texts.size() + 1;
        Component[] components = this.texts.toArray(new TextComponent[length]);
        components[length - 1] = this.currentBuilder.build();
        return Component.textOfChildren(components);
    }

    public void build(TextPiece piece) {
        piece.build(this);
    }
}

