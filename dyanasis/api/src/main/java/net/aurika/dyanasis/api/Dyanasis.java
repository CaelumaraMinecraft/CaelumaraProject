package net.aurika.dyanasis.api;

import net.aurika.common.ident.Group;
import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.compiler.context.evaluating.DyanasisLexerVariableProvider;
import net.aurika.dyanasis.api.compiler.expression.Expression;
import net.aurika.dyanasis.api.compiler.setting.DyanasisCompilerSetting;
import net.aurika.dyanasis.api.declaration.file.DyanasisFile;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaced;
import net.aurika.dyanasis.api.object.DyanasisObjectArray;
import net.aurika.dyanasis.api.object.DyanasisObjectMap;
import net.aurika.dyanasis.api.object.DyanasisObjectString;

/**
 * A powerful dynamic string based configuration language.
 * <p></p>
 * <b>Standard syntaxes:</b>
 * <blockquote><pre>
 * // string declaration {@link DyanasisObjectString}
 * "str"
 *
 * // map declaration {@link DyanasisObjectMap}
 * { key1: value1, key2: value2 }
 *
 * // array declaration {@link DyanasisObjectArray}
 * [ e1, e2, e3 ]
 *
 * // others object declaration
 * 'Dyanasis:LOOKUP'
 *
 * // namespace visiting
 * ::NS1
 * ::NS2::subNS3
 *
 * var var1 = "123456"                  // variable "var1"
 * var1.function1("at", 8, "at")
 *
 * ::ns1::subNs2.extFunction("123456", 'Aurika:LOGGER', 0)
 * =>  variable@::ns1::subNs2.extFunction('Aurika:LOGGER', 0)
 * </pre></blockquote>
 * <p><strong>Implementation requirements:</strong></p>
 *
 * <b>拓展成员:</b>
 * <p>若在类型上拓展, 则该拓展成员对所有这个类型创建的对象有效 (包括之前创建的), 若在对象上拓展, 则仅对该对象有效.</p>
 *
 * <b>File:</b>
 * <p>一个 {@linkplain DyanasisFile}, 继承了 {@linkplain DyanasisNamespaced}, 因此可以定义它的命名空间.
 * {@linkplain DyanasisFile} 可能代表了一个配置文件, 能够在其中定义一个或多个 {@linkplain DyanasisNamespace},
 * 为这些被定义的命名空间定义函数, 属性等, 也可以在其中定义文件级的属性, 方法等.
 * <strong>一个文件中的各个声明应该能够互相访问.</strong></p>
 *
 * <b>Lex:</b>
 * <p><b>Dyanasis 对一个配置文件进行分析的过程:</b></p>
 * <pre>
 *    ╭──────────────────╮
 *    │  Lexer Settings  │ ═╗
 *    ╰──────────────────╯  ║   ╭─────────╮       ╭──────────────╮
 *                          ╠═> │  Lexer  │ ════> │  Expression  │ ═╗
 *      ╭───────────────╮   ║   ╰─────────╯       ╰──────────────╯  ║   ╭───────────────────╮
 *      │  Config File  │ ══╝                                       ╠═> │  Dyanasis Object  │
 *      ╰───────────────╯                  ╭─────────────────────╮  ║   ╰───────────────────╯
 *                                     ╔═> │  Variable Provider  │ ═╝             ║
 *                                     ║   ╰─────────────────────╯                ║
 *                                     ╚══════════════════════════════════════════╝  Next Variable...
 *
 * see {@linkplain DyanasisCompiler Lexer}
 * see {@linkplain DyanasisCompilerSetting Compiler Settings}
 * see {@linkplain Expression Expression}
 * see {@linkplain DyanasisLexerVariableProvider Variable Provider}
 * </pre>
 */
public final class Dyanasis {

  public static final String NAMESPACE = "dyanasis";
  public static final String GROUP_STRING = "net.aurika." + NAMESPACE;
  public static final Group GROUP = Group.group(GROUP_STRING);

  private Dyanasis() { }

}
