package net.aurika.ecliptor.database.flatfile.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.aurika.validate.Validate;
import net.aurika.ecliptor.handler.DataHandler;
import net.aurika.ecliptor.api.DataObject;
import net.aurika.util.gson.AurikaGson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class JsonDatabase {

    private JsonDatabase() {
    }

    public static <T extends DataObject> @Nullable T load(@NotNull String label, @NotNull Path file, @NotNull DataHandler<T> dataHandler, @NotNull BufferedReader reader, @NotNull Function<? super JsonObject, ? extends T> converter) {
        Validate.Arg.notNull(label, "label");
        Validate.Arg.notNull(file, "file");
        Validate.Arg.notNull(dataHandler, "dataHandler");
        Validate.Arg.notNull(reader, "reader");
        Validate.Arg.notNull(converter, "converter");

        try {
            JsonObject var9 = AurikaGson.parse(reader);
            Objects.requireNonNull(var9);
            DataObject var11;
            if ((var11 = converter.apply(var9)) == null) {
                AuspiceLogger.error("Could not load data for '" + label + "' with adapter " + dataHandler + " JSON:(" + Files.lines(file).collect(Collectors.joining()) + ") Deleting their data...");
                Files.delete(file);
                return null;
            } else {
                return (T) var11;
            }
        } catch (JsonSyntaxException var6) {
            JsonSyntaxException var8 = var6;

            try {
                String var10 = Files.lines(file).collect(Collectors.joining());
                AuspiceLogger.error("Malformed JSON data '" + file.toAbsolutePath() + "' with adapter " + dataHandler + " JSON:(" + var10 + ") Deleting their data...");
                if (var10.length() <= 2) {
                    Files.delete(file);
                }

                var8.printStackTrace();
            } catch (IOException var5) {
                var5.printStackTrace();
            }

            return null;
        } catch (IOException var7) {
            throw new RuntimeException(var7);
        }
    }

    public static <T extends DataObject> void save(@NotNull T data, @NotNull DataHandler<T> dataHandler, @NotNull BufferedWriter writer) {
        Validate.Arg.notNull(data, "data");
        Validate.Arg.notNull(dataHandler, "dataHandler");
        Validate.Arg.notNull(writer, "writer");
        JsonObject var3 = new JsonObject();
        JsonObjectDataProvider var4 = new JsonObjectDataProvider(null, var3);
        dataHandler.save(var4, data);

        try {
            AurikaGson.toJson(var3, writer);
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }
    }
}
