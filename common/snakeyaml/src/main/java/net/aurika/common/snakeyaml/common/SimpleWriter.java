package net.aurika.common.snakeyaml.common;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.api.StreamDataWriter;

import java.io.IOException;
import java.io.Writer;

public class SimpleWriter implements StreamDataWriter {

  private final @NotNull Writer writer;

  public SimpleWriter(@NotNull Writer writer) {
    Validate.Arg.notNull(writer, "writer");
    this.writer = writer;
  }

  public void write(@NotNull String str) {
    Validate.Arg.notNull(str, "str");
    try {
      this.writer.write(str);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void write(String str, int off, int len) {
    Validate.Arg.notNull(str, "str");
    try {
      this.writer.write(str, off, len);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
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
