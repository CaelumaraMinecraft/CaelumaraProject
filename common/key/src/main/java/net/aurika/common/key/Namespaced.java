package net.aurika.common.key;

import net.aurika.validate.Validate;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Obsolete
@Deprecated
public interface Namespaced {

  /**
   * Unbox a {@linkplain Namespaced}.
   *
   * @throws IllegalStateException When the return value of {@link Namespaced#namespace()} is invalid.
   */
  @KeyPatterns.Namespace
  static @NotNull String unbox(@NotNull Namespaced namespaced) throws IllegalStateException {
    Validate.Arg.notNull(namespaced, "namespaced");
    // noinspection PatternValidation
    String ns = namespaced.namespace();
    // noinspection ConstantValue
    if (ns == null)
      throw new IllegalStateException("Return value of " + Namespaced.class.getName() + ".namespace() is null");
    if (isValidNamespace(ns)) {
      return ns;
    } else {
      throw new IllegalStateException("Invalid namespace: " + ns);
    }
  }

  static boolean isValidNamespace(@NotNull String namespace) {
    return NamespacedImpl.NAMESPACE_PATTERN.matcher(namespace).matches();
  }

  @KeyPatterns.Namespace
  @NotNull String namespace();

}

@Deprecated
class NamespacedImpl implements Namespaced {

  @Language("RegExp")
  static final String ALLOWED_NAMESPACE = "^[A-Za-z]{3,90}+$";
  static final java.util.regex.Pattern NAMESPACE_PATTERN = java.util.regex.Pattern.compile(ALLOWED_NAMESPACE);

  @KeyPatterns.Namespace
  private final @NotNull String namespace;

  NamespacedImpl(@KeyPatterns.Namespace final @NotNull String namespace) {
    Validate.Arg.notEmpty(namespace, "namespace");
    Validate.Arg.require(
        NAMESPACE_PATTERN.matcher(namespace).matches(),
        "namespace not matches regex: " + ALLOWED_NAMESPACE
    );
    this.namespace = namespace;
  }

  @KeyPatterns.Namespace
  public @NotNull String namespace() {
    return namespace;
  }

}
