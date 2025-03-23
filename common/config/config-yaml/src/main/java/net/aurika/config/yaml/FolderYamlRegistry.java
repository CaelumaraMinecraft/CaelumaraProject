package net.aurika.config.yaml;

import net.aurika.config.adapters.YamlContainer;
import net.aurika.config.adapters.YamlFile;
import net.aurika.config.adapters.YamlResource;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class FolderYamlRegistry extends FolderRegistry {
    private final String subFolder;
    private final BiConsumer<String, YamlContainer> b;
    private final Map<String, YamlContainer> c = new HashMap<>();
    private final Map<String, YamlContainer> d = new HashMap<>();

    public FolderYamlRegistry(String folder, String other, BiConsumer<String, YamlContainer> var3) {
        super(folder, Auspice.get().getDataFolder().toPath().resolve(other));
        this.b = var3;
        this.subFolder = other;
    }

    protected Pair<String, URI> getDefaultsURI() {
        URI var1;
        try {
            var1 = Auspice.class.getResource("/" + this.subFolder).toURI();
        } catch (URISyntaxException var2) {
            throw new RuntimeException(var2);
        }

        return Pair.of(this.subFolder, var1);
    }

    protected void handle(FolderRegistry.@NotNull Entry var1) {
        String var2 = this.subFolder + '/' + var1.getRelativeName().replace('\\', '/');
        String var3 = var1.getName();
        YamlContainer var4;
        if (var1.isDefault()) {
            var4 = this.a(new File(Auspice.get().getDataFolder(), var2), var2);
            this.d.put(var3, var4);
            this.c.put(var3, var4);
        } else {
            var4 = this.a(var1.getPath().toFile(), var2);
            this.d.put(var3, var4);
            this.b.accept(var3, var4);
        }
    }

    private YamlContainer a(File var1, String var2) {
        return super.useDefaults ? new YamlResource(var1, var2) : new YamlFile(var1);
    }

    private void a(boolean var1) {
        this.visitDefaults();

        for (Map.Entry<String, YamlContainer> stringYamlContainerEntry : this.c.entrySet()) {

            try {
                String var4 = stringYamlContainerEntry.getKey();
                if (var1 || !this.d.containsKey(var4)) {
                    this.d.put(var4, stringYamlContainerEntry.getValue());
                    this.b.accept(var4, stringYamlContainerEntry.getValue());
                }
            } catch (Throwable var5) {
                throw new IllegalStateException("Error while registering default " + super.displayName + " '" + stringYamlContainerEntry.getKey() + "' " + stringYamlContainerEntry.getValue().getFile(), var5);
            }
        }
    }

    public void register() {
        if (!Files.exists(super.folder)) {
            this.a(true);
        } else {
            this.visitPresent();
            if (super.useDefaults) {
                this.a(false);
            }
        }
    }
}

