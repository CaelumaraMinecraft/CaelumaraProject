package top.auspice.nbt.stream.impl;

import top.auspice.nbt.stream.exception.NBTParseException;
import top.auspice.nbt.stream.token.NBTToken;

public class ValueCounter {
    private static final byte BYTE_ARRAY = 1;
    private static final byte INT_ARRAY = 2;
    private static final byte LONG_ARRAY = 3;
    private int count;
    private int compounds;
    private int lists;
    private byte arrayType;

    public ValueCounter() {
    }

    public void add(NBTToken token) {
        if (token.isTerminal() && !this.isNested()) {
            ++this.count;
        } else {
            if (token instanceof NBTToken.CompoundStart) {
                ++this.compounds;
            } else if (token instanceof NBTToken.CompoundEnd) {
                --this.compounds;
                if (this.compounds < 0) {
                    throw new NBTParseException("Compound end without start");
                }

                if (!this.isNested()) {
                    ++this.count;
                }
            } else if (token instanceof NBTToken.ListStart) {
                ++this.lists;
            } else if (token instanceof NBTToken.ListEnd) {
                --this.lists;
                if (this.lists < 0) {
                    throw new NBTParseException("List end without start");
                }

                if (!this.isNested()) {
                    ++this.count;
                }
            } else if (token instanceof NBTToken.ByteArrayStart) {
                this.arrayType = 1;
            } else if (token instanceof NBTToken.ByteArrayEnd) {
                if (this.arrayType != 1) {
                    throw new NBTParseException("Byte array end without start");
                }

                this.arrayType = 0;
                if (!this.isNested()) {
                    ++this.count;
                }
            } else if (token instanceof NBTToken.IntArrayStart) {
                this.arrayType = 2;
            } else if (token instanceof NBTToken.IntArrayEnd) {
                if (this.arrayType != 2) {
                    throw new NBTParseException("Int array end without start");
                }

                this.arrayType = 0;
                if (!this.isNested()) {
                    ++this.count;
                }
            } else if (token instanceof NBTToken.LongArrayStart) {
                this.arrayType = 3;
            } else if (token instanceof NBTToken.LongArrayEnd) {
                if (this.arrayType != 3) {
                    throw new NBTParseException("Long array end without start");
                }

                this.arrayType = 0;
                if (!this.isNested()) {
                    ++this.count;
                }
            }

        }
    }

    public int count() {
        return this.count;
    }

    public boolean isNested() {
        return this.lists > 0 || this.compounds > 0;
    }
}
