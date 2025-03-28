package net.aurika.common.snakeyaml.nodes

import org.snakeyaml.engine.v2.nodes.Node

const val PARSED_MARKER = "aurika_parsed"

/**
 * Caches constructed value to [this] node.
 */
fun Node.cacheConstructed(parsed: Any?) {
  this.setProperty(PARSED_MARKER, parsed)
}

/**
 * Gets parsed value from [this] node.
 */
fun Node.parsed(): Any? {
  return this.getProperty(PARSED_MARKER)
}
