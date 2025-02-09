package net.aurika.text.placeholders.target;

public interface BasePlaceholderTargetProvider {

    BasePlaceholderTargetProvider EMPTY = new AbstractBasePlaceholderTargetProvider(null, null);

    Object getPrimaryTarget();

    Object getSecondaryTarget();
}
