package top.mckingdom.props.managers;

import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.kingdoms.abstraction.processor.AbstractKingdomsProcessor;
import org.kingdoms.abstraction.processor.KingdomsProcessor;
import org.kingdoms.locale.messenger.Messenger;
import org.kingdoms.nbt.tag.NBTTagCompound;
import org.kingdoms.nbt.tag.NBTTagType;
import org.kingdoms.platform.bukkit.item.ItemNBT;

import java.util.Objects;

public class PropsInteractProcessor extends AbstractKingdomsProcessor {

    private final PlayerInteractEvent event;

    public PropsInteractProcessor(PlayerInteractEvent event) {
        this.event = event;
    }

    @Override
    public KingdomsProcessor reprocess() {
        return new PropsInteractProcessor(this.event);
    }

    @Override
    public KingdomsProcessor process() {
        super.process();
        return this;
    }

    @Override
    public Messenger processIssue() {
        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();
        if (item != null) {                                            //TODO: in disable world
            NBTTagCompound nbt = ItemNBT.getTag(item);
            NBTTagCompound kingdomsNBT = nbt.tryGetTag("Kingdoms", NBTTagType.COMPOUND);
        }

        return null;
    }
}
