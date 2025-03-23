package net.aurika.config.functional.invoking;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

public abstract class ConfigFunctionalInvokingData {

    protected final String originalString;

    protected ConfigFunctionalInvokingData(String originalString) {
        this.originalString = Objects.requireNonNull(originalString);
    }

    @Contract(value = "_ -> new", pure = true)
    public static OriginalStringConfigFunctionalInvokingData toOriginalStringData(String originalString) {
        return new OriginalStringConfigFunctionalInvokingData(originalString);
    }



}
