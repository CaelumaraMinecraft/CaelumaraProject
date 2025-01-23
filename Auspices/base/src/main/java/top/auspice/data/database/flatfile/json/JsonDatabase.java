package top.auspice.data.database.flatfile.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.object.DataObject;
import top.auspice.data.handlers.abstraction.DataHandler;
import top.auspice.utils.gson.KingdomsGson;
import top.auspice.utils.logging.AuspiceLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class JsonDatabase {

    private JsonDatabase() {
    }

    public static @Nullable <T extends DataObject.Impl> T load(@NotNull String string, @NotNull Path path, @NotNull DataHandler<T> dataHandler, @NotNull BufferedReader bufferedReader, @NotNull Function<? super JsonObject, ? extends T> converter) {
        Intrinsics.checkNotNullParameter(string, "");
        Intrinsics.checkNotNullParameter(path, "");
        Intrinsics.checkNotNullParameter(dataHandler, "");
        Intrinsics.checkNotNullParameter(bufferedReader, "");
        Intrinsics.checkNotNullParameter(converter, "");
        try {
            JsonObject jsonObject = KingdomsGson.parse(bufferedReader);
            Intrinsics.checkNotNull(bufferedReader);
            T o = converter.apply(jsonObject);
            if (o == null) {
                AuspiceLogger.error("Could not load data for '" + string + "' with adapter " + dataHandler + " JSON:(" + Files.lines(path).collect(Collectors.joining()) + ") Deleting their data...");
                Files.delete(path);
                return null;
            }
            return o;
        } catch (JsonSyntaxException jsonSyntaxException) {
            try {
                String var1 = Files.lines(path).collect(Collectors.joining());
                AuspiceLogger.error("Malformed JSON data '" + path.toAbsolutePath() + "' with adapter " + dataHandler + " JSON:(" + var1 + ") Deleting their data...");
                if (var1.length() <= 2) {
                    Files.delete(path);
                }
                jsonSyntaxException.printStackTrace();
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        return null;
    }

    public static <T extends DataObject.Impl> void save(@NotNull T t, @NotNull DataHandler<T> dataHandler, @NotNull BufferedWriter bufferedWriter) {
        Intrinsics.checkNotNullParameter(t, "");
        Intrinsics.checkNotNullParameter(dataHandler, "");
        Intrinsics.checkNotNullParameter(bufferedWriter, "");
        JsonObject jsonObject = new JsonObject();
        JsonObjectDataProvider jsonObjectDataProvider = new JsonObjectDataProvider(null, jsonObject);
        dataHandler.save(jsonObjectDataProvider, t);
        try {
            KingdomsGson.toJson(jsonObject, bufferedWriter);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}
 