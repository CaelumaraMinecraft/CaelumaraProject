package net.aurika.ecliptor.database.sql.schema;

import net.aurika.auspice.utils.nonnull.NonNullList;
import net.aurika.auspice.utils.nonnull.NonNullMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLSchemaReader {

  private static final Pattern a = Pattern.compile("\\[\\[(\\w+)\\]\\]");
  private final List<String> b = new NonNullList<>(100);
  private final Map<String, String> c = new NonNullMap<>(5);
  private String d;
  private final StringBuilder e = new StringBuilder();
  private String f;
  private int g = 0;

  public SQLSchemaReader() {
  }

  public void error(String var1) {
    throw new IllegalStateException(var1 + " at line " + this.g + ": " + this.f);
  }

  public List<String> getStatements(InputStream var1) throws IOException {
    Objects.requireNonNull(var1, "Cannot get statements from null stream");
    BufferedReader var7 = new BufferedReader(new InputStreamReader(var1, StandardCharsets.UTF_8));

    try {
      this.g = 0;

      while ((this.f = var7.readLine()) != null) {
        ++this.g;
        this.f = this.f.trim();
        if (!this.f.isEmpty() && !this.f.startsWith("--") && !this.f.startsWith("#")) {
          if (this.f.startsWith("{{")) {
            if (this.d != null) {
              this.error("Unexpected start of macro");
            }

            this.d = this.f.substring(2).trim();
          } else if (this.f.startsWith("}}")) {
            if (this.d == null) {
              this.error("Unexpected end of macro");
            }

            this.c.put(this.d, this.e.toString());
            this.d = null;
            this.e.setLength(0);
          } else {
            int var2;
            if ((var2 = this.f.indexOf("--")) > 0) {
              this.f = this.f.substring(0, var2);
            }

            String var4;
            for (Matcher var3 = a.matcher(this.f); var3.find(); this.f = var3.replaceAll(var4)) {
              String var8 = var3.group(1);
              if ((var4 = this.c.get(var8)) == null) {
                this.error("Unknown macro '" + var8 + '\'');
              }
            }

            this.e.append(this.f);
            if (this.f.endsWith(";")) {
              this.e.deleteCharAt(this.e.length() - 1);
              this.b.add(this.e.toString());
              this.e.setLength(0);
            }
          }
        }
      }
    } catch (Throwable var6) {
      try {
        var7.close();
      } catch (Throwable var5) {
        var6.addSuppressed(var5);
      }

      throw var6;
    }

    var7.close();
    return this.b;
  }

}
