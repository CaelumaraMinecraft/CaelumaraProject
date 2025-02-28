package net.aurika.dyanasis;

import net.aurika.dyanasis.object.DyanasisObjectArray;
import net.aurika.dyanasis.object.DyanasisObjectMap;
import net.aurika.dyanasis.object.DyanasisObjectString;

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
 */
public final class Dyanasis {
    public static final String NAMESPACE = "dyanasis";
}
