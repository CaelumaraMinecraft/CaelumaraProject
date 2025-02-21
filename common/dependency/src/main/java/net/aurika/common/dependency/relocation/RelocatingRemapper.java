package net.aurika.common.dependency.relocation;

import org.objectweb.asm.commons.Remapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class RelocatingRemapper extends Remapper {
    private static final Pattern CLASS_PATTERN = Pattern.compile("(\\[*)?L(.+);");
    private static final Pattern VERSION_PATTERN = Pattern.compile("^(META-INF/versions/\\d+/)(.*)$");
    private final Collection<Relocation> rules;

    RelocatingRemapper(Collection<Relocation> rules) {
        this.rules = rules;
    }

    public String map(String name) {
        String relocatedName = this.relocate(name, false);
        return relocatedName != null ? relocatedName : super.map(name);
    }

    public Object mapValue(Object object) {
        if (object instanceof String) {
            String relocatedName = this.relocate((String) object, true);
            if (relocatedName != null) {
                return relocatedName;
            }
        }

        return super.mapValue(object);
    }

    private String relocate(String name, boolean isStringValue) {
        String prefix = "";
        String suffix = "";
        Matcher m;
        if (isStringValue) {
            m = CLASS_PATTERN.matcher(name);
            if (m.matches()) {
                prefix = m.group(1) + 'L';
                name = m.group(2);
                suffix = ";";
            }
        }

        m = VERSION_PATTERN.matcher(name);
        if (m.matches()) {
            prefix = m.group(1);
            name = m.group(2);
        }

        Iterator<Relocation> var6 = this.rules.iterator();

        Relocation r;
        do {
            if (!var6.hasNext()) {
                return null;
            }

            r = var6.next();
            if (isStringValue && r.canRelocateClass(name)) {
                return prefix + r.relocateClass(name) + suffix;
            }
        } while (!r.canRelocatePath(name));

        return prefix + r.relocatePath(name) + suffix;
    }

    public String toString() {
        return this.rules.toString();
    }
}
