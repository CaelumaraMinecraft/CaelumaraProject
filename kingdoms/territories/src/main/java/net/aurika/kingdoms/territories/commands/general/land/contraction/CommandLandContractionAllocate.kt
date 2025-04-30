package net.aurika.kingdoms.territories.commands.general.land.contraction

import net.aurika.kingdoms.territories.config.PowerfulTerritoryConfig
import net.aurika.kingdoms.territories.config.PowerfulTerritoryLang
import net.aurika.kingdoms.territories.constant.land.contraction.LandContraction
import net.aurika.kingdoms.territories.constant.land.contraction.std.StandardLandContraction
import net.aurika.kingdoms.territories.data.Contractions
import net.aurika.kingdoms.territories.data.Contractions.contractionsString
import net.aurika.kingdoms.territories.data.allocate
import net.aurika.kingdoms.territories.data.hasLandContraction
import net.aurika.kingdoms.territories.GroupExt
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.kingdoms.commands.*
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.player.KingdomPlayer
import org.kingdoms.locale.Language
import org.kingdoms.utils.PlayerUtils
import java.util.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toKotlinDuration

class CommandLandContractionAllocate(parent: KingdomsParentCommand) : KingdomsCommand("allocate", parent) {
  override fun execute(context: CommandContext): CommandResult {
    if (context.requireArgs(2)) {    //至少2参数
      return CommandResult.FAILED
    } else if (context.assertPlayer()) {
      return CommandResult.FAILED
    } else {
      val sender: CommandSender = context.getMessageReceiver()
      val kSender = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer())
      val lang = kSender.language
      val land = Land.getLand(context.senderAsPlayer().location)
      val contraction: LandContraction? = contractionsString.get(lang)?.get(context.arg(0))
      val receiver: OfflinePlayer? = PlayerUtils.getOfflinePlayer(context.arg(1))

      val duration: Duration = if (context.hasArgs(3)) {
        context.arg(2).toLongOrNull()?.toDuration(DurationUnit.SECONDS)
          ?: PowerfulTerritoryConfig.LAND_CONTRACTION_ALLOCATE_DEFAULT_DURATION.manager.time!!
            .toKotlinDuration()
      } else {
        PowerfulTerritoryConfig.LAND_CONTRACTION_ALLOCATE_DEFAULT_DURATION.manager.time!!
          .toKotlinDuration()
      }

      if (contraction == null || receiver == null) {                                                             //如果两个参数不正确
        PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_ALLOCATE_USAGE.sendMessage(sender)
        return CommandResult.FAILED
      } else {
        if (land == null) {                                                                 //如果区块没被占领
          PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_ALLOCATE_FAILED_NOT_CLAIMED.sendMessage(sender)
          return CommandResult.FAILED
        } else {
          if (kSender.isAdmin) {
            return a(receiver, land, contraction, duration, kSender, sender, lang)
          } else {
            if (!land.isClaimed) {
              PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_ALLOCATE_FAILED_NOT_CLAIMED.sendMessage(
                sender
              )
              return CommandResult.FAILED
            } else {
              if (land.kingdom != kSender.kingdom) {                                                         //如果是不同王国
                PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_ALLOCATE_FAILED_OTHER_KINGDOM.sendMessage(
                  sender
                )
                return CommandResult.FAILED
              } else {
                if (
                  kSender.hasPermission(GroupExt.PERMISSION_MANAGE_LAND_CONTRACTIONS)                                                                                                           //如果分配者有"管理所有土地分配"王国权限
                  || kSender.hasLandContraction(
                    land,
                    StandardLandContraction.MANAGE_CONTRACTIONS
                  )                                                                                       //如果分配者被分配到了"管理承包"承包项目
                  || (land.claimer == kSender && PowerfulTerritoryConfig.LAND_CONTRACTION_CLAIMER_HAS_ALL_PERMISSIONS.manager
                    .boolean)                                    //如果土地由此人占领
                ) {                                                                                                                                                                    //如果分配土地的人有任一权限, 即可进行分配
                  return a(receiver, land, contraction, duration, kSender, sender, lang)
                } else {
                  PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_ALLOCATE_FAILED_NO_PERMISSION.sendError(
                    sender
                  )
                  return CommandResult.FAILED
                }
              }
            }
          }
        }
      }
    }
  }

  private fun a(
    receiver: OfflinePlayer,
    land: Land,
    contraction: LandContraction,
    duration: Duration,
    kSender: KingdomPlayer,
    sender: CommandSender,
    lang: Language
  ): CommandResult {
    val kReceiver = KingdomPlayer.getKingdomPlayer(receiver)
    land.allocate(contraction, kReceiver, duration, kSender)
    PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_ALLOCATE_SUCCESS.sendMessage(
      sender,
      "contraction", contraction.getName(lang),
      "player", receiver.name ?: "",
      "duration", duration
    )
    return CommandResult.SUCCESS
  }

  override fun tabComplete(context: CommandTabContext): List<String> {
    val lang = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer()).language
    if (context.isAtArg(0)) {
      contractionsString.get(lang).also {
        if (it == null) {
          return emptyList()
        } else {
          return Contractions.getContractions(context.arg(0), lang)
        }
      }
    }
    if (context.isAtArg(1)) {
      val out = ArrayList<String>()
      val kingdom = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer()).kingdom
//            return kingdom.getMembers { kp ->
//                return@getMembers kp.getPlayer().getName()
//            }
      kingdom!!.getMembers().forEach { uuid: UUID ->
        KingdomPlayer.getKingdomPlayer(uuid).offlinePlayer.name?.let { out.add(it) }

      }
      return out
    }
    if (context.isAtArg(2)) {
      return listOf("[duration]")
    }
    return emptyList()
  }
}
