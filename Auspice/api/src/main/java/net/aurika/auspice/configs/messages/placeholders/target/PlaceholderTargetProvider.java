package net.aurika.auspice.configs.messages.placeholders.target;

import net.aurika.auspice.configs.messages.placeholders.context.VariableProvider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Some placeholders
 */
public interface PlaceholderTargetProvider extends VariableProvider, BasePlaceholderTargetProvider {

  /**
   * Gets targets.
   *
   * @return the targets
   */
  @NotNull Map<String, ?> targets();

  /**
   * Switches primary target and secondary target.
   *
   * @return the {@linkplain PlaceholderTargetProvider} after targets switched
   */
  @Contract("-> this")
  @NotNull PlaceholderTargetProvider switchTargets();

  default @NotNull BasePlaceholderTargetProvider getTargetProviderFor(@Nullable String pointer) {
    if (this.primaryTarget() == null && this.secondaryTarget() == null) {
      return BasePlaceholderTargetProvider.EMPTY;
    } else if (pointer == null) {
      return new ConstantBasePlaceholderTargetProvider(this.primaryTarget(), this.secondaryTarget());
    } else if ("other".equals(pointer)) {
      return new ConstantBasePlaceholderTargetProvider(this.secondaryTarget(), this.primaryTarget());
    } else {
      Object var10000 = this.targets().get(pointer);
      if (var10000 == null) {
        throw new IllegalArgumentException("Unknown pointer: " + pointer);
      } else {
        return new ConstantBasePlaceholderTargetProvider(var10000, null);
      }
    }
  }

//    @Override
//    default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
//        return Stream.concat(
//                BasePlaceholderTargetProvider.super.examinableProperties(),
//                Stream.of(
//                        ExaminableProperty.of("targets", targets())
//                )
//        );
//    }
}
