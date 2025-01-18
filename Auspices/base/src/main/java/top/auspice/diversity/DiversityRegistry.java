package top.auspice.diversity;

import top.auspice.key.NSKedRegistry;
import top.auspice.main.Auspice;

public class DiversityRegistry extends NSKedRegistry<Diversity> {
    public static final DiversityRegistry INSTANCE = new DiversityRegistry();

    protected DiversityRegistry() {
        super(Auspice.get(), "DIVERSITY");
    }



}
