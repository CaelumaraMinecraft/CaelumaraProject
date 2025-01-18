package top.auspice.config.yaml.snakeyaml.common;

import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.api.StreamDataWriter;
import top.auspice.utils.Checker;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public class SimpleWriter implements StreamDataWriter {
    private final @NotNull Writer writer;

    public SimpleWriter(@NotNull Writer writer) {
        Checker.Argument.checkNotNull(writer, "writer");
        this.writer = writer;
    }

    public void write(@NotNull String str) {
        Checker.Argument.checkNotNull(str, "str");
        try {
            this.writer.write(str);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public void write(String str, int off, int len) {
        Checker.Argument.checkNotNull(str, "str");
        try {
            this.writer.write(str, off, len);
        } catch (IOException var5) {
            var5.printStackTrace();
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