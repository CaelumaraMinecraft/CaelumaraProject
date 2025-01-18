package top.auspice.config.adapters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.common.Anchor;

import java.io.InputStream;
import java.util.Objects;
import java.util.function.Function;

public final class YamlParseContext {
    private String name;
    private InputStream stream;
    @Nullable
    private Function<String, Anchor> anchorProcessor;
    private boolean shouldLoadAlias = true;

    public YamlParseContext() {
    }

    @NotNull
    public String getName() {
        return Objects.requireNonNull(this.name, "Property \"name\" is not init.");
    }

    @NotNull
    public InputStream getStream() {
        return Objects.requireNonNull(this.stream, "Property \"stream\" is not init.");
    }

    @Nullable
    public Function<String, Anchor> getAnchorProcessor() {
        return this.anchorProcessor;
    }

    public @NotNull YamlParseContext named(@NotNull String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }

    @NotNull
    public YamlParseContext stream(@NotNull InputStream stream) {
        Objects.requireNonNull(stream);
        this.stream = stream;
        return this;
    }

    @NotNull
    public YamlParseContext processAnchors(@NotNull Function<String, Anchor> anchorProcessor) {
        Objects.requireNonNull(anchorProcessor);
        this.anchorProcessor = anchorProcessor;
        return this;
    }

    public boolean shouldLoadAliases() {
        return this.shouldLoadAlias;
    }

    @NotNull
    public YamlParseContext shouldLoadAliases(boolean set) {
        this.shouldLoadAlias = set;
        return this;
    }
}
