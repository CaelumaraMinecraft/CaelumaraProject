package net.aurika.config.placeholders.target;

public interface BasePlaceholderTargetProvider {

    BasePlaceholderTargetProvider EMPTY = new AbstractBasePlaceholderTargetProvider(null, null);

    Object getPrimaryTarget();

    Object getSecondaryTarget();
}
