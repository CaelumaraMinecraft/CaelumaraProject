package net.aurika.auspice.text.compiler.utils;

import net.aurika.auspice.text.compiler.TextCompiler;

/**
 * To a string that can be compiled to a hover message by {@linkplain TextCompiler}
 */
public final class HoverMessage {
    private final String normalMessage;
    private final String hoverMessage;
    private final String clickAction;

    public HoverMessage(String normalMessage, String hoverMessage, String clickAction) {
        this.normalMessage = normalMessage;
        this.hoverMessage = hoverMessage;
        this.clickAction = clickAction;
    }

    public String toString() {
        return "hover:{" + this.normalMessage + ';' + this.hoverMessage + (this.clickAction.isEmpty() ? "" : ";" + this.clickAction) + '}';
    }
}