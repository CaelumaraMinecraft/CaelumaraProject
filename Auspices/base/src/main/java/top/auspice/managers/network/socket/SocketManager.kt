package top.auspice.managers.network.socket

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.aurika.namespace.NSedKey
import net.aurika.namespace.NSKeyed
import net.aurika.namespace.NSKedRegistry
import top.auspice.main.Auspice
import top.auspice.utils.network.SocketJsonCommunicator
import java.util.function.Consumer
import java.util.logging.Logger

class SocketManager(val logger: Logger) : NSKedRegistry<SocketHandler>(Auspice.get(), "NETWORK_SOCKET_MANAGER") {
    companion object {
        @JvmStatic
        private var INSTANCE: SocketManager? = null

        @JvmStatic
        fun initMainManager(logger: Logger) {
            INSTANCE = SocketManager(logger)
        }

        @JvmStatic
        fun getMain(): SocketManager = INSTANCE!!
    }

    fun sendRequest(request: Consumer<JsonObject>) {
        val jsonObject = JsonObject()
        request.accept(jsonObject)
        socket.send(jsonObject)
    }

    override fun register(value: SocketHandler) {
        super.register(value)
        socket.log("Registered handler: " + value.getNamespacedKey())
    }

    val socket = object : SocketJsonCommunicator(4343, logger) {
        override fun onReceive(data: JsonElement) {
            require(data is JsonObject) { "Cannot deserialize non-object socket" }
            val nsElement = data["namespace"] ?: throw NullPointerException("Namespace element not available")
            val ns = NSedKey.fromString(nsElement.getAsString())
            val handler = registered[ns] ?: throw IllegalArgumentException("Unknown socket handler: $ns")
            val dataElement = data["data"] ?: throw NullPointerException("Missing data element for: $ns")
            val requestId: String? = data["id"]?.getAsString()
            if (handler.needsRequestId && requestId == null) throw NullPointerException("Missing request ID for: $ns")

            handler.onReceive(handler.SocketSession(this@SocketManager, dataElement, requestId))
        }
    }
}

abstract class SocketHandler(private val ns: NSedKey, val needsRequestId: Boolean = false) :
    NSKeyed {
    abstract fun onReceive(session: SocketSession)
    override fun getNamespacedKey() = ns

    inner class SocketSession(val socket: SocketManager, val data: JsonElement, val requestId: String?) {
        fun reply(sendData: JsonElement) {
            socket.sendRequest { container ->
                if (needsRequestId) container.addProperty("id", requestId)
                container.addProperty("namespace", ns.asString())
                container.add("data", sendData)
            }
        }
    }
}