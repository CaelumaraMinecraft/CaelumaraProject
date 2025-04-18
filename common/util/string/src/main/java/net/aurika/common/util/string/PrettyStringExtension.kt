package net.aurika.common.util.string

fun Any?.toPrettyString(): String {
  val context = PrettyStringContext(StringBuilder(), 0)
  context.delegate(this)
  return context.getString().toString()
}
