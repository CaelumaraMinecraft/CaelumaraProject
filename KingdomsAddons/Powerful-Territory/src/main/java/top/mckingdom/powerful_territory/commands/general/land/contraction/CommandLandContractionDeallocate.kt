package top.mckingdom.powerful_territory.commands.general.land.contraction

import org.bukkit.entity.Player
import org.kingdoms.commands.*
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.player.KingdomPlayer
import org.kingdoms.locale.Language
import org.kingdoms.utils.PlayerUtils
import top.mckingdom.powerful_territory.constants.land_contractions.LandContraction
import top.mckingdom.powerful_territory.data.Contractions
import top.mckingdom.powerful_territory.data.getContractions
import top.mckingdom.powerful_territory.GroupExt
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryConfig
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryLang

class CommandLandContractionDeallocate(parent: KingdomsParentCommand): KingdomsCommand("deallocate", parent) {
    override fun execute(context: CommandContext): CommandResult {
        val sender = context.getMessageReceiver()
        if (context.assertPlayer()) {
            return CommandResult.FAILED
        }
        val land: Land? = Land.getLand((sender as Player).getLocation())
        if (land == null) {
            PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_FAILED_NOT_CLAIMED.sendError(sender)
            return CommandResult.FAILED
        }
        val kSender = KingdomPlayer.getKingdomPlayer(sender)
        val lang = kSender.getLanguage()

        val contraction: LandContraction?
        val receiver: KingdomPlayer?
        val allocator: KingdomPlayer?

        if (context.requireArgs(2)) {
            return CommandResult.FAILED
        } else {

            contraction = Contractions.getContraction(context.arg(0), lang)
            if (contraction == null) {
                PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_USAGE.sendMessage(sender)
                return CommandResult.FAILED
            }
            receiver = PlayerUtils.getOfflinePlayer(context.arg(1))?.let { KingdomPlayer.getKingdomPlayer(it) }
            if (receiver == null) {
                PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_USAGE.sendMessage(sender)
                return CommandResult.FAILED
            }
            if (context.assertArgs(3)) {

                allocator = PlayerUtils.getOfflinePlayer(context.arg(2))?.let { KingdomPlayer.getKingdomPlayer(it) }
                if (allocator == null) {
                    PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_USAGE.sendMessage(sender)
                    return CommandResult.FAILED
                }

                if (kSender.isAdmin()) {

                    land.getContractions()?.get(contraction)?.allocationReceivers?.get(receiver)?.deallocate(allocator)

                    PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_SUCCESS_SINGLE_ALLOCATOR.sendMessage(sender,
                        "contraction", contraction.getName(lang),
                        "receiver", receiver.getOfflinePlayer().getName() ?: "",
                        "location", "${land.location.world} ${land.location.x} ${land.location.z}",
                        "allocator", allocator.getOfflinePlayer().getName() ?: ""
                    )
                    return CommandResult.SUCCESS
                } else {

                    if (!land.isClaimed()) {
                        PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_FAILED_NOT_CLAIMED.sendError(sender)
                        return CommandResult.FAILED
                    } else {
                        if (land.getKingdom() != kSender.getKingdom()) {
                            PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_FAILED_OTHER_KINGDOM.sendError(
                                sender
                            )
                            return CommandResult.FAILED
                        } else {
                            if (
                                kSender.hasPermission(GroupExt.PERMISSION_MANAGE_LAND_CONTRACTIONS)
                                || (land.getClaimer() == kSender && PowerfulTerritoryConfig.LAND_CONTRACTION_CLAIMER_HAS_ALL_PERMISSIONS.getManager()
                                    .getBoolean())
                                || allocator == kSender
                            ) {
                                land.getContractions()?.get(contraction)?.deallocateAll(receiver)
                                PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_SUCCESS_ALL_ALLOCATOR.sendMessage(
                                    sender,
                                    "contraction", contraction.getName(lang),
                                    "receiver", receiver.getOfflinePlayer().getName() ?: "",
                                    "location", "${land.location.world} ${land.location.x} ${land.location.z}",
                                    "allocator", allocator.getOfflinePlayer().getName() ?: ""
                                )
                                return CommandResult.SUCCESS
                            } else {
                                return CommandResult.FAILED
                            }
                        }
                    }
                }

            }

            if (kSender.isAdmin()) {
                land.getContractions()?.get(contraction)?.deallocateAll(receiver)
                PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_SUCCESS_ALL_ALLOCATOR.sendMessage(sender,
                    "contraction", contraction.getName(lang),
                    "receiver", receiver.getOfflinePlayer().getName() ?: "",
                    "location", "${land.location.world} ${land.location.x} ${land.location.z}"
                )
                return CommandResult.SUCCESS

            }

            if (!land.isClaimed()) {
                PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_FAILED_NOT_CLAIMED.sendError(sender)
                return CommandResult.FAILED
            }
            if (land.getKingdom() != kSender.getKingdom()) {
                PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_FAILED_OTHER_KINGDOM.sendError(sender)
                return CommandResult.FAILED
            }

            if (
                kSender.hasPermission(GroupExt.PERMISSION_MANAGE_LAND_CONTRACTIONS)
                || (land.getClaimer() == kSender && PowerfulTerritoryConfig.LAND_CONTRACTION_CLAIMER_HAS_ALL_PERMISSIONS.getManager().getBoolean())
            ) {
                land.getContractions()?.get(contraction)?.deallocateAll(receiver)
                PowerfulTerritoryLang.COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_SUCCESS_ALL_ALLOCATOR.sendMessage(sender,
                    "contraction", contraction.getName(lang),
                    "receiver", receiver.getOfflinePlayer().getName() ?: "",
                    "location", "${land.location.world} ${land.location.x} ${land.location.z}"
                )
                return CommandResult.SUCCESS
            }


        }


        return CommandResult.FAILED

    }

