package net.aurika.data.database.flatfile.yaml;

import net.aurika.config.yaml.snakeyaml.common.SimpleWriter;
import net.aurika.data.handlers.abstraction.DataHandler;
import net.aurika.data.object.DataObject;
import net.aurika.snakeyaml.extension.nodes.MapNode;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.api.*;
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

    @NotNull
    public static YamlMappingDataProvider load(@NotNull String label, @NotNull BufferedReader reader) {
        Checker.Argument.checkNotNull(label, "label");
        Checker.Argument.checkNotNull(reader, "reader");
        LoadSettingsBuilder builder = LoadSettings.builder();
        builder.setLabel(label);
        LoadSettings loadSettings = builder.build();
        Load load = new Load(loadSettings);


        Composer composer = new Composer(loadSettings, new ParserImpl(loadSettings, new StreamReader(loadSettings, reader)));

        composer

        MappingNode var4 = load.createComposer(reader).getRoot();
        load.construct(var4);
        return new YamlMappingDataProvider(null, var4);
    }

    public static <T extends DataObject> void save(@NotNull T data, @NotNull DataHandler<T> dataHandler, @NotNull BufferedWriter writer) {
        Checker.Argument.checkNotNull(data, "data");
        Checker.Argument.checkNotNull(dataHandler, "dataHandler");
        Checker.Argument.checkNotNull(writer, "writer");
        MappingNode mappingNode = new MappingNode(Tag.MAP, new LinkedList<>(), FlowStyle.BLOCK);
        YamlMappingDataProvider yamlDataProvider = new YamlMappingDataProvider(null, new MapNode(mappingNode));
        dataHandler.save(yamlDataProvider, data);
        DUMP.dumpNode(mappingNode, new SimpleWriter(writer));
    }
}
