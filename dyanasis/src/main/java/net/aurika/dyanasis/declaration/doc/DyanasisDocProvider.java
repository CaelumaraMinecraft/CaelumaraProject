package net.aurika.dyanasis.declaration.doc;

import org.jetbrains.annotations.Nullable;

public interface DyanasisDocProvider {
    default @Nullable DyanasisDoc dyanasisDoc() {
        return null;
    }
}
