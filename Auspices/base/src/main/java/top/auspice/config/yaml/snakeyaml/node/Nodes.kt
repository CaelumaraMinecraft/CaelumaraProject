package top.auspice.config.yaml.snakeyaml.node

import org.snakeyaml.engine.v2.nodes.Node

private val parsedValues = HashMap<Node, Any>()

fun Node.cacheConstructed(parsed: Any) {
    parsedValues[this] = parsed
    this.setProperty("Auspice_Parsed", parsed)
}

fun Node?.getParsed(): Any? {
    return this?.getProperty("Auspice_Parsed")
    return parsedValues[this]
}
