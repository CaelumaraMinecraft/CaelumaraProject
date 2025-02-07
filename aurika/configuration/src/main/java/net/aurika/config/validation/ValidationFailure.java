package net.aurika.config.validation;

import net.aurika.config.sections.ConfigSection;

public class ValidationFailure {
    private final Severity severity;
    private final ConfigSection section;
    private String message;

    public ValidationFailure(Severity severity, ConfigSection section) {
        this.severity = severity;
        this.section = section;
    }

    public ValidationFailure(Severity severity, ConfigSection section, String message) {
        this.severity = severity;
        this.section = section;
        this.message = message;
    }

    public Severity getSeverity() {
        return this.severity;
    }

    public ConfigSection getSection() {
        return section;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum Severity {
        ERROR,
        WARNING

    }
}
