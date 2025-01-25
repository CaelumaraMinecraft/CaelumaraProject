package net.aurika.config.path;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import net.aurika.abstraction.BuildableObject;
import net.aurika.utils.Checker;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class ConfigEntryBuilder implements BuildableObject.Builder {
    protected LinkedList<String> path;

    public ConfigEntryBuilder() {
    }

    public ConfigEntryBuilder(ConfigEntry initialization) {
        this(initialization.getPath());
    }

    public ConfigEntryBuilder(@NotNull String @NotNull [] initialization) {
        Checker.Argument.checkNotNullArray(initialization, "initialization");
        LinkedList<@NotNull String> path = new LinkedList<>();
        path.addAll(Arrays.asList(initialization));
        this.path = path;
    }

    public ConfigEntryBuilder(LinkedList<@NotNull String> initialization) {
        this.path = initialization;
        if (initialization != null) {
            for (String s : initialization) {
                if (s == null) {
                    throw new IllegalArgumentException("Initialization path contains null section");
                }
            }
        }
    }

    @Contract("_ -> this")
    public ConfigEntryBuilder append(@NotNull String section) {
        Checker.Argument.checkNotNull(section, "section");
        this.path.add(section);
        return this;
    }

    @Contract("_ -> this")
    public ConfigEntryBuilder append(@NotNull String @NotNull [] sections) {
        Checker.Argument.checkNotNullArray(sections, "sections");
        Collections.addAll(this.path, sections);
        return this;
    }

    public @NotNull ConfigEntry build() {
        if (this.path == null) {
            return ConfigEntry.empty();
        }
        LinkedList<String> strings = this.path;
        @NotNull String @NotNull [] path = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            if (s == null) {
                throw new IllegalArgumentException("path contains null section");
            }
            path[i] = s;
        }
        return new ConfigEntry(path);
    }
}
