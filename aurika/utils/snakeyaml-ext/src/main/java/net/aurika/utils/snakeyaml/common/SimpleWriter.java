package net.aurika.utils.snakeyaml.common;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.api.StreamDataWriter;

import java.io.IOException;
import java.io.Writer;

public class SimpleWriter implements StreamDataWriter {
    private final @NotNull Writer writer;

    public SimpleWriter(@NotNull Writer writer) {
        Checker.Arg.notNull(writer, "writer");
        this.writer = writer;
    }

    public void write(@NotNull String str) {
        Checker.Arg.notNull(str, "str");
        try {
            this.writer.write(str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void write(String str, int off, int len) {
        Checker.Arg.notNull(str, "str");
        try {
            this.writer.write(str, off, len);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closeWriter() {
        try {
            this.writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
