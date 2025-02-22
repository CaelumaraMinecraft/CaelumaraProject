package top.mckingdom.powerful_territory.constants.land_categories;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.kingdoms.constants.namespace.Lockable;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.NamespacedRegistry;

import java.util.HashMap;
import java.util.Map;

public class LandCategoryRegistry extends NamespacedRegistry<LandCategory> implements Lockable {
    public static boolean unLocked = true;

    @Override
    public void lock() {
        unLocked = false;
    }

    static final Map<String, LandCategory> a = new HashMap<>();


    protected final Map<Namespace, LandCategory> getRawRegistry() {
        return this.registry;
    }

    @Override
    public final void register(LandCategory landCategory) {
        super.register(landCategory);
        a.put(landCategory.toConfigKey(), landCategory);
//        StandardLandCategory.class.cast(null);
    }

    public static @Nullable LandCategory getLandCategoryFromConfigName(String name) {
        return a.get(name);
    }
}