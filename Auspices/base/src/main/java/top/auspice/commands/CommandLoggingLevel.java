package top.auspice.commands;

public enum CommandLoggingLevel {
    ALL,
    INFO,
    ERRORS,
    NONE;

    public boolean atLeast(CommandLoggingLevel otherLvl) {
        return this.ordinal() <= otherLvl.ordinal();
    }
}