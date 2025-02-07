package top.auspice.configs.texts.compiler.builders.context;

import top.auspice.configs.texts.compiler.pieces.TextPiece;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;

import java.util.ArrayList;
import java.util.List;

public class PlainTextBuilderContextProvider extends TextBuilderContextProvider {
    private final List<StringBuilder> lines = new ArrayList<>(5);
    private StringBuilder builder;
    private boolean merged = false;

    public PlainTextBuilderContextProvider(TextPlaceholderProvider textPlaceholderProvider) {
        super(textPlaceholderProvider);
        this.newLine();
    }

    public StringBuilder getCurrentLine() {
        this.checkMerged();
        return this.builder;
    }

    public void newLine() {
        this.checkMerged();
        this.builder = new StringBuilder();
        this.lines.add(this.builder);
    }

    private void checkMerged() {
        if (this.merged) {
            throw new IllegalStateException("This message was already merged and cannot be accessed anymore");
        }
    }

    public String merge() {
        this.checkMerged();
        this.merged = true;
        StringBuilder firstLine = this.lines.get(0);

        for (StringBuilder line : this.lines) {
            if (firstLine != line) {
                firstLine.append('\n').append(line);
            }
        }

        return firstLine.toString();
    }

    public List<StringBuilder> getLines() {
        this.checkMerged();
        return this.lines;
    }

    public void build(TextPiece piece) {
        this.checkMerged();
        piece.build(this);
    }
}
