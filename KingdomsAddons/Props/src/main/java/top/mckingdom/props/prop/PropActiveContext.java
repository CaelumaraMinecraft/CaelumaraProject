package top.mckingdom.props.prop;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.config.accessor.KeyedConfigAccessor;
import org.kingdoms.constants.player.KingdomPlayer;
import top.mckingdom.props.events.KingdomPropActiveEvent;

import java.util.function.Consumer;

public final class PropActiveContext {

    private Event event;
    private KingdomPlayer player;
    private ItemStack item;
    private ActiveType activeType;

    private Block blockClicked;
    private BlockFace blockFace;
    private KeyedConfigAccessor option;

    private LivingEntity interactedEntity;
    private Consumer<KingdomPropActiveEvent> modifier;

    public PropActiveContext() {

    }

    private PropActiveContext(Event cause) {
        this.event = cause;
    }

    public PropActiveContext(PlayerInteractEntityEvent cause) {
        this((Event) cause);
        player = KingdomPlayer.getKingdomPlayer(cause.getPlayer());
        item = cause.getPlayer().getItemInUse();
        activeType = ActiveType.RIGHT_CLICK_ENTITY;
    }

    public PropActiveContext(PlayerInteractEvent cause) {
        this((Event) cause);
        player = KingdomPlayer.getKingdomPlayer(cause.getPlayer());
        item = cause.getItem();
        blockClicked = cause.getClickedBlock();
        blockFace = cause.getBlockFace();
        if (cause.getAction() == Action.RIGHT_CLICK_AIR) {
            activeType = ActiveType.RIGHT_CLICK_AIR;
        } else if (cause.getAction() == Action.LEFT_CLICK_AIR) {
            activeType = ActiveType.LEFT_CLICK_AIR;
        } else if (cause.getAction() == Action.RIGHT_CLICK_BLOCK) {
            activeType = ActiveType.RIGHT_CLICK_BLOCK;
        } else if (cause.getAction() == Action.LEFT_CLICK_BLOCK) {
            activeType = ActiveType.LEFT_CLICK_BLOCK;
        } else {
            activeType = null;
        }
    }

    public PropActiveContext(BlockBreakEvent cause) {
        this((Event) cause);
        player = KingdomPlayer.getKingdomPlayer(cause.getPlayer());
        item = cause.getPlayer().getItemInUse();
        blockClicked = cause.getBlock();
        blockFace = cause.getPlayer().getFacing().getOppositeFace();   //TODO: 不确定
    }

    public PropActiveContext(Event cause, KingdomPlayer player, ItemStack item, ActiveType activeType) {
        this((Event) cause);
        this.player = player;
        this.item = item;
        this.activeType = activeType;
    }

    public @Nullable Event getCause() {
        return this.event;
    }

    public void setCause(@Nullable Event cause) {
        this.event = cause;
    }

    public @Nullable KingdomPlayer getPlayer() {
        return player;
    }

    public void setPlayer(@Nullable KingdomPlayer player) {
        this.player = player;
    }

    public @Nullable ItemStack getItem() {
        return item;
    }

    public void setItem(@Nullable ItemStack item) {
        this.item = item;
    }

    public @Nullable ActiveType getActiveType() {
        return activeType;
    }

    public void setActiveType(@Nullable ActiveType activeType) {
        this.activeType = activeType;
    }

    public LivingEntity getInteractedEntity() {
        return interactedEntity;
    }

    public void setInteractedEntity(LivingEntity interactedEntity) {
        this.interactedEntity = interactedEntity;
    }

    public Consumer<KingdomPropActiveEvent> getModifier() {
        return modifier;
    }

    public void setModifier(Consumer<KingdomPropActiveEvent> modifier) {
        this.modifier = modifier;
    }

    public KeyedConfigAccessor getOption() {
        return option;
    }

    public void setOption(KeyedConfigAccessor option) {
        this.option = option;
    }

    public PropActiveContext dontCallEvent() {
        this.modifier = PropActiveContext::a;
        return this;
    }

    private static void a(KingdomPropActiveEvent event) {   //节省内存?
        event.setCancelled(true);
    }
}
