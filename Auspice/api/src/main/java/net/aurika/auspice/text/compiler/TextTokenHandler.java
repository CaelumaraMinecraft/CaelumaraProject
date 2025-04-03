package net.aurika.auspice.text.compiler;

import net.aurika.auspice.text.compiler.pieces.TextPiece;

/**
 * {@linkplain TextCompiler} 的工作逻辑是: 遍历要编译的消息字符串中的每个字符 (用主指针 {@link TextCompiler#getIndex()} 和字符串数组 {@link TextCompiler#getChars()} 完成), 对于特殊字符 (如'&') 会去访问后面的字符, 验证是否为某种特定的内容 ({@linkplain TextPiece}), 若是, 则在 {@link TextCompiler#getPieces()} 里面增加对应的 {@linkplain TextPiece}, 并将主指针增加对应字符串的长度.
 */
public abstract class TextTokenHandler {

  public abstract TextTokenResult consumeUntil(TextCompiler textCompiler);

}