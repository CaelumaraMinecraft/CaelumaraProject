package net.aurika.auspice.configs.messages.placeholders.target;

import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface BasePlaceholderTargetProvider/* extends Examinable*/ {

    /**
     * A {@linkplain BasePlaceholderTargetProvider} always provides null target.
     */
    BasePlaceholderTargetProvider EMPTY = new BasePlaceholderTargetProvider() {

        @Override
        public Object primaryTarget() {
            return null;
        }

        @Override
        public Object secondaryTarget() {
            return null;
        }
    };

    /**
     * Gets the primary target for a placeholder.
     * @return the primary target
     */
    Object primaryTarget();

    /**
     * Gets the secondary target for a placeholder.
     * @return the secondary target
     */
    Object secondaryTarget();

//    @Override
//    default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
//        return Stream.of(
//                ExaminableProperty.of("primaryTarget", primaryTarget()),
//                ExaminableProperty.of("secondaryTarget", secondaryTarget())
//        );
//    }
}
