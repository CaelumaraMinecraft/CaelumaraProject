package net.aurika.nbt.snbt;

import org.jetbrains.annotations.NotNull;
import net.aurika.nbt.IOFunction;
import net.aurika.nbt.snbt.reader.SNBTReader;
import net.aurika.nbt.snbt.reader.SNBTTokenizer;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.NBTStreamable;
import net.aurika.nbt.tag.NBTTag;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UncheckedIOException;

public class NBTStringIO {
    @NotNull
    public static NBTStream read(@NotNull Reader input) {
        return new SNBTReader(new SNBTTokenizer(input));
    }

    @NotNull
    public static NBTStream readFromString(@NotNull String input) {
        return read(new StringReader(input));
    }

    public static <R> R readUsing(@NotNull Reader input, @NotNull IOFunction<? super NBTStream, R> transform) throws IOException {
        return transform.apply(read(input));
    }

    public static <R> R readFromStringUsing(@NotNull String input, @NotNull IOFunction<? super NBTStream, R> transform) {
        try {
            return transform.apply(readFromString(input));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void write(@NotNull Appendable output, @NotNull NBTStreamable tokens) throws IOException {
        (new SNBTWriter()).write(output, tokens.stream());
    }

    public static String toString(NBTTag<?> tag) {
        return writeToString(tag);
    }

    public static String writeToString(@NotNull NBTStreamable tokens) {
        StringBuilder builder = new StringBuilder();

        try {
            write(builder, tokens);
        } catch (IOException e) {
            throw new AssertionError("No I/O to perform, so shouldn't throw an I/O exception", e);
        }

        return builder.toString();
    }

    private NBTStringIO() {
    }
}
