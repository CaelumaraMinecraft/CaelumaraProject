package top.auspice.configs.texts.placeholders.target;

public interface BasePlaceholderTargetProvider {

    BasePlaceholderTargetProvider EMPTY = new AbstractBasePlaceholderTargetProvider(null, null);

    Object getPrimaryTarget();

    Object getSecondaryTarget();
}
