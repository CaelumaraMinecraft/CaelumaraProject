package net.aurika.data.database.flatfile.yaml;

import net.aurika.data.handlers.abstraction.DataHandler;
import net.aurika.data.object.DataObject;
import net.aurika.snakeyaml.extension.common.SimpleWriter;
import net.aurika.snakeyaml.extension.nodes.MapNode;
import net.aurika.snakeyaml.extension.nodes.NodeUtils;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.api.LoadSettingsBuilder;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.composer.Composer;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Tag;
import org.snakeyaml.engine.v2.parser.ParserImpl;
import org.snakeyaml.engine.v2.scanner.StreamReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.LinkedList;

public final class YamlDatabase {

    private static final @NotNull Dump DUMP = new Dump(DumpSettings.builder().build());

    private YamlDatabase() {
    }

    public static @NotNull YamlMappingDataProvider load(@NotNull String label, @NotNull BufferedReader reader) {
        Checker.Arg.notNull(label, "label");
        Checker.Arg.notNull(reader, "reader");
        LoadSettingsBuilder builder = LoadSettings.builder();
        builder.setLabel(label);
        LoadSettings loadSettings = builder.build();

        Composer composer = new Composer(loadSettings, new ParserImpl(loadSettings, new StreamReader(loadSettings, reader)));
        var single = composer.getSingleNode();
        if (single.isEmpty()) {
            return new YamlMappingDataProvider(null, new MapNode(NodeUtils.emptyMapping()));    // TODO 验证
        }
        if (!(single.get() instanceof MappingNode)) {
            throw new IllegalStateException("Expected a mapping single document");
        } else {
            return new YamlMappingDataProvider(null, new MapNode((MappingNode) single.get()));
        }
    }

    public static <T extends DataObject> void save(@NotNull T data, @NotNull DataHandler<T> dataHandler, @NotNull BufferedWriter writer) {
        Checker.Arg.notNull(data, "data");
        Checker.Arg.notNull(dataHandler, "dataHandler");
        Checker.Arg.notNull(writer, "writer");
        MappingNode mappingNode = new MappingNode(Tag.MAP, new LinkedList<>(), FlowStyle.BLOCK);
        YamlMappingDataProvider yamlDataProvider = new YamlMappingDataProvider(null, new MapNode(mappingNode));
        dataHandler.save(yamlDataProvider, data);
        DUMP.dumpNode(mappingNode, new SimpleWriter(writer));
    }
}
