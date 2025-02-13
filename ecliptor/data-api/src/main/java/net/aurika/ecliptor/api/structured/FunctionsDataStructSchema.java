package net.aurika.ecliptor.api.structured;

import net.aurika.ecliptor.api.structured.scalars.DataScalarType;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class FunctionsDataStructSchema<T extends StructuredDataObject> extends DataStructSchema<T> {

    protected final @NotNull Function<StructuredData, T> structToObject;
    protected final @NotNull Function<String, T> plainToObject;
    protected final @NotNull Function<T, String> objectToPlain;

    @Contract("_, _, _, _, _ -> new")
    public static <T extends StructuredDataObject> @NotNull FunctionsDataStructSchema<T> of(@NotNull Class<T> type,
                                                                                            @NotNull Function<StructuredData, T> fromMap,
                                                                                            @NotNull Function<String, T> fromPlain,
                                                                                            @NotNull Function<T, String> toPlain,
                                                                                            @NotNull Object @NotNull ... template
    ) {
        return new FunctionsDataStructSchema<>(type, arrayToMap(template), fromMap, fromPlain, toPlain);
    }

    protected static LinkedHashMap<String, DataScalarType> arrayToMap(@NotNull Object @NotNull [] keyTypeArray) {
        int length = keyTypeArray.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("keyTypeArray length must be even");
        }
        LinkedHashMap<String, DataScalarType> keyTypeMap = new LinkedHashMap<>();

        for (int i = 0; i < length; i += 2) {
            String key = ((CharSequence) keyTypeArray[i]).toString();
            DataScalarType type = (DataScalarType) keyTypeArray[i + 1];
            Objects.requireNonNull(key, "Key at index " + i + " is null");
            Objects.requireNonNull(type, "Type at index " + i + " is null");
            keyTypeMap.put(key, type);
        }
        return keyTypeMap;
    }

    public FunctionsDataStructSchema(@NotNull Class<T> type,
                                     @NotNull Map<String, DataScalarType> template,
                                     @NotNull Function<StructuredData, T> structToObject,
                                     @NotNull Function<String, T> plainToObject,
                                     @NotNull Function<T, String> objectToPlain
    ) {
        super(type, template);
        Validate.Arg.notNull(structToObject, "structToObject");
        Validate.Arg.notNull(plainToObject, "plainToObject");
        this.structToObject = structToObject;
        this.plainToObject = plainToObject;
        this.objectToPlain = objectToPlain;
    }

    @Override
    public @NotNull T structToObject(@NotNull StructuredData struct) {
        return structToObject.apply(struct);
    }

    @Override
    public @NotNull T plainToObject(@NotNull String plain) {
        return plainToObject.apply(plain);
    }

    @Override
    public @NotNull String objectToPlain(@NotNull T object) {
        return objectToPlain.apply(object);
    }
}
