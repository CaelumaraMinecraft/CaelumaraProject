package top.auspice.config.translator;

import java.util.HashMap;
import java.util.Map;

public final class TranslatorRegistry {

    public static TranslatorRegistry INSTANCE;

    private final Map<Class<?>, Translator<?>> transformers = new HashMap<>();


    public static <T> void register(Translator<T> translator) {
        INSTANCE.transformers.put(translator.getOutputType(), translator);
    }

    public <T> Translator<T> getTransformer(Class<T> type) {
        return (Translator<T>) transformers.get(type);
    }
    public Map<Class<?>, Translator<?>> getTransformers() {
        return transformers;
    }

    static {
        register(Translator.BOOLEAN);
        register(Translator.BYTE);
        register(Translator.SHORT);
        register(Translator.INTEGER);
        register(Translator.LONG);
        register(Translator.FLOAT);
        register(Translator.DOUBLE);

        register(Translator.STRING);

        register(ListTranslator.INTEGER_LIST);
        register(ListTranslator.STRING_LIST);

        register(Translator.MATH);
        register(Translator.MESSAGE);
        register(Translator.CONDITION);

        register(Translator.OFFLINE_PLAYER);
    }

}