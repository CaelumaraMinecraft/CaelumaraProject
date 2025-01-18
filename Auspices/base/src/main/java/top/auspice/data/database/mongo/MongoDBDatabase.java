package top.auspice.data.database.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.constants.base.AuspiceObject;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.base.KingdomsDatabase;
import top.auspice.data.database.sql.DatabaseProperties;
import top.auspice.data.handlers.abstraction.DataHandler;

import java.util.Objects;

public abstract class MongoDBDatabase<T extends AuspiceObject> implements KingdomsDatabase<T> {
    @NotNull
    public static final Companion Companion = new Companion();
    @NotNull
    protected final MongoCollection<Document> a;
    @NotNull
    protected static final MongoClient CLIENT;
    @NotNull
    protected static final MongoDatabase DATABASE;
    protected static final ReplaceOptions UPSERT = (new ReplaceOptions()).upsert(true);
    @NotNull
    protected static final String PRIMARY_KEY_ID = "_id";
    protected static final DecoderContext DEFAULT_DECODER_CONTEXT = DecoderContext.builder().build();

    protected MongoDBDatabase(@NotNull MongoCollection<Document> var1) {
        Objects.requireNonNull(var1);
        this.a = var1;
    }

    @NotNull
    protected final MongoCollection<Document> getCollection() {
        return this.a;
    }

    @NotNull
    protected abstract DataHandler<T> getDataHandler();

    @NotNull
    public DatabaseType getDatabaseType() {
        return DatabaseType.MongoDB;
    }

    public void close() {
        CLIENT.close();
    }

    @NotNull
    public static MongoCollection<Document> getCollection(@NotNull String var1) {
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
        (var2 = var1 = MongoClientSettings.builder()).applicationName("KingdomsX");
        String databaseUri = AuspiceGlobalConfig.DATABASE_URI.getString();
        CharSequence var4;
        if ((var4 = databaseUri) != null && !var4.isEmpty()) {
            var2.applyConnectionString(new ConnectionString(databaseUri));
        } else if ((var4 = AuspiceGlobalConfig.DATABASE_USERNAME.getString()) != null && !var4.isEmpty()) {
            String var10001 = var0.getUser();
            String var10002 = var0.getDatabaseName();
            char[] var10003 = var0.getPassword().toCharArray();
            Objects.requireNonNull(var10003, "");
            var2.credential(MongoCredential.createCredential(var10001, var10002, var10003));
        }

        var2.retryReads(false);
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
        Objects.requireNonNull(var7, "");
        CLIENT = var7;
        MongoDatabase var8 = var7.getDatabase(var0.getDatabaseName());
        Objects.requireNonNull(var8, "");
        DATABASE = var8;
    }

    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public MongoClient getCLIENT$core() {
            return MongoDBDatabase.CLIENT;
        }

        @NotNull
        public MongoDatabase getDATABASE$core() {
            return MongoDBDatabase.DATABASE;
        }

        public ReplaceOptions getUPSERT$core() {
            return MongoDBDatabase.UPSERT;
        }

        public DecoderContext getDEFAULT_DECODER_CONTEXT$core() {
            return MongoDBDatabase.DEFAULT_DECODER_CONTEXT;
        }

    }
}
