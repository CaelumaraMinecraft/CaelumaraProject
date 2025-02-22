package top.mckingdom.auspice.util;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.CommandContext;
import org.kingdoms.commands.CommandTabContext;
import org.kingdoms.commands.TabCompleteManager;
import org.kingdoms.constants.group.Group;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.Nation;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.locale.KingdomsLang;
import org.kingdoms.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandUtil {

    //   give #P DuoDuoJuZi 1
    //   give DuoDuoJuZi 1

    /**
     * 会返回所有未上线的玩家和王国!!
     */
    public static List<String> tabCompleteSelector(CommandTabContext context, boolean targetNation) {
        List<String> out = new ArrayList<>();
        String playerTag = KingdomsLang.COMMANDS_TAGS_IDENTIFIER_PLAYERS.parse(context.getMessageReceiver());
        String kingdomTag = KingdomsLang.COMMANDS_TAGS_IDENTIFIER_KINGDOMS.parse(context.getMessageReceiver());
        String nationTag = KingdomsLang.COMMANDS_TAGS_IDENTIFIER_NATIONS.parse(context.getMessageReceiver());
        if (context.isAtArg(0)) {
            if (targetNation) {
                return Arrays.asList(playerTag, kingdomTag, nationTag);
            } else {
                return Arrays.asList(playerTag, kingdomTag);
            }
        }

        String startsWith;                                //
        if (context.isAtArg(1)) {
            startsWith = context.arg(1);
            if (context.args[0].equals(playerTag)) {
                out.addAll(TabCompleteManager.getPlayers(startsWith));
            }
            if (context.args[0].equals(kingdomTag)) {
                out.addAll(TabCompleteManager.getKingdoms(startsWith));
            }
            if (context.args[0].equals(nationTag) && targetNation) {
                out.addAll(TabCompleteManager.getNations(startsWith));
            }
            return out;
        }

        return out;

    }


    /**
     *
     */

    public static @NotNull Operation<Group, String> selector(CommandContext context, boolean targetNation) {
        Operation<Group, String> out = new Operation<>();
        String playerTag = KingdomsLang.COMMANDS_TAGS_IDENTIFIER_PLAYERS.parse(context.getMessageReceiver());
        String kingdomTag = KingdomsLang.COMMANDS_TAGS_IDENTIFIER_KINGDOMS.parse(context.getMessageReceiver());
        String nationTag = KingdomsLang.COMMANDS_TAGS_IDENTIFIER_NATIONS.parse(context.getMessageReceiver());
        if (context.args.length != 3) {
            return out;     //不是 #P DuoDuoJuZi 2 这样两段长直接返回两个null
        }
        if (context.arg(0).equals(playerTag)) {
            OfflinePlayer offlinePlayer = PlayerUtils.getOfflinePlayer(context.arg(1));
            if (offlinePlayer == null) {
                return out;
            }
            out.setTarget(KingdomPlayer.getKingdomPlayer(offlinePlayer).getKingdom());
        }
        if (context.arg(0).equals(kingdomTag)) {
            out.setTarget(Kingdom.getKingdom(context.arg(1)));
        }
        if (context.arg(0).equals(nationTag) && targetNation) {
            out.setTarget(Nation.getNation(context.arg(1)));
        }
        out.setOperation(context.arg(2));
        return out;
    }




    //若操作的对象为null, 则操作也为null
    // null, null
    // not null, null
    // not null, not null

    public static class Operation<T, O> {
        private T target;
        private O operation;

        public Operation(T target, O operation) {
            this.target = target;
            if (this.target != null) {
                this.operation = operation;
            } else {
                this.operation = null;
            }

        }

        public Operation() {
            this.target = null;
            this.operation = null;         //我犯神经了 这两个其实默认值就是null
        }


        public T getTarget() {
            return target;
        }
        public void setTarget(T target) {
            this.target = target;
        }


        public O getOperation() {
            return operation;
        }
        public boolean setOperation(O operation) {
            if (this.target == null) {
                return false;
            } else {
                this.operation = operation;
                return true;
            }
        }


    }

}
