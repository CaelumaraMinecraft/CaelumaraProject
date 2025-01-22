@file:JvmName("CloseableUtils")

package top.auspice.utils.internal

import java.io.Closeable

/**
 * Closes this [Closeable], suppressing possible exception or error thrown by [Closeable.close] function when
 * it's being closed due to some other [cause] exception occurred.
 *
 * The suppressed exception is added to the list of suppressed exceptions of [cause] exception, when it's supported.
 */
fun Closeable?.closeFinally(cause: Throwable?) {
    if (this == null) return
    if (cause == null) {
        this.close()
    }
    try {
        this.close()
    } catch (closeException: Throwable) {
        cause!!.addSuppressed(closeException)
    }
}
