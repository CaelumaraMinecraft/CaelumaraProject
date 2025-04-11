package net.aurika.auspice.configs.messages.context;

import net.aurika.auspice.text.context.TextContextImpl;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.common.annotation.Getter;
import net.aurika.common.annotation.Setter;
import net.aurika.util.string.Strings;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageContextImpl extends TextContextImpl implements MessageContext {

  public Boolean usePrefix;
  private @NotNull Diversity diversity = Diversity.globalDefault();

  public MessageContextImpl() {
  }

  @Override
  public boolean ignoreColors() {
    return super.ignoreColors();
  }

  @Override
  public MessageContext ignoreColors(boolean ignoreColors) {
    super.ignoreColors(ignoreColors);
    return this;
  }

  @Getter
  public @Nullable Boolean usePrefix() {
    return this.usePrefix;
  }

  @Setter
  public MessageContextImpl usePrefix(Boolean usePrefix) {
    this.usePrefix = usePrefix;
    return this;
  }

  @Getter
  public @NotNull Diversity diversity() {
    return this.diversity;
  }

  @Setter
  public MessageContextImpl diversity(@NotNull Diversity diversity) {
    Validate.Arg.notNull(diversity, "diversity");
    this.diversity = diversity;
    return this;
  }

  public MessageContextImpl clone() {
    return this.cloneInto(new MessageContextImpl());
  }

  public @NotNull String toString() {
    return this.getClass().getSimpleName() +
        "{" +
        "context=" + primaryTarget() +
        ", ignoreColors=" + ignoreColors() +
        ", prefix=" + usePrefix() +
        ", other=" + secondaryTarget() +
        ", Children=" + Strings.associatedArrayMap(children()) +
        ", placeholders=" + (variables() == null ? "{}" : variables().entrySet().stream().map(
        (entry) -> String.valueOf(entry.getKey()) + '=' + entry.getValue()).toList()) + " }";
  }

}
