package top.auspice.nbt.snbt;

import org.jetbrains.annotations.NotNull;
import top.auspice.nbt.IOFunction;
import top.auspice.nbt.snbt.reader.SNBTReader;
import top.auspice.nbt.snbt.reader.SNBTTokenizer;
import top.auspice.nbt.stream.NBTStream;
import top.auspice.nbt.stream.NBTStreamable;
import top.auspice.nbt.tag.NBTTag;

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
