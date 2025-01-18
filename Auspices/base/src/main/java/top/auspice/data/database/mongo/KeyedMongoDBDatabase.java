package top.auspice.data.database.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.ReplaceOneModel;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.bson.BsonDocumentReader;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.data.database.base.KeyedKingdomsDatabase;
import top.auspice.data.database.dataprovider.SectionableDataGetter;
import top.auspice.data.database.dataprovider.SectionableDataSetter;
import top.auspice.data.handlers.abstraction.KeyedDataHandler;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class KeyedMongoDBDatabase<K, T extends KeyedAuspiceObject<K>> extends MongoDBDatabase<T> implements KeyedKingdomsDatabase<K, T> {
    @NotNull
    private final KeyedDataHandler<K, T> dataHandler;
    private int b;

    private KeyedMongoDBDatabase(@NotNull KeyedDataHandler<K, T> var1, MongoCollection<Document> var2) {
        super(var2);
        this.dataHandler = var1;
        this.b = 10;
    }

    @NotNull
    protected KeyedDataHandler<K, T> getDataHandler() {
        return this.dataHandler;
    }

    private MongoIdQueryContainer<K> a(K var1) {
        return new MongoIdQueryContainer(var1, this.getDataHandler().getIdHandler().getKlass());
    }

    private static Document b(Object var0) {
        return new Document("_id", var0);
    }

    @Nullable
    public T load(@NotNull K var1) {
        Objects.requireNonNull(var1, "");
        Document var10000 = this.getCollection().find(this.a(var1)).first();
        if (var10000 == null) {
            return null;
        } else {
            MongoDataProvider var3 = new MongoDataProvider(null, var10000);
            return this.getDataHandler().load(var3, var1);
        }
    }

    private K c(Object var1) {
        if (var1 instanceof Document) {
            Codec<K> var10000 = this.getCollection().getCodecRegistry().get(this.getDataHandler().getIdHandler().getKlass());
            Intrinsics.checkNotNull(var10000);
            K var3 = var10000.decode(new BsonDocumentReader(((Document) var1).toBsonDocument()), MongoDBDatabase.DEFAULT_DECODER_CONTEXT);
            Objects.requireNonNull(var3, "");
            return var3;
        } else {
            Intrinsics.checkNotNull(var1);
            return (K) var1;
        }
    }

    public void load(@NotNull Collection<K> var1, @NotNull Consumer<T> var2) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        if (!var1.isEmpty()) {
            Document var5 = new Document("_id", new Document("$in", var1));

            for (Document var3 : this.getCollection().find(var5)) {
                Intrinsics.checkNotNull(var3);
                MongoDataProvider var4 = new MongoDataProvider(null, var3);
                Object var10001 = var3.get("_id");
                Intrinsics.checkNotNull(var10001);
                K var7 = this.c(var10001);
                T var8 = this.getDataHandler().load((SectionableDataGetter) var4, var7);
                var2.accept(var8);
            }

        }
    }

    public void save(@NotNull T var1) {
        Objects.requireNonNull(var1, "");
        K var10001 = var1.getKey();
        Objects.requireNonNull(var10001, "");
        MongoIdQueryContainer<K> var2 = this.a(var10001);
        K var10000 = var1.getKey();
        Objects.requireNonNull(var10000, "");
        Document var3 = b(var10000);
        MongoDataProvider var4 = new MongoDataProvider(null, var3);
        this.getDataHandler().save(var4, var1);
        this.getCollection().replaceOne(var2, var3, MongoDBDatabase.UPSERT);
    }

    public void delete(@NotNull K var1) {
        Objects.requireNonNull(var1, "");
        this.getCollection().deleteOne(this.a(var1));
    }

    public boolean hasData(@NotNull K var1) {
        Objects.requireNonNull(var1, "");
        return this.getCollection().find(this.a(var1)).first() != null;
    }

    @NotNull
    public Collection<K> getAllDataKeys() {
        List<K> var1 = new ArrayList<>((int) this.getCollection().estimatedDocumentCount());

        for (Document var3 : this.getCollection().find()) {
            Object var10001 = var3.get("_id");
            Intrinsics.checkNotNull(var10001);
            K var4 = this.c(var10001);
            var1.add(var4);
        }

        return var1;
    }

    public void deleteAllData() {
        this.getCollection().drop();
    }

    @NotNull
    public Collection<T> loadAllData(@Nullable Predicate<K> var1) {
        List var2 = new ArrayList(this.b);
        MongoCursor var3 = this.getCollection().find().iterator();

        while (true) {
            MongoDataProvider var5;
            Object var7;
            do {
                if (!var3.hasNext()) {
                    this.b = RangesKt.coerceAtLeast(this.b, var2.size());
                    return (Collection) var2;
                }

                Document var4 = (Document) var3.next();
                Intrinsics.checkNotNull(var4);
                var5 = new MongoDataProvider(null, var4);
                Object var10001 = var4.get("_id");
                Intrinsics.checkNotNull(var10001);
                var7 = this.c(var10001);
            } while (var1 != null && !var1.test(var7));

            try {
                var2.add(this.getDataHandler().load((SectionableDataGetter) var5, var7));
            } catch (Throwable var6) {
                KLogger.error("Error while loading '" + var7 + "' of type " + this.getDataHandler().getClass().getSimpleName() + " (Skipping):");
                var6.printStackTrace();
            }
        }
    }

    public void save(@NotNull Collection<T> var1) {
        Objects.requireNonNull(var1, "");
        if (!var1.isEmpty()) {
            List var2 = new ArrayList(var1.size());
            Iterator var3 = var1.iterator();

            while (var3.hasNext()) {
                KeyedAuspiceObject var4 = (KeyedAuspiceObject) var3.next();
                Object var10001 = var4.getKey();
                Objects.requireNonNull(var10001, "");
                MongoIdQueryContainer var5 = this.a(var10001);
                Object var10000 = var4.getKey();
                Objects.requireNonNull(var10000, "");
                Document var6 = b(var10000);
                MongoDataProvider var7 = new MongoDataProvider(null, var6);
                this.getDataHandler().save((SectionableDataSetter) var7, var4);
                var2.add(new ReplaceOneModel(var5, var6, MongoDBDatabase.Companion.getUPSERT$core()));
            }

            this.getCollection().bulkWrite(var2, (new BulkWriteOptions()).ordered(false).comment("Save batch data of " + var1.size()));
        }
    }

    public void close() {
        MongoDBDatabase.CLIENT.close();
    }

    @NotNull
    public static <K, T extends KeyedAuspiceObject<K>> KeyedMongoDBDatabase<K, T> withCollection(@NotNull String var1, @NotNull KeyedDataHandler<K, T> var2) {
        return new KeyedMongoDBDatabase<>(var2, MongoDBDatabase.getCollection(var1));
    }


}
