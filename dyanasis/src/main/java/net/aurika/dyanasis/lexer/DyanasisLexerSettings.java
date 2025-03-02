package net.aurika.dyanasis.lexer;

import org.jetbrains.annotations.NotNull;

public class DyanasisLexerSettings {

    public static final DyanasisLexerSettings DEFAULT = DyanasisLexerSettings.builder().build();

    public static DyanasisLexerSettingsBuilder builder() {
        return new DyanasisLexerSettingsBuilder();
    }

    protected final @NotNull Idents idents;

    public @NotNull Idents idents() {
        return idents;
    }

    public DyanasisLexerSettings(@NotNull Idents idents) {
        this.idents = idents;
    }

    public class Idents {

        protected @NotNull String self;
        protected @NotNull String invoke;        // invoke function / property
        protected @NotNull String transfer;      // transfer to a function
        protected @NotNull String namespaceAnalysis;
        protected @NotNull String functionInputLeft;
        protected @NotNull String functionInputRight;

        protected @NotNull String nil;

        protected final @NotNull String stringLeft;
        protected final @NotNull String stringRight;

        protected final @NotNull String rawInvokeLeft;
        protected final @NotNull String rawInvokeRight;

        protected final @NotNull String mapLeft;
        protected final @NotNull String mapRight;
        protected final @NotNull String mapKeyValueColumn;
        protected final @NotNull String mapEntryColumn;

        protected final @NotNull String arrayLeft;
        protected final @NotNull String arrayRight;
        protected final @NotNull String arrayElementColumn;

        protected final @NotNull String objectLeft;
        protected final @NotNull String objectRight;

        public Idents(@NotNull String stringLeft,
                      @NotNull String stringRight,
                      @NotNull String rawInvokeLeft,
                      @NotNull String rawInvokeRight,
                      @NotNull String mapLeft,
                      @NotNull String mapRight,
                      @NotNull String mapKeyValueColumn,
                      @NotNull String mapEntryColumn,
                      @NotNull String arrayLeft,
                      @NotNull String arrayRight,
                      @NotNull String arrayElementColumn,
                      @NotNull String objectLeft,
                      @NotNull String objectRight) {
            this.stringLeft = stringLeft;
            this.stringRight = stringRight;
            this.rawInvokeLeft = rawInvokeLeft;
            this.rawInvokeRight = rawInvokeRight;
            this.mapLeft = mapLeft;
            this.mapRight = mapRight;
            this.mapKeyValueColumn = mapKeyValueColumn;
            this.mapEntryColumn = mapEntryColumn;
            this.arrayLeft = arrayLeft;
            this.arrayRight = arrayRight;
            this.arrayElementColumn = arrayElementColumn;
            this.objectLeft = objectLeft;
            this.objectRight = objectRight;
        }

        public @NotNull String self() {
            return self;
        }

        public @NotNull String invoke() {
            return invoke;
        }

        public @NotNull String transfer() {
            return transfer;
        }

        public @NotNull String namespaceAnalysis() {
            return namespaceAnalysis;
        }

        public @NotNull String functionInputLeft() {
            return functionInputLeft;
        }

        public @NotNull String functionInputRight() {
            return functionInputRight;
        }

        public @NotNull String nil() {
            return nil;
        }

        public @NotNull String stringLeft() {
            return stringLeft;
        }

        public @NotNull String stringRight() {
            return stringRight;
        }

        public @NotNull String rawInvokeLeft() {
            return rawInvokeLeft;
        }

        public @NotNull String rawInvokeRight() {
            return rawInvokeRight;
        }

        public @NotNull String mapLeft() {
            return mapLeft;
        }

        public @NotNull String mapRight() {
            return mapRight;
        }

        public @NotNull String mapKeyValueColumn() {
            return mapKeyValueColumn;
        }

        public @NotNull String mapEntryColumn() {
            return mapEntryColumn;
        }

        public @NotNull String arrayLeft() {
            return arrayLeft;
        }

        public @NotNull String arrayRight() {
            return arrayRight;
        }

        public @NotNull String arrayElementColumn() {
            return arrayElementColumn;
        }

        public @NotNull String objectLeft() {
            return objectLeft;
        }

        public @NotNull String objectRight() {
            return objectRight;
        }

        public @NotNull DyanasisLexerSettings parentSettings() {
            return DyanasisLexerSettings.this;
        }
    }
}
