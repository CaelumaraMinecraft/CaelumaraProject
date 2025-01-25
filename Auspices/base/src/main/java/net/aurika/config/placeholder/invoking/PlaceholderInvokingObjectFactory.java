package net.aurika.config.placeholder.invoking;

import org.jetbrains.annotations.NotNull;
import net.aurika.config.functional.invoking.ConfigFunctionalInvokingData;
import top.auspice.configs.texts.placeholders.context.PlaceholderContextBuilder;
import top.auspice.utils.nonnull.NonNullMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public final class PlaceholderInvokingObjectFactory {

    public static final Map<Class<?>, ExtensionInvokable<?>> EXTENSIONS = new NonNullMap<>();

    public static <T> Object getAttribute(T constant, @NotNull String attrName) {
        return getAttribute((Class<? super T>) constant.getClass(), constant, attrName);
    }

    public static <T extends S, S> Object getAttribute(Class<S> superType, @NotNull T constant, @NotNull String attrName) {

        if (constant instanceof PlaceholderAttributeProvider attributableObj) {
            return attributableObj.providePlaceholderAttribute(attrName);
        } else {
            Objects.requireNonNull(superType);
            ExtensionInvokable<S> ext = (ExtensionInvokable<S>) EXTENSIONS.get(superType);
            return ext == null ? null : ext.getAttribute(constant, attrName);
        }
    }

    public static <T> Object processFunctionInvoke(@NotNull T constant, @NotNull String functionName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context) {
        return processFuncInvoke((Class<? super T>) constant.getClass(), constant, functionName, invokeData, context);
    }

    public static <T extends S, S> Object processFuncInvoke(Class<S> superType, @NotNull T constant, @NotNull String functionName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context) {
        Objects.requireNonNull(constant);
        Objects.requireNonNull(functionName);
        if (constant instanceof PlaceholderFunctional functionalObj) {
            return functionalObj.invokePlaceholderFunction(functionName, invokeData, context);
        } else {
            Objects.requireNonNull(superType);
            ExtensionInvokable<S> ext = (ExtensionInvokable<S>) EXTENSIONS.get(superType);
            return ext == null ? null : ext.processFuncInvoke(constant, functionName, invokeData, context);
        }
    }

    public static final class StringExtension extends ExtensionInvokable<String> {
        public static final StringExtension INSTANCE = new StringExtension();

        private StringExtension() {
            super(String.class);
        }

        public Object getAttribute(String str, @NotNull String attrName) {
            return switch (attrName) {
                case "length" -> str.length();
                case "charArray", "chars" -> str.toCharArray();
                default -> null;
            };
        }

        public Object processFuncInvoke(String constant, String funcName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context) {
            return null;
        }

    }

    public static final class HashMapExtension extends ExtensionInvokable<HashMap> {
        public static final HashMapExtension INSTANCE = new HashMapExtension();

        private HashMapExtension() {
            super(HashMap.class);
        }

        public Object getAttribute(HashMap constant, @NotNull String attrName) {
            return switch (attrName) {
                case "" -> null;
                default -> MapExtension.INSTANCE.getAttribute(constant, attrName);
            };
        }

        public Object processFuncInvoke(HashMap constant, String funcName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context) {
            return null;
        }

    }

    public static final class TreeMapExtension extends ExtensionInvokable<TreeMap> {
        public static final MapExtension INSTANCE = new MapExtension();

        private TreeMapExtension() {
            super(TreeMap.class);
        }

        public Object getAttribute(TreeMap constant, @NotNull String attrName) {
            return null;
        }

        public Object processFuncInvoke(TreeMap constant, String funcName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context) {
            return null;
        }

    }

    public static final class MapExtension extends ExtensionInvokable<Map> {
        public static final MapExtension INSTANCE = new MapExtension();

        private MapExtension() {
            super(Map.class);
        }

        public Object getAttribute(Map constant, @NotNull String attrName) {
            return null;
        }

        public Object processFuncInvoke(Map constant, String funcName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context) {
            return null;
        }

    }

    public static abstract class ExtensionInvokable<T> {
        private final Class<T> type;

        protected ExtensionInvokable(Class<T> type) {
            this.type = Objects.requireNonNull(type);
        }

        public final Class<T> getType$core() {
            return type;
        }

        public abstract Object getAttribute(T constant, @NotNull String attrName);

        public abstract Object processFuncInvoke(T constant, String funcName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context);
    }

}
