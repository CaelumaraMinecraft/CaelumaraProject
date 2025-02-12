package net.aurika.util.snakeyaml.nodes

import org.snakeyaml.engine.v2.nodes.Node

const val PARSED_MARKER = "aurika_parsed"

fun Node.cacheConstructed(parsed: Any?) {
    this.setProperty(PARSED_MARKER, parsed)
}

fun Node.getParsed(): Any? {
    return this.getProperty(PARSED_MARKER)
}
