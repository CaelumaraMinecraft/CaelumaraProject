package net.aurika.gradle.dependency.relocation;

import net.aurika.gradle.dependency.relocation.relocator.SimpleRelocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PreRelocationsExtension implements Iterable<SimpleRelocation> {

    public static final String EXTENSION_NAME = "preRelocations";

    private final @NotNull PreRelocationsSettingsExtension settings;
    private final @NotNull List<SimpleRelocation> relocates;
    protected boolean relocated = false;

    public PreRelocationsExtension(@NotNull PreRelocationsSettingsExtension settings) {
        this.settings = settings;
        this.relocates = new ArrayList<>();
    }

    public void relocate(@NotNull String from, @NotNull String to) {
        relocates.add(new SimpleRelocation(processClassPath(from), processClassPath(to)));
    }

    private static @NotNull String processClassPath(@NotNull String path) {
        return path.replace("{}", ".").replace('/', '.').replace('\\', '.');
    }

    public @NotNull List<SimpleRelocation> relocates() {
        return relocates;
    }

    @Override
    public @NotNull Iterator<SimpleRelocation> iterator() {
        return new Itr();
    }

    public @NotNull PreRelocationsSettingsExtension settings() {
        return settings;
    }

    protected class Itr implements Iterator<SimpleRelocation> {

        private final Iterator<SimpleRelocation> it = PreRelocationsExtension.this.relocates.iterator();

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public SimpleRelocation next() {
            return it.next();
        }
    }
}
