package net.aurika.gradle.relocation.relocation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class Relocation {

  private final String pattern;
  private final String relocatedPattern;
  private final String pathPattern;
  private final String relocatedPathPattern;
  private final Set<String> includes;
  private final Set<String> excludes;

  public Relocation(@NotNull String pattern, @NotNull String relocatedPattern) {
    this(pattern, relocatedPattern, Collections.emptyList(), Collections.emptyList());
  }

  public Relocation(@NotNull String pattern, @NotNull String relocatedPattern, Collection<String> includes, Collection<String> excludes) {
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

  private boolean isIncluded(String path) {
    if (this.includes == null) {
      return true;
    } else {
      for (String include : this.includes) {
        if (SelectorUtils.matchPath(include, path, true)) {
          return true;
        }
      }

      return false;
    }
  }

  private boolean isExcluded(String path) {
    if (this.excludes == null) {
      return false;
    } else {
      for (String exclude : this.excludes) {
        if (SelectorUtils.matchPath(exclude, path, true)) {
          return true;
        }
      }

      return false;
    }
  }

  boolean canRelocatePath(@NotNull String path) {
    if (path.endsWith(".class")) {
      path = path.substring(0, path.length() - 6);
    }

    if (this.isIncluded(path) && !this.isExcluded(path)) {
      return path.startsWith(this.pathPattern) || path.startsWith('/' + this.pathPattern);
    } else {
      return false;
    }
  }

  boolean canRelocateClass(@NotNull String clazz) {
    return clazz.indexOf(47) == -1 && this.canRelocatePath(clazz.replace('.', '/'));
  }

  @Contract(pure = true)
  @NotNull String relocatePath(@NotNull String path) {
    return path.replaceFirst(this.pathPattern, this.relocatedPathPattern);
  }

  @Contract(pure = true)
  @NotNull String relocateClass(@NotNull String clazz) {
    return clazz.replaceFirst(this.pattern, this.relocatedPattern);
  }

  private static @NotNull Set<String> normalizePatterns(@NotNull Collection<String> patterns) {
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
