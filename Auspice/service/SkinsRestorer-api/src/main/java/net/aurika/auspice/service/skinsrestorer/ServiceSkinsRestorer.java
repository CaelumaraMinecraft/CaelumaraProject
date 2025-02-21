package net.aurika.auspice.service.skinsrestorer;

import net.aurika.auspice.service.api.ServicedAPIHolder;
import net.aurika.auspice.service.api.ServiceSkin;
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;

public interface ServiceSkinsRestorer extends ServiceSkin {

    public static final ServicedAPIHolder<SkinsRestorer> API_HOLDER = new ServicedAPIHolder<>(null) {
        {
            initServiced();
        }

        @Override
        protected void initServiced() {
            try {
                set(SkinsRestorerProvider.get());
            } catch (Exception ignored) {
            }
        }
    };

    public static enum SkinValueType {
        /**
         * The URL skin value type.
         */
        URL,
        /**
         * The name skin value type.
         */
        NAME
    }
}
