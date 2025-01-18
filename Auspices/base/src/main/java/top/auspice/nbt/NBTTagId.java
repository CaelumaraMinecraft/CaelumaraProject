package top.auspice.nbt;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum NBTTagId {

    END(0, false),
    BYTE(1, true),
    SHORT(2, true),
    INT(3, true),
    LONG(4, true),
    FLOAT(5, true),
    DOUBLE(6, true),
    BYTE_ARRAY(7, false),
    STRING(8, true),
    LIST(9, false),
    COMPOUND(10, false),
    INT_ARRAY(11, false),
    LONG_ARRAY(12, false);

    private final int id;
    private final boolean isTerminal;
    @NotNull
    private static final NBTTagId[] VALUES = values();
    private static final NBTTagId[] BY_ID = new NBTTagId[]{END, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BYTE_ARRAY, STRING, LIST, COMPOUND, INT_ARRAY, LONG_ARRAY};

    NBTTagId(final int id, final boolean isTerminal) {
        this.id = id;
        this.isTerminal = isTerminal;
    }

    public boolean isTerminal() {
        return this.isTerminal;
    }

    public int id() {
        return this.id;
    }

    @NotNull
    public String toString() {
        return this.name() + "[id=" + this.id() + ']';
    }

    @NotNull
    public static NBTTagId fromId(final int id) {
        if (!(id >= 0 && id < BY_ID.length)) {
            throw new IllegalArgumentException("Invalid NBT ID: " + id);
        }
        return BY_ID[id];
    }

    @NotNull
    public static NBTTagId fromClassNameOfObject(@NotNull final Object nbtObject) {
        Objects.requireNonNull(nbtObject);
        String name = nbtObject.getClass().getSimpleName();

        return switch (name) {
            case "NBTTagEnd", "EndTag" -> NBTTagId.END;
            case "NBTTagByte", "ByteTag" -> NBTTagId.BYTE;
            case "NBTTagShort", "ShortTag" -> NBTTagId.SHORT;
            case "NBTTagInt", "IntTag" -> NBTTagId.INT;
            case "NBTTagLong", "LongTag" -> NBTTagId.LONG;
            case "NBTTagFloat", "FloatTag" -> NBTTagId.FLOAT;
            case "NBTTagDouble", "DoubleTag" -> NBTTagId.DOUBLE;
            case "NBTTagByteArray", "ByteArrayTag" -> NBTTagId.BYTE_ARRAY;
            case "NBTTagString", "StringTag" -> NBTTagId.STRING;
            case "NBTTagList", "ListTag" -> NBTTagId.LIST;
            case "NBTTagCompound", "CompoundTag" -> NBTTagId.COMPOUND;
            case "NBTTagIntArray", "IntArrayTag" -> NBTTagId.INT_ARRAY;
            case "NBTTagLongArray", "LongArrayTag" -> NBTTagId.LONG_ARRAY;
            default ->
                    throw new UnsupportedOperationException("Unknown NBT type: " + nbtObject.getClass().getSimpleName());
        };

    }

}
