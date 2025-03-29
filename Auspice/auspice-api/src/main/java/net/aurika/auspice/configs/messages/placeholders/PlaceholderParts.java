package net.aurika.auspice.configs.messages.placeholders;

import net.aurika.util.string.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public final class PlaceholderParts {

  private final @NotNull String full;
  private @Nullable List<String> parts;

  public PlaceholderParts(@NotNull String full) {
    Objects.requireNonNull(full, "full");
    this.full = full;
    if (this.full.isEmpty()) {
      throw new IllegalArgumentException("Placeholder parts cannot be empty: " + this.full);
    }
  }

  /**
   * Gets the full name.
   *
   * @return the full name
   */
  public @NotNull String full() {
    return this.full;
  }

  /**
   * Gets the placeholder parts.
   *
   * @return the parts
   */
  public @NotNull List<String> parts() {
    if (this.parts == null) {
      this.parts = Strings.split(this.full, '_', false);
    }

    return Objects.requireNonNull(this.parts);
  }

  /**
   * Gets the first part of the placeholder name.
   *
   * @return the first part
   */
  public @NotNull String id() {
    return this.parts().getFirst();
  }

  public boolean isTerminal() {
    return this.parts().size() == 1;
  }

  public @NotNull String getParameterFromIndex(int index) {
    return Objects.requireNonNull(String.join("_", this.parts().subList(index, this.parts().size())));
  }

  @NotNull
  public String toString() {
    return "PlaceholderParts(full='" + this.full + "', placeholder=" + this.parts() + ')';
  }

}

