package top.auspice.data.database.flatfile.yaml;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.api.*;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.composer.Composer;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Tag;
import org.snakeyaml.engine.v2.parser.ParserImpl;
import org.snakeyaml.engine.v2.scanner.StreamReader;
import top.auspice.config.yaml.snakeyaml.common.SimpleWriter;
import top.auspice.data.object.DataObject;
import top.auspice.data.handlers.abstraction.DataHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.LinkedList;

public final class YamlDatabase {
    @NotNull
    public static final YamlDatabase INSTANCE = new YamlDatabase();
    @NotNull
    private static final Dump a = new Dump(DumpSettings.builder().build());

    private YamlDatabase() {
    }

    @NotNull
    public static YamlMappingDataProvider load(@NotNull String label, @NotNull BufferedReader reader) {
        Intrinsics.checkNotNullParameter(label, "");
        Intrinsics.checkNotNullParameter(reader, "");
        LoadSettingsBuilder builder = LoadSettings.builder();
        builder.setLabel(label);
        LoadSettings settings = builder.build();
        Load load = new Load(settings);


        Composer composer = new Composer(settings, new ParserImpl(settings, new StreamReader(settings, reader)));

        composer

        MappingNode var4 = load.createComposer(reader).getRoot();
        load.construct(var4);
        return new YamlMappingDataProvider(null, var4);
    }

    public static <T extends DataObject.Impl> void save(@NotNull T var0, @NotNull DataHandler<T> var1, @NotNull BufferedWriter var2) {
        Intrinsics.checkNotNullParameter(var0, "");
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        MappingNode var3 = new MappingNode(Tag.MAP, new LinkedList<>(), FlowStyle.BLOCK);
        YamlMappingDataProvider var4 = new YamlMappingDataProvider(null, var3);
        var1.save(var4, var0);
        a.dumpNode(var3, new SimpleWriter(var2));
    }
}
