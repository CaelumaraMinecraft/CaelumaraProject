package net.aurika.config.adapters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.exceptions.ScannerException;
import net.aurika.config.accessor.YamlClearlyConfigAccessor;
import net.aurika.config.profile.managers.ConfigManager;
import net.aurika.config.sections.YamlConfigSection;
import net.aurika.config.yaml.importers.YamlImporter;
import net.aurika.config.yaml.snakeyaml.common.SimpleWriter;
import top.auspice.utils.AuspiceLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

@SuppressWarnings("unused")
public class YamlFile implements YamlContainer {
    protected @NonNull File file;
    protected YamlConfigSection config;

    private YamlImporter importer;

    public YamlFile(File var1) {
        this.file = Objects.requireNonNull(var1);
    }

    public YamlConfigSection getConfig() {
        return this.config;
    }

    public YamlClearlyConfigAccessor accessor() {
        return new YamlClearlyConfigAccessor(this.config, this.config);
    }

    public void saveConfig() {
        DumpSettings dumpSettings = DumpSettings.builder().build();
        Dump dump = new Dump(dumpSettings);

        ConfigManager.beforeWrite(this);

        try {
            BufferedWriter var2 = Files.newBufferedWriter(this.file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            try {
                dump.dumpNode(this.config.getRoot(), new SimpleWriter(var2));
            } catch (Throwable var4) {
                //noinspection ConstantValue
                if (var2 != null) {
                    try {
                        var2.close();
                    } catch (Throwable var3) {
                        var4.addSuppressed(var3);
                    }
                }

                throw var4;
            }

            //noinspection ConstantValue
            if (var2 == null) {
                return;
            }

            var2.close();
        } catch (IOException var5) {
            AuspiceLogger.error("Error while attempting to save configuration file " + this.file.getName() + ": ");
            var5.printStackTrace();
        }

    }

    @NotNull
    public File getFile() {
        return this.file;
    }

    public YamlFile setImporter(YamlImporter importer) {
        this.importer = importer;
        return this;
    }

    public YamlImporter getImporter() {
        return this.importer;
    }

    public YamlFile load() {
        this.reload();
        return this;
    }

    public void reload() {
        if (this.file.exists()) {
            try {
                try {
                    FileInputStream var1 = new FileInputStream(this.file);

                    try {
                        this.config = YamlConfigSection.root(YamlContainer.parse((new YamlParseContext()).named(this.file.getName()).stream(var1)));
                    } catch (Throwable var4) {
                        try {
                            var1.close();
                        } catch (Throwable var3) {
                            var4.addSuppressed(var3);
                        }

                        throw var4;
                    }

                    var1.close();
                } catch (IOException ioExc) {
                    throw new AssertionError(ioExc);
                }
            } catch (ScannerException var6) {
                this.config = YamlConfigSection.empty();
                AuspiceLogger.error("Failed to load config '" + this.file.getAbsolutePath() + "':");
                var6.printStackTrace();
            }
        } else {
            this.config = YamlConfigSection.empty();
        }

        this.importDeclarations();
    }
}