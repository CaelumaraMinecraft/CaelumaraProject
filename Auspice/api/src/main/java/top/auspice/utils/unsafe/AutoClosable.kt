@file:JvmName("AutoCloseableUtils")

package top.auspice.utils.unsafe

fun AutoCloseable?.closeFinally(cause: Throwable?) {
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
