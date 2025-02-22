package net.aurika.config.adapters;

import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.exceptions.ComposerException;
import org.snakeyaml.engine.v2.exceptions.ParserException;
import org.snakeyaml.engine.v2.exceptions.ScannerException;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Tag;
import net.aurika.config.accessor.YamlClearlyConfigAccessor;
import net.aurika.config.profile.managers.ConfigManager;
import net.aurika.config.sections.YamlNodeSection;
import net.aurika.config.yaml.importers.YamlImporter;
import net.aurika.common.snakeyaml.common.SimpleWriter;
import net.aurika.common.snakeyaml.validation.CustomNodeValidators;
import net.aurika.common.snakeyaml.validation.NodeValidator;
import net.aurika.common.snakeyaml.validation.ValidationFailure;
import net.aurika.common.snakeyaml.validation.Validator;
import net.aurika.auspice.utils.AuspiceLogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class YamlWithDefaults implements YamlContainer {
    @Nullable
    protected final File file;
    protected YamlNodeSection config;
    protected YamlNodeSection defaults;
    protected NodeValidator schema;
    protected YamlImporter importer;
    protected boolean loadAnchors = true;

    protected YamlWithDefaults(@Nullable File file) {
        this.file = file;
    }

    public List<ValidationFailure> validate() {
        Objects.requireNonNull(this.schema, "Cannot validate config with no validator attached: " + this.file.getPath());
        Objects.requireNonNull(this.config, "Cannot validate config that isn't loaded yet: " + this.file.getPath());
        return Validator.validate(this.config.getRootNode(), this.schema, CustomNodeValidators.getValidators());
    }

    protected static void transferTo(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] var2 = new byte[8192];

        int var3;
        while ((var3 = inputStream.read(var2)) >= 0) {
            outputStream.write(var2, 0, var3);
        }

    }

    public YamlWithDefaults setLoadAnchors(boolean loadAnchors) {
        this.loadAnchors = loadAnchors;
        return this;
    }

    public void createFile() {
        Objects.requireNonNull(this.file, "No file path specified to generate the config");
        if (!this.file.exists()) {
            this.saveDefaultConfig();
        }

    }

    public YamlContainer setImporter(YamlImporter importer) {
        this.importer = Objects.requireNonNull(importer);
        return this;
    }

    public YamlImporter getImporter() {
        return this.importer;
    }

    public final YamlNodeSection getConfig() {
        return this.config;
    }

    public final YamlClearlyConfigAccessor accessor() {
        return new YamlClearlyConfigAccessor(this.config, this.defaults);
    }

    public void saveConfig() {
        Objects.requireNonNull(this.file, "No file path specified to save the config");
        DumpSettings dumpSettings = DumpSettings.builder().build();
        Dump var1 = new Dump(dumpSettings);
        ConfigManager.beforeWrite(this);

        try {
            BufferedWriter var2 = Files.newBufferedWriter(this.file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            try {
                var1.dumpNode(this.config.getRootNode(), new SimpleWriter(var2));
            } catch (Throwable var4) {
                if (var2 != null) {
                    try {
                        var2.close();
                    } catch (Throwable var3) {
                        var4.addSuppressed(var3);
                    }
                }

                throw var4;
            }

            if (var2 == null) {
                return;
            }

            var2.close();
        } catch (IOException var5) {
            AuspiceLogger.error("Error while attempting to save configuration file " + this.file.getName() + ": ");
            var5.printStackTrace();
        }

    }

    public final @Nullable File getFile() {
        return this.file;
    }

    protected abstract InputStream getDefaultsInputStream();

    public boolean defaultExists() {
        return this.getDefaultsInputStream() != null;
    }

    protected abstract String getDefaultsPath();

    protected static InputStream inputStreamOf(File var0) {
        try {
            return new FileInputStream(var0);
        } catch (FileNotFoundException var1) {
            return null;
        }
    }

    public boolean saveDefaultConfig() {
        Objects.requireNonNull(this.file, "No file path specified to save the default config");
        InputStream var1;
        if ((var1 = this.getDefaultsInputStream()) == null) {
            return false;
        } else {
            File var2 = this.file.getParentFile();
            if (!var2.exists()) {
                var2.mkdirs();
            } else if (this.file.exists()) {
                return false;
            }

            ConfigManager.beforeWrite(this);
            boolean var11 = false;

            label137:
            {
                try {
                    var11 = true;
                    OutputStream var19 = Files.newOutputStream(this.file.toPath());

                    try {
                        transferTo(var1, var19);
                    } catch (Throwable var16) {
                        if (var19 != null) {
                            try {
                                var19.close();
                            } catch (Throwable var14) {
                                var16.addSuppressed(var14);
                            }
                        }

                        throw var16;
                    }

                    if (var19 != null) {
                        var19.close();
                        var11 = false;
                    } else {
                        var11 = false;
                    }
                    break label137;
                } catch (IOException var17) {
                    var17.printStackTrace();
                    var11 = false;
                } finally {
                    if (var11) {
                        try {
                            var1.close();
                        } catch (IOException var12) {
                            var12.printStackTrace();
                        }

                    }
                }

                try {
                    var1.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }

                return false;
            }

            try {
                var1.close();
            } catch (IOException var15) {
                var15.printStackTrace();
            }

            return true;
        }
    }

    protected abstract InputStream getSchemaInputStream();

    protected void loadSchema() {
        if (this.defaults != null) {
            InputStream var1 = this.getSchemaInputStream();
            if (var1 != null) {
                MappingNode var2 = YamlContainer.parse((new YamlParseContext()).named(this.file.getName()).stream(var1));
                this.schema = Validator.parseSchema(var2);

                try {
                    var1.close();
                } catch (IOException var3) {
                    throw new RuntimeException(var3);
                }
            }
        }
    }

    public void setSchema(NodeValidator var1) {
        this.schema = var1;
    }

    public YamlWithDefaults load() {
        this.loadDefaults();
        this.loadSchema();
        this.reload();
        return this;
    }

    protected void loadDefaults() {
        try {
            InputStream var1 = this.getDefaultsInputStream();

            try {
                if (var1 != null) {
                    try {
                        String var2 = this.file != null && this.file.exists() ? this.file.getName() : this.toString();
                        this.defaults = YamlNodeSection.root(YamlContainer.parse((new YamlParseContext()).named(var2).shouldLoadAliases(this.loadAnchors).stream(var1)));
                    } catch (ParserException | ComposerException | UnsupportedOperationException |
                             ScannerException var4) {
                        AuspiceLogger.error("Failed to load defaults for config '" + this.file.getAbsolutePath() + "' from '" + this.getDefaultsPath() + "':");
                        var4.printStackTrace();
                    }
                } else if (this.file == null) {
                    throw new IllegalStateException("Internal config not found: " + this);
                }
            } catch (Throwable var5) {
                if (var1 != null) {
                    try {
                        var1.close();
                    } catch (Throwable var3) {
                        var5.addSuppressed(var3);
                    }
                }

                throw var5;
            }

            if (var1 != null) {
                var1.close();
            }
        } catch (IOException var6) {
            throw new RuntimeException(var6);
        }
    }

    public void reload() {
        if (this.file == null) {
            this.config = this.defaults;
        } else {
            if (this.file.exists()) {
                try {
                    InputStream var1 = inputStreamOf(this.file);
                    this.config = YamlNodeSection.root(YamlContainer.parse((new YamlParseContext()).named(this.file.getName()).shouldLoadAliases(this.loadAnchors).stream(var1)));
                    var1.close();
                } catch (ParserException | ComposerException | UnsupportedOperationException |
                         ScannerException parseExc) {
                    this.config = this.defaults;
                    AuspiceLogger.error("Invalid config when loading '" + this.file.getAbsolutePath() + "':");
                    parseExc.printStackTrace();
                } catch (IOException IOExc) {
                    this.config = this.defaults;
                    AuspiceLogger.error("Error when loading config '" + this.file.getAbsolutePath() + "':");
                    throw new RuntimeException(IOExc);
                }
            } else {   //对应文件不存在
                this.createFile();
                this.config = this.defaults;
            }

            this.importDeclarations();
        }
    }

    public YamlWithDefaults createEmptyConfigIfNull() {
        if (this.config == null) {
            this.createFile();
            this.config = YamlNodeSection.root(new MappingNode(Tag.MAP, new ArrayList<>(), FlowStyle.BLOCK));
        }

        return this;
    }

    public boolean isDefault() {
        return this.config == this.defaults;
    }

    public final YamlNodeSection getDefaults() {
        return this.defaults;
    }

    public void update() {
        if (!this.isDefault()) {
            if (this.defaults == null) {
                throw new IllegalStateException("The config " + this.file.getName() + " cannot be updated because there's no default config for it");
            } else {
                ConfigManager.beforeWrite(this);

                UpdateResult var1;


                try {
                    var1 = Updater.updateConfig(this.config.getRootNode(), this.defaults.getRootNode().clone(), this.schema, this.file.toPath(), new Dump(new DumpSettings()));
                } catch (Throwable var7) {
                    AuspiceLogger.error("An error occurred while attempting to update " + this.file.getAbsolutePath() + ": " + var7.getMessage() + (var7.getMessage().contains("another process") ? ". This is probably caused by your text editor" : ""));
                    return;
                }

                Iterator var8 = var1.getChanges().iterator();

                while (var8.hasNext()) {
                    UpdateResult.Change var2 = (UpdateResult.Change) var8.next();
                    StringBuilder var3 = new StringBuilder();
                    int var4 = var2.getPath().size();
                    int var5 = 0;
                    Iterator<String> var9 = var2.getPath().iterator();

                    while (var9.hasNext()) {
                        String var6 = (String) var9.next();
                        ++var5;
                        var3.append(var6);
                        if (var5 != var4) {
                            var3.append(" -> ");
                        }
                    }

                    AuspiceLogger.warn("Added missing config option to " + this.file.getName() + ": " + var3);
                }

                this.load();
            }
        }
    }

}
