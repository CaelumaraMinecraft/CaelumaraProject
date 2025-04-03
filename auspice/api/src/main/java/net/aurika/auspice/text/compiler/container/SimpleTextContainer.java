package net.aurika.auspice.text.compiler.container;

import net.aurika.auspice.text.TextObject;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;

public class SimpleTextContainer implements TextContainer {

  private final TextObject text;

  public SimpleTextContainer(TextObject text) {
    this.text = text;
  }

  public TextObject get(MessagePlaceholderProvider messageContext) {
    return this.text;
  }

}
