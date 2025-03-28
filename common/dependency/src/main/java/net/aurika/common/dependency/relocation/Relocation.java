package net.aurika.common.dependency.relocation;

import java.util.*;

public final class Relocation {

  private final String pattern;
  private final String relocatedPattern;
  private final String pathPattern;
  private final String relocatedPathPattern;
  private final Set<String> includes;
  private final Set<String> excludes;

  public Relocation(String pattern, String relocatedPattern, Collection<String> includes, Collection<String> excludes) {
    this.pattern = pattern.replace('/', '.');
    this.pathPattern = pattern.replace('.', '/');
    this.relocatedPattern = relocatedPattern.replace('/', '.');
    this.relocatedPathPattern = relocatedPattern.replace('.', '/');
    if (includes != null && !includes.isEmpty()) {
      this.includes = normalizePatterns(includes);
      this.includes.addAll(includes);
    } else {
      this.includes = null;
    }

    if (excludes != null && !excludes.isEmpty()) {
      this.excludes = normalizePatterns(excludes);
      this.excludes.addAll(excludes);
    } else {
      this.excludes = null;
    }
  }

  public Relocation(String pattern, String relocatedPattern) {
    this(pattern, relocatedPattern, Collections.emptyList(), Collections.emptyList());
  }

  private boolean isIncluded(String path) {
    if (this.includes != null) {
      Iterator<String> var2 = this.includes.iterator();

      String include;
      do {
        if (!var2.hasNext()) {
          return false;
        }

        include = var2.next();
      } while (!SelectorUtils.matchPath(include, path, true));
    }
    return true;
  }

  private boolean isExcluded(String path) {
    if (this.excludes == null) {
      return false;
    } else {
      Iterator<String> var2 = this.excludes.iterator();

      String exclude;
      do {
        if (!var2.hasNext()) {
          return false;
        }

        exclude = var2.next();
      } while (!SelectorUtils.matchPath(exclude, path, true));

      return true;
    }
  }

  boolean canRelocatePath(String path) {
    if (path.endsWith(".class")) {
      path = path.substring(0, path.length() - 6);
    }

    if (this.isIncluded(path) && !this.isExcluded(path)) {
      return path.startsWith(this.pathPattern) || path.startsWith('/' + this.pathPattern);
    } else {
      return false;
    }
  }

  boolean canRelocateClass(String clazz) {
    return clazz.indexOf(47) == -1 && this.canRelocatePath(clazz.replace('.', '/'));
  }

  String relocatePath(String path) {
    return path.replaceFirst(this.pathPattern, this.relocatedPathPattern);
  }

  String relocateClass(String clazz) {
    return clazz.replaceFirst(this.pattern, this.relocatedPattern);
  }

  private static Set<String> normalizePatterns(Collection<String> patterns) {
    Set<String> normalized = new LinkedHashSet<>();

    for (String pattern : patterns) {
      String classPattern = pattern.replace('.', '/');
      normalized.add(classPattern);
      if (classPattern.endsWith("/*")) {
        String packagePattern = classPattern.substring(0, classPattern.lastIndexOf(47));
        normalized.add(packagePattern);
      }
    }

    return normalized;
  }

}
