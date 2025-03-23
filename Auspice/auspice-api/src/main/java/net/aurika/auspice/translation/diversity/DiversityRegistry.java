package net.aurika.auspice.translation.diversity;

import net.aurika.common.key.registry.AbstractKeyedRegistry;

public final class DiversityRegistry extends AbstractKeyedRegistry<Diversity> {
    public static final DiversityRegistry INSTANCE = new DiversityRegistry();

    private DiversityRegistry() {
        super();
    }
}
