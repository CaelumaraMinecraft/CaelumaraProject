package net.aurika.config.adapters;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import net.aurika.config.yaml.importers.YamlImporter;
import top.auspice.main.Auspice;

import java.io.File;
import java.io.InputStream;

public class YamlResource extends YamlWithDefaults {
    protected final Plugin plugin;
    protected String resourcePath;

    public YamlResource(Plugin plugin, File file, String resourcePath) {
        super(file);
        this.resourcePath = resourcePath;
        this.plugin = plugin;
    }

    public YamlResource(File var1, String var2) {
        this(Auspice.get(), var1, var2);
    }

    public YamlResource(File var1) {
        this(var1, var1.getName());
    }

    protected InputStream getDefaultsInputStream() {
        return this.resourcePath == null ? null : this.plugin.getResource(this.resourcePath);
    }

    public YamlResource setImporter(YamlImporter importer) {
        super.setImporter(importer);
        return this;
    }

    @NotNull
    public YamlResource importDeclarations() {
        super.importDeclarations();
        return this;
    }

    protected String getDefaultsPath() {
        return this.resourcePath;
    }

    public YamlResource load() {
        super.load();
        return this;
    }

    protected InputStream getSchemaInputStream() {
        return this.plugin.getResource("schemas/" + this.resourcePath);
    }

    public String toString() {
        return "YamlResource{plugin=" + this.plugin + ", resourcePath='" + this.resourcePath + '\'' + ", file=" + super.file + ", validator=" + (super.schema == null ? "NULL" : super.schema.getClass()) + ", importer=" + super.importer + ", loadAnchors=" + super.loadAnchors + '}';
    }
}

