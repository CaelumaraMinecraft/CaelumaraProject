package top.auspice.data.compressor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.key.NSedKey;
import top.auspice.key.NSKeyed;
import top.auspice.server.location.OldLocation;
import top.auspice.utils.unsafe.io.ByteArrayOutputStream;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class DataCompressor {
    public static final DataCompressRegistry REGISTRY = new DataCompressRegistry();
    private final ByteArrayOutputStream a;
    private final DeflaterOutputStream b;
    private boolean c;

    public DataCompressor(int var1) {
        this.a = new ByteArrayOutputStream(var1);
        this.b = new DeflaterOutputStream(this.a, new Deflater(), var1 == 0 ? 8192 : Math.min(var1, 8192));
    }

    public final ByteArrayOutputStream result() {
        if (this.c) {
            throw new IllegalStateException("Already used");
        } else {
            try {
                this.b.close();
                this.a.close();
                this.c = true;
                return this.a;
            } catch (IOException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public final DataCompressor ensureAdditionalCapacity(int var1) {
        this.a.ensureCapacity(this.a.size() + var1);
        return this;
    }

    public final void registerSize(Class<?> var1) {
        if (!this.c) {
            throw new IllegalComponentStateException("Cannot register size when open");
        } else {
            REGISTRY.registerSize(var1, this.a.size());
        }
    }

    public final DataCompressor compress(ByteBuffer byteBuffer) {
        this.compress(byteBuffer.array());
        return this;
    }

    public final DataCompressor compressDynamically(Object o) {
        REGISTRY.compress(this, o);
        return this;
    }

    public final DataCompressor compress(byte[] byteArray) {
        try {
            this.b.write(byteArray);
            return this;
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public final DataCompressor compressNull() {
        return this.compressByte((byte) 0);
    }

    public final DataCompressor compressByte(byte b) {
        try {
            this.b.write(b);
            return this;
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public final DataCompressor compress(short s) {
        return this.compress(ByteBuffer.allocate(2).putShort(s));
    }

    public final DataCompressor compress(char c) {
        return this.compress(ByteBuffer.allocate(2).putChar(c));
    }

    public final DataCompressor compress(float f) {
        return this.compress(ByteBuffer.allocate(4).putFloat(f));
    }

    public final DataCompressor compress(int i) {
        return this.compress(ByteBuffer.allocate(4).putInt(i));
    }

    public final DataCompressor compress(long l) {
        return this.compress(ByteBuffer.allocate(8).putLong(l));
    }

    public final DataCompressor compress(double d) {
        return this.compress(ByteBuffer.allocate(8).putDouble(d));
    }

    public final DataCompressor compress(SimpleLocation location) {
        if (location == null) {
            return this.compressNull();
        } else {
            return this.compress(location.getWorld()).compress(location.getX()).compress(location.getY()).compress(location.getZ()).compress(location.getYaw()).compress(location.getPitch());
        }
    }


    public final DataCompressor compress(NSKeyed nsKeyed) {
        return this.compress(nsKeyed.getNamespacedKey());
    }

    public final DataCompressor compress(NSedKey nsKey) {
        return nsKey == null ? this.compressNull() : this.compress(nsKey.asString());
    }

    public final DataCompressor compress(UUID uuid) {
        return uuid == null ? this.compressNull() : this.compress(uuid.getLeastSignificantBits()).compress(uuid.getMostSignificantBits());
    }

    public final DataCompressor compress(boolean b) {
        this.compressByte((byte) (b ? 1 : 0));
        return this;
    }

    public final <T> DataCompressor compressNotNull(T var1, BiConsumer<DataCompressor, T> var2) {
        if (var1 == null) {
            return this.compressNull();
        } else {
            var2.accept(this, var1);
            return this;
        }
    }

    public final DataCompressor compress(String string) {
        return string == null ? this.compressNull() : this.compress(string.length()).compress(string.getBytes(StandardCharsets.UTF_8));
    }

    public final DataCompressor compress(Enum<?> e) {
        return e == null ? this.compressNull() : this.compress(e.ordinal());
    }

    public final DataCompressor compress(Color color) {
        return color == null ? this.compressNull() : this.compress(color.getRGB());
    }

    public final DataCompressor compress(Inventory var1) {
        this.compress(var1.getSize());
        this.ensureAdditionalCapacity(var1.getSize() * 500);
        ItemStack[] var5;
        int var2 = (var5 = var1.getContents()).length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ItemStack var4;
            if ((var4 = var5[var3]) != null && var4.getType() != Material.AIR) {
                this.compress(var4.toString());
            }
        }

        return this;
    }

    public final <T> DataCompressor compress(Collection<T> var1) {
        return this.compress(var1, 0, DataCompressor::compressDynamically);
    }

    public final <T> DataCompressor compress(Collection<T> var1, BiConsumer<DataCompressor, T> var2) {
        return this.compress(var1, 0, var2);
    }

    public final <T> DataCompressor compress(Collection<T> collection, int var2, BiConsumer<DataCompressor, T> var3) {
        Objects.requireNonNull(collection, "Cannot compress null collection");
        Objects.requireNonNull(var3, "Compress function cannot be null");
        int var4;
        if ((var4 = collection.size()) == 0) {
            return this.compressNull();
        } else {
            if (var2 == 0) {
                var2 = var4;
            }

            this.ensureAdditionalCapacity(var2 << 1);
            this.compress(collection.size());

            for (T var6 : collection) {
                var3.accept(this, var6);
            }

            return this;
        }
    }

    public final <K, V> DataCompressor compress(Map<K, V> var1) {
        return this.compress(var1, 0, DataCompressor::compressDynamically, DataCompressor::compressDynamically);
    }

    public final <K, V> DataCompressor compress(Map<K, V> var1, BiConsumer<DataCompressor, K> var2, BiConsumer<DataCompressor, V> var3) {
        return this.compress(var1, 0, var2, var3);
    }

    public final <K, V> DataCompressor compress(Map<K, V> var1, int var2, BiConsumer<DataCompressor, K> var3, BiConsumer<DataCompressor, V> var4) {
        int var5;
        if ((var5 = var1.size()) == 0) {
            return this.compressNull();
        } else {
            if (var2 == 0) {
                var2 = var5;
            }

            this.ensureAdditionalCapacity(var2 * 3);
            this.compress(var1.size());

            for (Map.Entry<K, V> kvEntry : var1.entrySet()) {
                var3.accept(this, kvEntry.getKey());
                V var8 = kvEntry.getValue();
                var4.accept(this, var8);
            }

            return this;
        }
    }
}
