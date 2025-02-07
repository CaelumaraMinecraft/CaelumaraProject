package top.auspice.platform;

import top.auspice.utils.reflection.Reflect;

public enum StandardPlatforms implements SupportedPlatform {
    BUKKIT() {
        @Override
        public boolean isAvailable() {
            return false;
        }

        @Override
        public boolean contains(SupportedPlatform platform) {
            return false;
        }
    },

    BUKKIT_LEGACY() {
        @Override
        public boolean isAvailable() {
            return false;
        }

        @Override
        public boolean contains(SupportedPlatform platform) {
            return false;
        }
    },

    FOLIA() {
        @Override
        public boolean isAvailable() {
            return Reflect.classExists();
        }

        @Override
        public boolean contains(SupportedPlatform platform) {
            return false;
        }
    },

    ADVENTURE() {
        @Override
        public boolean isAvailable() {
            return Reflect.classExists("net.kyori.adventure.Adventure");
        }

        @Override
        public boolean contains(SupportedPlatform platform) {
            return false;
        }
    };

    public abstract boolean isAvailable();

    public abstract boolean contains(SupportedPlatform platform);
}
