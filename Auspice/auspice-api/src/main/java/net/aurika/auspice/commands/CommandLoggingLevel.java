package net.aurika.auspice.commands;

public enum CommandLoggingLevel {
    ALL,
    INFO,
    ERRORS,
    NONE;

    public boolean atLeast(CommandLoggingLevel otherLvl) {
        return this.ordinal() <= otherLvl.ordinal();
    }
}