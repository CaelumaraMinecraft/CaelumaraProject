package top.mckingdom.auspice.commands.general.transfer_member

import org.bukkit.Bukkit
import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.CommandTabContext
import org.kingdoms.commands.KingdomsParentCommand
import org.kingdoms.constants.group.Kingdom
import org.kingdoms.constants.group.Nation
import org.kingdoms.constants.player.KingdomPlayer
import org.kingdoms.events.members.LeaveReason
import org.kingdoms.locale.KingdomsLang
import top.mckingdom.auspice.configs.AuspiceLang
import top.mckingdom.auspice.util.GroupExt
import java.util.*

class CommandTransferMember : KingdomsParentCommand("transferMember", true) {

    override fun execute(context: CommandContext): CommandResult {
        val args = context.getArgs()

        val senderKP = context.kingdomPlayer ?: return CommandResult.FAILED
        val senderKingdom = senderKP.kingdom ?: return CommandResult.FAILED
        val offlinePlayer = context.getOfflinePlayer(0)
        if (!senderKP.hasPermission(GroupExt.PERMISSION_TRANSFER_MEMBERS)) {
            GroupExt.PERMISSION_TRANSFER_MEMBERS.sendDeniedMessage(offlinePlayer.player!!)  // TODO
            return CommandResult.FAILED
        }
        val kPlayer = KingdomPlayer.getKingdomPlayer(offlinePlayer)
        val takerKingdom = context.getKingdom(1)
        if (!senderKingdom.hasAttribute(kPlayer.kingdom, GroupExt.DIRECTLY_TRANSFER_MEMBERS)) {
            context.getPlayer(0).sendMessage("转移成员请求功能未实现")
            //TODO 转移成员申请
        } else {
            directlyTransfer(kPlayer, senderKP, takerKingdom)
            return CommandResult.SUCCESS
        }
        return CommandResult.SUCCESS
    }

    override fun tabComplete(context: CommandTabContext): List<String> {
        val kingdomPlayer = context.kingdomPlayer ?: return emptyList();
        val kingdom = kingdomPlayer.kingdom ?: return emptyList()

        val players = ArrayList<String>()
        kingdom.getMembers().forEach { uuid: UUID ->
            val player = KingdomPlayer.getKingdomPlayer(uuid)
            if (player.rank.priority > kingdomPlayer.rank.priority) {
                players.add(player.getPlayer().getName())
            }
        }


        return players
    }

    init {
        CommandTransferMemberAccept(this)
    }

    companion object {
        @JvmStatic
        fun directlyTransfer(player: KingdomPlayer, sender: KingdomPlayer, taker: Kingdom): Boolean {
            if (sender.rank.priority >= player.rank.priority) {    //如果送人的玩家职级优先级比被送出去的玩家差
                AuspiceLang.COMMAND_TRANSFER_MEMBER_FAILED_RANK_PRIORITY.sendError(sender.player)
                return false
            } else {
                if (taker.maxMembers <= taker.members.size) {       //如果接收玩家的王国成员满了
                    KingdomsLang.COMMAND_ACCEPT_MAX_MEMBERS.sendError(sender.player)
                    return false
                } else {
                    Bukkit.getPluginManager().callEvent(player.leaveKingdom(LeaveReason.CUSTOM)) //让被送出去的玩家离开原先王国
                    player.joinKingdom(taker)
                    return true
                }
            }
        }

        @Deprecated("代码不稳定")
        @JvmStatic
        fun directlyTransfer(kingdom: Kingdom, sender: KingdomPlayer, taker: Nation): Boolean {
            val kp: Int = kingdom.king.nationRank.priority
            val sp: Int = sender.nationRank.priority

            if (sp >= kp) {
                AuspiceLang.COMMAND_TRANSFER_MEMBER_FAILED_RANK_PRIORITY.sendError(sender.player)
                return false;
            }

            if (taker.maxMembers <= taker.members.size) {
                KingdomsLang.COMMAND_ACCEPT_MAX_MEMBERS.sendError(sender.player)
                return false
            }
            Bukkit.getPluginManager().callEvent(kingdom.leaveNation(LeaveReason.CUSTOM)) //让被送出去的玩家离开原先王国
            kingdom.joinNation(taker, sender)
            return true
        }
    }
}