    override fun tabComplete(context: CommandTabContext): List<String> {
        val sender = context.getMessageReceiver()
        if (sender !is Player) {
            return emptyList()
        }
        val land: Land = Land.getLand(sender.getLocation()) ?: return emptyList()
        val kSender = KingdomPlayer.getKingdomPlayer(sender)
        val lang = kSender.getLanguage()
        val out = ArrayList<String>()
        if (kSender.isAdmin()) {
            return allPermissionOut(context, land, out, lang)
        }
        if (!land.isClaimed() || land.getKingdom() != kSender.getKingdom()) {
            return emptyList()
        }
        if (
            kSender.hasPermission(GroupExt.PERMISSION_MANAGE_LAND_CONTRACTIONS)
            || (land.getClaimer() == kSender && PowerfulTerritoryConfig.LAND_CONTRACTION_CLAIMER_HAS_ALL_PERMISSIONS.getManager().getBoolean())
            ) {

            return allPermissionOut(context, land, out, lang)

        } else {

            if (context.isAtArg(0)) {
                land.getContractions()?.keys?.forEach {
                    out.add(it.getName(lang))
                }
            }
            if (context.isAtArg(1)) {
                val contraction: LandContraction = Contractions.getContraction(context.arg(0), lang) ?: return emptyList()
                land.getContractions()?.get(contraction)?.allocationReceivers?.forEach { entry ->
                    if (entry.value.hasAllocator(kSender)) {                //如果由kSender分配, 则增加接受者的名字
                        entry.key.getOfflinePlayer().getName()?.let { out.add(it) }
                    }
                }
            }
            return out


        }

    }

    private fun allPermissionOut(
        context: CommandTabContext,
        land: Land,
        out: ArrayList<String>,
        lang: Language
    ): List<String> {
        if (context.isAtArg(0)) {
            land.getContractions()?.keys?.forEach {
                out.add(it.getName(lang))
            }
        }
        val contraction: LandContraction = Contractions.getContraction(context.arg(0), lang) ?: return emptyList()
        if (context.isAtArg(1)) {
            land.getContractions()?.get(contraction)?.allocationReceivers?.keys?.forEach { kingdomPlayer ->
                kingdomPlayer.getOfflinePlayer().getName()?.let { out.add(it) }
            }
        }
        val receiver = PlayerUtils.getOfflinePlayer(context.arg(1))?.let { KingdomPlayer.getKingdomPlayer(it) }
            ?: return emptyList()
        if (context.isAtArg(2)) {
            return land.getContractions()?.get(contraction)?.allocationReceivers?.get(receiver)?.getAllocatorsName() ?: emptyList()
        }

        return out
    }

}