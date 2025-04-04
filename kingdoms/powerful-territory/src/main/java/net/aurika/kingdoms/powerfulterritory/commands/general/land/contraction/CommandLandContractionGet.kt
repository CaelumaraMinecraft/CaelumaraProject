package net.aurika.kingdoms.powerfulterritory.commands.general.land.contraction

import net.aurika.kingdoms.powerfulterritory.configs.PowerfulTerritoryLang
import net.aurika.kingdoms.powerfulterritory.data.getContractions
import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.KingdomsCommand
import org.kingdoms.commands.KingdomsParentCommand
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.land.location.SimpleChunkLocation
import org.kingdoms.constants.player.KingdomPlayer
import org.kingdoms.locale.Language
import org.kingdoms.locale.compiler.builders.MessageObjectLinker

class CommandLandContractionGet(parent: KingdomsParentCommand) : KingdomsCommand("get", parent) {

  override fun execute(context: CommandContext): CommandResult {
    val location = SimpleChunkLocation.of(context.senderAsPlayer().location)
    if (show(context, location)) {
      return CommandResult.SUCCESS
    } else {
      return CommandResult.FAILED
    }
  }

  companion object {
    @JvmStatic
    fun show(context: CommandContext, location: SimpleChunkLocation): Boolean {
      val sender = context.getMessageReceiver()
      val lang: Language = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer()).language

      val land = Land.getLand(location)
      val x = location.x
      val z = location.z
      if (land == null || !land.isClaimed) {
        PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_GET_FAILED_NOT_CLAIMED.sendMessage(
          sender,
          "location",
          "$x $z"
        )
        return false
      }
      PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_GET_SUCCESS_HEAD.sendMessage(sender, "location", "$x $z")
      land.getContractions()?.forEach { entry ->
        if (entry.value.allocationReceivers.size > 0) {
          val linker = MessageObjectLinker()
          val name = entry.key.getName(lang)
          entry.value.allocationReceivers.forEach { allocationReceiver ->
            allocationReceiver.key.offlinePlayer.name?.let { linker.add(it) }
          }
          PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_GET_SUCCESS_BODY.sendMessage(
            sender,
            "contraction",
            name,
            "players",
            linker.buildPlain(context.messageContext)
          )
        }
      }
      PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_GET_SUCCESS_END.sendMessage(sender, "location", "$x $z")
      return true
    }
  }
}