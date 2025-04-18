package net.aurika.auspice.managers.network.socket

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.aurika.auspice.utils.network.SocketJsonCommunicator
import net.aurika.common.ident.Ident
import net.aurika.common.ident.Identified
import net.aurika.common.ident.registry.AbstractIdentifiedRegistry
import java.util.function.Consumer
import java.util.logging.Logger

class SocketManager(val logger: Logger) : AbstractIdentifiedRegistry<SocketHandler>() {
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
    socket.log("Registered handler: " + value.ident())
  }

  val socket = object : SocketJsonCommunicator(4343, logger) {
    override fun onReceive(data: JsonElement) {
      require(data is JsonObject) { "Cannot deserialize non-object socket" }
      val nsElement = data["namespace"] ?: throw NullPointerException("Namespace element not available")
      val ident = Ident.ident(nsElement.getAsString())
      val handler = rawRegistry()[ident] ?: throw IllegalArgumentException("Unknown socket handler: $ident")
      val dataElement = data["data"] ?: throw NullPointerException("Missing data element for: $ident")
      val requestId: String? = data["id"]?.getAsString()
      if (handler.needsRequestId && requestId == null) throw NullPointerException("Missing request ID for: $ident")

      handler.onReceive(handler.SocketSession(this@SocketManager, dataElement, requestId))
    }
  }
}

abstract class SocketHandler(private val ns: Ident, val needsRequestId: Boolean = false) : Identified {
  abstract fun onReceive(session: SocketSession)
  override fun ident() = ns

  inner class SocketSession(val socket: SocketManager, val data: JsonElement, val requestId: String?) {
    fun reply(sendData: JsonElement) {
      socket.sendRequest { container ->
        if (needsRequestId) container.addProperty("id", requestId)
        container.addProperty("namespace", ns.asDataString())
        container.add("data", sendData)
      }
    }
  }
}