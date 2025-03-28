package net.aurika.logging;

public interface Indent {

  /**
   * 普通缩进
   */
  static Indent defaultIndent() {
    return StandardIndents.DEFAULT_INDENT;
  }

  /**
   * 连续缩进
   */
  static Indent contunuousIndent() {
    return StandardIndents.CONTINUOUS_INDENT;
  }

  int getSpaces();

}

enum StandardIndents implements Indent {
  DEFAULT_INDENT(4),
  CONTINUOUS_INDENT(8),
  ;

  private final int spaces;

  StandardIndents(int spaces) {
    this.spaces = spaces;
  }

  @Override
  public int getSpaces() {
    return spaces;
  }
}
