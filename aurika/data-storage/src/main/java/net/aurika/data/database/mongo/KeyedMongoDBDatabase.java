package net.aurika.data.database.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.ReplaceOneModel;
import net.aurika.data.api.KeyedDataObject;
import net.aurika.data.api.handler.KeyedDataHandler;
import net.aurika.data.database.base.KeyedDatabase;
import org.bson.BsonDocumentReader;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class KeyedMongoDBDatabase<K, T extends KeyedDataObject<K>> extends MongoDBDatabase<T> implements KeyedDatabase<K, T> {

    private final @NotNull KeyedDataHandler<K, T> dataHandler;
    private int b;

    private KeyedMongoDBDatabase(@NotNull KeyedDataHandler<K, T> var1, MongoCollection<Document> var2) {
        super(var2);
        this.dataHandler = var1;
        this.b = 10;
    }

    protected @NotNull KeyedDataHandler<K, T> getDataHandler() {
        return this.dataHandler;
    }

    private MongoIdQueryContainer<K> a(K var1) {
        return new MongoIdQueryContainer<>(var1, this.getDataHandler().getIdHandler().getKlass());
    }

    private static Document b(Object var0) {
        return new Document(PRIMARY_KEY_ID, var0);
    }

    public @Nullable T load(@NotNull K var1) {
        Objects.requireNonNull(var1, "");
        Document var10000 = this.getCollection().find(this.a(var1)).first();
        if (var10000 == null) {
            return null;
        } else {
            MongoDataProvider var3 = new MongoDataProvider(null, var10000);
            return this.getDataHandler().load(var3, var1);
        }
    }

    private K c(Object obj) {
        if (obj instanceof Document document) {
            Codec<K> var10000 = this.getCollection().getCodecRegistry().get(this.getDataHandler().getIdHandler().getKlass());  // todo
            Objects.requireNonNull(var10000);
            K var3 = var10000.decode(new BsonDocumentReader(document.toBsonDocument()), MongoDBDatabase.DEFAULT_DECODER_CONTEXT);
            Objects.requireNonNull(var3, "");
            return var3;
        } else {
            Objects.requireNonNull(obj);
            return (K) obj;
        }
    }

    public void load(@NotNull Collection<K> keys, @NotNull Consumer<T> var2) {
        Objects.requireNonNull(keys);
        Objects.requireNonNull(var2);
        if (!keys.isEmpty()) {
            Document var5 = new Document(PRIMARY_KEY_ID, new Document("$in", keys));

            for (Document var3 : this.getCollection().find(var5)) {
                Objects.requireNonNull(var3);
                MongoDataProvider var4 = new MongoDataProvider(null, var3);
                Object var10001 = var3.get(PRIMARY_KEY_ID);
                Objects.requireNonNull(var10001);
                K key = this.c(var10001);
                T data = this.getDataHandler().load(var4, key);
                var2.accept(data);
            }
        }
    }

    public void save(@NotNull T data) {
        Objects.requireNonNull(data, "data");
        K key = data.getKey();
        Objects.requireNonNull(key, "");
        MongoIdQueryContainer<K> var2 = this.a(key);
        K var10000 = data.getKey();
        Objects.requireNonNull(var10000, "");
        Document var3 = b(var10000);
        MongoDataProvider var4 = new MongoDataProvider(null, var3);
        this.getDataHandler().save(var4, data);
        this.getCollection().replaceOne(var2, var3, MongoDBDatabase.UPSERT);
    }

    public void delete(@NotNull K key) {
        Objects.requireNonNull(key, "");
        this.getCollection().deleteOne(this.a(key));
    }

    public boolean hasData(@NotNull K key) {
        Objects.requireNonNull(key, "");
        return this.getCollection().find(this.a(key)).first() != null;
    }

    public @NotNull Collection<K> getAllDataKeys() {
        List<K> var1 = new ArrayList<>((int) this.getCollection().estimatedDocumentCount());

        for (Document var3 : this.getCollection().find()) {
            Object var10001 = var3.get(PRIMARY_KEY_ID);
            Objects.requireNonNull(var10001);
            K var4 = this.c(var10001);
            var1.add(var4);
        }

        return var1;
    }

    public void deleteAllData() {
        this.getCollection().drop();
    }

    public @NotNull Collection<T> loadAllData(@Nullable Predicate<K> keyFilter) {
        List<T> var2 = new ArrayList<>(this.b);
        MongoCursor<Document> var3 = this.getCollection().find().iterator();

        while (true) {
            MongoDataProvider var5;
            K var7;
            do {
                if (!var3.hasNext()) {
                    this.b = RangesKt.coerceAtLeast(this.b, var2.size());
                    return var2;
                }

                Document var4 = var3.next();
                Objects.requireNonNull(var4);
                var5 = new MongoDataProvider(null, var4);
                Object var10001 = var4.get(PRIMARY_KEY_ID);
                Objects.requireNonNull(var10001);
                var7 = this.c(var10001);
            } while (keyFilter != null && !keyFilter.test(var7));

            try {
                var2.add(this.getDataHandler().load(var5, var7));
            } catch (Throwable var6) {
                AuspiceLogger.error("Error while loading '" + var7 + "' of type " + this.getDataHandler().getClass().getSimpleName() + " (Skipping):");
                var6.printStackTrace();
            }
        }
    }

    public void save(@NotNull Collection<T> data) {
        Objects.requireNonNull(data, "data");
        if (!data.isEmpty()) {
            List<ReplaceOneModel<Document>> var2 = new ArrayList<>(data.size());

            for (T t : data) {
                K k = t.getKey();
                Objects.requireNonNull(k, "");
                MongoIdQueryContainer<K> var5 = this.a(k);
                Object var10000 = t.getKey();
                Objects.requireNonNull(var10000, "");
                Document var6 = b(var10000);
                MongoDataProvider var7 = new MongoDataProvider(null, var6);
                this.getDataHandler().save(var7, t);
                var2.add(new ReplaceOneModel<>(var5, var6, MongoDBDatabase.UPSERT));
            }

            this.getCollection().bulkWrite(var2, (new BulkWriteOptions()).ordered(false).comment("Save batch data of " + data.size()));
        }
    }

    public void close() {
        MongoDBDatabase.CLIENT.close();
    }

    public static <K, T extends KeyedDataObject<K>> @NotNull KeyedMongoDBDatabase<K, T> withCollection(@NotNull String var1, @NotNull KeyedDataHandler<K, T> var2) {
        return new KeyedMongoDBDatabase<>(var2, MongoDBDatabase.getCollection(var1));
    }
}
