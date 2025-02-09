package net.aurika.data.api.structure;

import net.aurika.checker.Checker;
import net.aurika.data.api.structure.entries.MapDataEntry;
import net.aurika.utils.string.CommaDataSplitStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 例如 "overworld, 1, 5, 3", 模板为 "world"->string, "x"->int, "y"->int, "z"->int
 */
public final class PlainSimpleDataUtils {
    public static <T> T serializePlainMapData(@NotNull String plainData, SimpleDataObjectTemplate<T> template) {
        Checker.Arg.notNull(plainData, "plainData");
        Checker.Arg.notNull(template, "template");
        CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(plainData, template.size());
        LinkedHashSet<MapDataEntry> data = new LinkedHashSet<>();
        for (Map.Entry<String, DataMetaType> entryTemplate : template.templateMap().entrySet()) {
            data.add(nextEntry(entryTemplate.getKey(), entryTemplate.getValue(), splitter));
        }
        return template.toObject(SimpleData.of(data));
    }

    private static MapDataEntry nextEntry(@NotNull String key, @NotNull DataMetaType type, @NotNull CommaDataSplitStrategy splitter) {
        Checker.Arg.notNull(key, "key");
        Checker.Arg.notNull(type, "type");
        Checker.Arg.notNull(splitter, "splitter");
        return switch (type) {
            case INT -> MapDataEntry.of(key, splitter.nextInt());
            case LONG -> MapDataEntry.of(key, splitter.nextLong());
            case FLOAT -> MapDataEntry.of(key, splitter.nextFloat());
            case DOUBLE -> MapDataEntry.of(key, splitter.nextDouble());
            case BOOLEAN -> MapDataEntry.of(key, splitter.nextBoolean());
            case STRING -> MapDataEntry.of(key, splitter.nextString());
        };
    }

    public static @NotNull String compressPlainMapData(@NotNull SimpleData toPlainData) {
        Checker.Arg.notNull(toPlainData, "toPlainData");
        StringJoiner joiner = new StringJoiner(", ");
        for (MapDataEntry entry : toPlainData.getData()) {
            joiner.add(entry.valueAsString());
        }
        return joiner.toString();
    }
}
