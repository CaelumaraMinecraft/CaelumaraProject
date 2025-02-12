package net.aurika.data.database.mongo;

import com.mongodb.client.MongoCollection;
import net.aurika.checker.Checker;
import net.aurika.data.api.DataObject;
import net.aurika.data.handler.SingularDataHandler;
import net.aurika.data.database.base.SingularDatabase;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SingularMongoDBDatabase<T extends DataObject> extends MongoDBDatabase<T> implements SingularDatabase<T> {

    private final @NotNull SingularDataHandler<T> dataHandler;

    private SingularMongoDBDatabase(@NotNull SingularDataHandler<T> dataHandler, MongoCollection<Document> var2) {
        super(var2);
        this.dataHandler = dataHandler;
    }

    protected @NotNull SingularDataHandler<T> getDataHandler() {
        return this.dataHandler;
    }

    public void deleteAllData() {
        this.getCollection().deleteOne(new SingularMongoIdQueryContainer());
    }

    public void save(@NotNull T data) {
        Checker.Arg.notNull(data, "data");
        Document var2 = new Document(PRIMARY_KEY_ID, null);
        MongoDataProvider var3 = new MongoDataProvider(null, var2);
        this.getDataHandler().save(var3, data);
        this.getCollection().replaceOne(new SingularMongoIdQueryContainer(), var2, MongoDBDatabase.UPSERT);
    }

    public @Nullable T load() {
        Document document = this.getCollection().find(new SingularMongoIdQueryContainer()).first();
        if (document == null) {
            return null;
        } else {
            MongoDataProvider var2 = new MongoDataProvider(null, document);
            return this.getDataHandler().load(var2);
        }
    }

    public boolean hasData() {
        return getCollection().find(new SingularMongoIdQueryContainer()).first() != null;
    }

    public static <T extends DataObject> @NotNull SingularMongoDBDatabase<T> withCollection(@NotNull String var1, @NotNull SingularDataHandler<T> var2) {
        return new SingularMongoDBDatabase<>(var2, MongoDBDatabase.getCollection(var1));
    }
}
