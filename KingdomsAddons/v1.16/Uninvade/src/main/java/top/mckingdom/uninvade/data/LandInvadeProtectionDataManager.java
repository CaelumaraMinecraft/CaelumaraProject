package top.mckingdom.uninvade.data;

import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.location.SimpleChunkLocation;
import org.kingdoms.constants.metadata.KingdomMetadata;
import org.kingdoms.constants.metadata.StandardKingdomMetadata;
import org.kingdoms.constants.metadata.StandardKingdomMetadataHandler;
import org.kingdoms.constants.namespace.Namespace;

import java.util.Objects;

public class LandInvadeProtectionDataManager {
    public static final StandardKingdomMetadataHandler PROTECTION_STATUS_META_HANDLER = new StandardKingdomMetadataHandler(new Namespace("PowerfulTerritory", "INVADE_PROTECTION")) {};
                                                                                                                                                       //命名空间根据服务器需求进行修改

    public static InvadeProtection getInvadeProtectionStatus(SimpleChunkLocation chunk) {
        return getInvadeProtectionStatus(chunk.getLand());
    }

    @Nullable
    public static InvadeProtection getInvadeProtectionStatus(Land land) {
        if (land == null || !land.isClaimed()) {
            return null;
        }
        KingdomMetadata meta = land.getMetadata().get(PROTECTION_STATUS_META_HANDLER);
        if (meta == null) {
            return StandardInvadeProtection.NO_PROTECTION;
        } else {
            return Objects.requireNonNullElse(
                    InvadeProtectionRegistry.get().getRegistered(Namespace.fromString(((String) meta.getValue()))),
                    StandardInvadeProtection.NO_PROTECTION
            );
        }
    }


    public static void setInvadeProtectionStatus(SimpleChunkLocation chunk, InvadeProtection status) {
        setInvadeProtectionStatus(chunk.getLand(), status);
    }

    public static void setInvadeProtectionStatus(Land land, InvadeProtection status) {
        if (land != null && land.isClaimed()) {
            if (status == StandardInvadeProtection.NO_PROTECTION) {
                land.getMetadata().remove(PROTECTION_STATUS_META_HANDLER);
            } else {
                land.getMetadata().put(PROTECTION_STATUS_META_HANDLER, new StandardKingdomMetadata(status.getNamespace().asNormalizedString()));
            }
        }
    }


    public static void init() {
    }
}
