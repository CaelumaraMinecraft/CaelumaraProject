package top.auspice.bukkit.server.core;

import top.auspice.api.annotations.bookmark.Bookmark;
import top.auspice.api.annotations.bookmark.BookmarkType;

// https://github.com/rowan-vr/CustomAdvancements
// https://www.spigotmc.org/resources/ultimateadvancementapi-1-15-1-21-1.95585/
// https://www.spigotmc.org/resources/crazy-advancements-api.51741/
// https://mappings.dev/1.21.1/net/minecraft/network/protocol/game/ClientboundUpdateAdvancementsPacket.html
@Bookmark(BookmarkType.REMINDER)
public class Advancements {
//     public CompletableFuture<Void> sendAdvancementPacketImpl(Player player, boolean clear, Collection<AdvancementNode> advancements, Set<ResourceLocation> remove, Map<ResourceLocation, AdvancementProgress> progress) {
//         return CompletableFuture.runAsync(() -> {
//             ClientboundUpdateAdvancementsPacket packet = new ClientboundUpdateAdvancementsPacket(clear, advancements.stream().map(AdvancementNode::holder).toList(), remove, progress);
//             ((CraftPlayer) player).getHandle().connection.send(packet);
//         });
//     }
}
