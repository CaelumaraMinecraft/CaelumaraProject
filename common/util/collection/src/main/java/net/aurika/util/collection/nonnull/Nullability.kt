@file:JvmName("Nullability")

package net.aurika.util.collection.nonnull

fun <T> Collection<T?>?.assertNonNullElements(): Collection<T> {
    if (this == null) {
        throw IllegalArgumentException()
    }

    for (t in this) {
        if (t === null) {
            throw IllegalArgumentException(this::class.java.getSimpleName() + " contains null")
        }
    }

    return this as Collection<T>
}

fun <T> Collection<T>.assertNonNull(obj: T?): T {
    if (obj === null) {
        throw IllegalArgumentException(this::class.java.getSimpleName() + " cannot contain null values")
    }
    return obj
}
