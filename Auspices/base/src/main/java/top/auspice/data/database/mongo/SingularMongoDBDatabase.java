package top.auspice.data.database.mongo;

import com.mongodb.client.MongoCollection;
import kotlin.jvm.internal.Intrinsics;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.base.AuspiceObject;
import top.auspice.data.database.base.SingularKingdomsDatabase;
import top.auspice.data.handlers.abstraction.SingularDataHandler;

public class SingularMongoDBDatabase<T extends AuspiceObject> extends MongoDBDatabase<T> implements SingularKingdomsDatabase<T> {
    @NotNull
    private final SingularDataHandler<T> a;

    private SingularMongoDBDatabase(SingularDataHandler<T> var1, MongoCollection<Document> var2) {
        super(var2);
        this.a = var1;
    }

    @NotNull
    protected SingularDataHandler<T> getDataHandler() {
        return this.a;
    }

    public void deleteAllData() {
        this.getCollection().deleteOne((Bson) (new SingularMongoIdQueryContainer()));
    }

    public void save(@NotNull T var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        Document var2 = new Document("_id", null);
        MongoDataProvider var3 = new MongoDataProvider(null, var2);
        this.getDataHandler().save(var3, var1);
        this.getCollection().replaceOne((Bson) (new SingularMongoIdQueryContainer()), var2, MongoDBDatabase.Companion.getUPSERT$core());
    }

    @Nullable
    public T load() {
        Document var10000 = this.getCollection().find((Bson) (new SingularMongoIdQueryContainer())).first();
        if (var10000 == null) {
            return null;
        } else {
            Document var1 = var10000;
            MongoDataProvider var2 = new MongoDataProvider(null, var1);
            return this.getDataHandler().load(var2);
        }
    }

    public boolean hasData() {
        return this.getCollection().find((Bson) (new SingularMongoIdQueryContainer())).first() != null;
    }

    @NotNull
    public static <T extends AuspiceObject> SingularMongoDBDatabase<T> withCollection(@NotNull String var1, @NotNull SingularDataHandler<T> var2) {
        return new SingularMongoDBDatabase<>(var2, MongoDBDatabase.getCollection(var1));
    }

}
