package net.aurika.util.gson;

import com.google.gson.*;
import com.google.gson.internal.GsonBuildConfig;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Objects;

public final class AurikaGson {

  private static JsonReader newJsonReader(@NotNull Reader reader) {
    return new JsonReader(reader);
  }

  public static final Gson GSON = new GsonBuilder()
      .setPrettyPrinting().disableHtmlEscaping().enableComplexMapKeySerialization()
      .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
      .create();

  private static final class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext {

    @Override
    public JsonElement serialize(Object src) {
      return AurikaGson.GSON.toJsonTree(src);
    }

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc) {
      return AurikaGson.GSON.toJsonTree(src, typeOfSrc);
    }

    @Override
    public <R> R deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
      return AurikaGson.GSON.fromJson(json, typeOfT);
    }

  }

  private static final @NotNull GsonContextImpl CONTEXT = new GsonContextImpl();

  public static @NotNull JsonElement fromString(@NotNull String json) {
    Validate.Arg.notNull(json, "json");
    // JsonParser.parseString(json) doesn't exist for older versions of GSON.
    try {
      JsonReader jsonReader = new JsonReader(new StringReader(json));
      JsonElement element = parseReader(jsonReader);
      if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
        throw new JsonSyntaxException("Did not consume the entire document.");
      } else {
        return element;
      }
    } catch (MalformedJsonException | NumberFormatException ex) {
      throw new JsonSyntaxException(ex);
    } catch (IOException ex) {
      throw new JsonIOException(ex);
    }
  }

  private static JsonElement parseReader(@NotNull JsonReader reader) throws JsonIOException, JsonSyntaxException {
    try {
      return Streams.parse(reader);
    } catch (StackOverflowError | OutOfMemoryError ex) {
      throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", ex);
    }
  }

  public static String toString(@NotNull JsonElement element) {
    Objects.requireNonNull(element, "element");
    try {
      StringWriter stringWriter = new StringWriter();
      JsonWriter jsonWriter = new JsonWriter(stringWriter);
      jsonWriter.setStrictness(Strictness.LEGACY_STRICT);
      jsonWriter.setSerializeNulls(false);
      jsonWriter.setHtmlSafe(false);
      Streams.write(element, jsonWriter);
      return stringWriter.toString();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private static JsonWriter newJsonWriter(@NotNull Writer writer) throws IOException {
    JsonWriter jsonWriter = new JsonWriter(writer);
    jsonWriter.setIndent("  ");
    jsonWriter.setStrictness(Strictness.LEGACY_STRICT);
    jsonWriter.setSerializeNulls(false);
    jsonWriter.setHtmlSafe(false);
    return jsonWriter;
  }

  public static String toJson(Object src) {
    return AurikaGson.GSON.toJson(src);
  }

  public static void toJson(JsonObject object, Appendable writer) throws IOException {
    JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
    Streams.write(object, jsonWriter);
  }

  private static void assertFullConsumption(Object obj, JsonReader reader) {
    try {
      if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
        throw new JsonIOException("JSON document was not fully consumed.");
      }
    } catch (MalformedJsonException e) {
      throw new JsonSyntaxException(e);
    } catch (IOException e) {
      throw new JsonIOException(e);
    }
  }

  public static JsonObject parse(Reader json) throws JsonIOException, JsonSyntaxException {
    JsonReader reader = newJsonReader(json);
    JsonObject object;
    reader.setStrictness(Strictness.LEGACY_STRICT);
    try {
      reader.peek();
      object = (JsonObject) Streams.parse(reader);
    } catch (IllegalStateException e) {
      throw new JsonSyntaxException(e);
    } catch (IOException e) {
      // TODO(inder): Figure out whether it is indeed right to rethrow this as JsonSyntaxException
      throw new JsonSyntaxException(e);
    } catch (AssertionError e) {
      throw new AssertionError("AssertionError (GSON " + GsonBuildConfig.VERSION + "): " + e.getMessage(), e);
    }
    assertFullConsumption(object, reader);
    return object;
  }

  private static JsonElement parse(@NotNull JsonReader reader) throws JsonParseException {
    boolean isEmpty = true;
    try {
      reader.peek();
      isEmpty = false;
      return TypeAdapters.JSON_ELEMENT.read(reader);
    } catch (EOFException e) {
      /*
       * For compatibility with JSON 1.5 and earlier, we return a JsonNull for
       * empty documents instead of throwing.
       */
      if (isEmpty) {
        return JsonNull.INSTANCE;
      }
      // The stream ended prematurely so it is likely a syntax error.
      throw new JsonSyntaxException(e);
    } catch (MalformedJsonException e) {
      throw new JsonSyntaxException(e);
    } catch (IOException e) {
      throw new JsonIOException(e);
    } catch (NumberFormatException e) {
      throw new JsonSyntaxException(e);
    }
  }

}
