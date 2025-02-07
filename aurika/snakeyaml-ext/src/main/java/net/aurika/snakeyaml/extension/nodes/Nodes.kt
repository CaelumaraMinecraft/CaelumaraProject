package net.aurika.snakeyaml.extension.nodes

import org.snakeyaml.engine.v2.nodes.Node

const val PARSED_MARKER = "aurika_parsed"

fun Node.cacheConstructed(parsed: Any?) {
    this.setProperty(net.aurika.extension.nodes.PARSED_MARKER, parsed)
}

fun Node.getParsed(): Any? {
    return this.getProperty(net.aurika.extension.nodes.PARSED_MARKER)
}
