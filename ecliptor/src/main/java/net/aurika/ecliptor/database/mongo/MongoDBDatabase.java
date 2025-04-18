package net.aurika.ecliptor.database.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import net.aurika.ecliptor.api.DataObject;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.base.Database;
import net.aurika.ecliptor.database.sql.DatabaseProperties;
import net.aurika.ecliptor.handler.DataHandler;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class MongoDBDatabase<T extends DataObject> implements Database<T> {

  protected final @NotNull MongoCollection<Document> collection;

  protected static final @NotNull MongoClient CLIENT;
  protected static final @NotNull MongoDatabase DATABASE;
  protected static final ReplaceOptions UPSERT = (new ReplaceOptions()).upsert(true);
  protected static final @NotNull String PRIMARY_KEY_ID = "_id";
  protected static final @NotNull DecoderContext DEFAULT_DECODER_CONTEXT = DecoderContext.builder().build();

  protected MongoDBDatabase(@NotNull MongoCollection<Document> var1) {
    Objects.requireNonNull(var1);
    this.collection = var1;
  }

  protected final @NotNull MongoCollection<Document> getCollection() {
    return this.collection;
  }

  protected abstract @NotNull DataHandler<T> getDataHandler();

  public @NotNull DatabaseType getDatabaseType() {
    return DatabaseType.MongoDB;
  }

  public void close() {
    CLIENT.close();
  }

  public static @NotNull MongoCollection<Document> getCollection(@NotNull String var1) {
    Objects.requireNonNull(var1);
    var1 = AuspiceGlobalConfig.DATABASE_TABLE_PREFIX.getString() + '_' + var1;
    MongoCollection<Document> var10000 = DATABASE.getCollection(var1);
    Objects.requireNonNull(var10000, "");
    return var10000;
  }

  static {
    DatabaseProperties var0 = DatabaseProperties.defaults(DatabaseType.MongoDB);
    MongoClientSettings.Builder var1;
    MongoClientSettings.Builder var2;
    (var2 = var1 = MongoClientSettings.builder()).applicationName("Aurika");
    String databaseUri = AuspiceGlobalConfig.DATABASE_URI.getString();
    CharSequence var4;
    if ((var4 = databaseUri) != null && !var4.isEmpty()) {
      var2.applyConnectionString(new ConnectionString(databaseUri));
    } else if ((var4 = AuspiceGlobalConfig.DATABASE_USERNAME.getString()) != null && !var4.isEmpty()) {
      String var10001 = var0.user();
      String var10002 = var0.databaseName();
      char[] var10003 = var0.password().toCharArray();
      Objects.requireNonNull(var10003, "");
      var2.credential(MongoCredential.createCredential(var10001, var10002, var10003));
    }

    var2.retryReads(false);   // TODO use
    var2.retryWrites(true);
    var2.uuidRepresentation(UuidRepresentation.STANDARD);
    var2.serverApi(ServerApi.builder().version(ServerApiVersion.V1).deprecationErrors(true).strict(true).build());
    MongoClientSettings.Builder var10000 = var2;
    Codec<?>[] codecs = new Codec[2];
    codecs[0] = new SimpleChunkLocationEncoder();
    codecs[1] = new SimpleLocationEncoder();
    CodecRegistry[] codecRegistries = new CodecRegistry[2];
    codecRegistries[0] = MongoClientSettings.getDefaultCodecRegistry();
    codecRegistries[1] = CodecRegistries.fromCodecs(codecs);
    var10000.codecRegistry(CodecRegistries.fromRegistries(codecRegistries));
    MongoClient var7 = MongoClients.create(var1.build(), null);
    CLIENT = var7;
    MongoDatabase var8 = var7.getDatabase(var0.databaseName());
    Objects.requireNonNull(var8, "");
    DATABASE = var8;
  }
}
