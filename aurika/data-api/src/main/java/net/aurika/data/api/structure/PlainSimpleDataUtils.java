package net.aurika.data.api.structure;

import net.aurika.checker.Checker;
import net.aurika.utils.string.CommaDataSplitStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 例如 "overworld, 1, 5, 3", 模板为 "world"->string, "x"->int, "y"->int, "z"->int
 */
public final class PlainSimpleDataUtils {
    public static <T> T serializePlainMapData(@NotNull String plainData, SimpleDataMapObjectTemplate<T> template) {
        Checker.Arg.notNull(plainData, "plainData");
        Checker.Arg.notNull(template, "template");
        CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(plainData, template.size());
        LinkedHashSet<SimpleMappingDataEntry> data = new LinkedHashSet<>();
        for (Map.Entry<String, DataUnitType> entryTemplate : template.templateMap().entrySet()) {
            data.add(nextEntry(entryTemplate.getKey(), entryTemplate.getValue(), splitter));
        }
        return template.toObject(DataUnits.of(data));
    }

    private static SimpleMappingDataEntry nextEntry(@NotNull String key, @NotNull DataUnitType type, @NotNull CommaDataSplitStrategy splitter) {
        Checker.Arg.notNull(key, "key");
        Checker.Arg.notNull(type, "type");
        Checker.Arg.notNull(splitter, "splitter");
        return switch (type) {
            case INT -> SimpleMappingDataEntry.of(key, splitter.nextInt());
            case LONG -> SimpleMappingDataEntry.of(key, splitter.nextLong());
            case FLOAT -> SimpleMappingDataEntry.of(key, splitter.nextFloat());
            case DOUBLE -> SimpleMappingDataEntry.of(key, splitter.nextDouble());
            case BOOLEAN -> SimpleMappingDataEntry.of(key, splitter.nextBoolean());
            case STRING -> SimpleMappingDataEntry.of(key, splitter.nextString());
        };
    }

    public static @NotNull String compressPlainMapData(@NotNull DataUnits toPlainData) {
        Checker.Arg.notNull(toPlainData, "toPlainData");
        StringJoiner joiner = new StringJoiner(", ");
        for (SimpleMappingDataEntry entry : toPlainData.getData()) {
            joiner.add(entry.valueAsString());
        }
        return joiner.toString();
    }
}
