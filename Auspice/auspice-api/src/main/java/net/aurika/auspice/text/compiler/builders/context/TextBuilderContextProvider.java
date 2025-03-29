package net.aurika.auspice.text.compiler.builders.context;

import net.aurika.auspice.text.compiler.pieces.TextPiece;
import net.aurika.auspice.text.context.TextContext;

public abstract class TextBuilderContextProvider {

  protected TextContext settings;

  protected TextBuilderContextProvider(TextContext settings) {
    this.settings = settings;
  }

  public void setSettings(TextContext settings) {
    this.settings = settings;
  }

  public TextContext settings() {
    return this.settings;
  }

  public abstract void build(TextPiece piece);

}
