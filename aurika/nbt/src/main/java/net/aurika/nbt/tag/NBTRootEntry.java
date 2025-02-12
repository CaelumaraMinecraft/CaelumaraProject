package net.aurika.nbt.tag;

import org.jetbrains.annotations.NotNull;
import net.aurika.nbt.NBTTagId;
import net.aurika.nbt.stream.NBTBinaryIO;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.NBTStreamable;
import net.aurika.nbt.stream.internal.SurroundingNBTStream;
import net.aurika.nbt.stream.token.NBTToken;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NBTRootEntry implements ToNBTTag<NBTTagCompound>, NBTStreamable {
    private final String name;
    private final NBTTagCompound value;

    public NBTRootEntry(String name, NBTTagCompound value) {
        this.name = name;
        this.value = value;
    }

    public NBTTagCompound getValue() {
        return this.value;
    }

    public static NBTRootEntry readFrom(@NotNull NBTStream tokens) throws IOException {
        return NBTTagReader.readRoot(tokens);
    }

    public byte[] writeToArray() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(output);

            try {
                NBTBinaryIO.write(dataOutputStream, this);
            } catch (Throwable var6) {
                try {
                    dataOutputStream.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            dataOutputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return output.toByteArray();
    }

    @NotNull
    public NBTTagCompound toNBTTag() {
        Map<String, NBTTagCompound> map = new HashMap<>();
        map.put(this.name, this.value);
        return NBTTagCompound.of(map);
    }

    @NotNull
    public NBTStream stream() {
        return new SurroundingNBTStream(new NBTToken.Name(this.name, Optional.of(NBTTagId.COMPOUND)), this.value.stream(), (NBTToken) null);
    }
}
