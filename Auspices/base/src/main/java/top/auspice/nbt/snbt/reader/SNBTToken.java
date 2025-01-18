package top.auspice.nbt.snbt.reader;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface SNBTToken {

    enum CompoundStart implements SNBTToken {
        INSTANCE;

        @NotNull
        public String toString() {
            return "'{'";
        }

    }

    enum CompoundEnd implements SNBTToken {
        INSTANCE;

        @NotNull
        public String toString() {
            return "'}'";
        }

    }

    enum ListLikeStart implements SNBTToken {
        INSTANCE;

        @NotNull
        public String toString() {
            return "'['";
        }

    }

    enum ListLikeEnd implements SNBTToken {
        INSTANCE;

        @NotNull
        public String toString() {
            return "']'";
        }

    }

    enum EntrySeparator implements SNBTToken {
        INSTANCE;

        @NotNull
        public String toString() {
            return "':'";
        }

    }

    enum ListTypeSeparator implements SNBTToken {
        INSTANCE;

        @NotNull
        public String toString() {
            return "';'";
        }

    }

    enum Separator implements SNBTToken {
        INSTANCE;

        @NotNull
        public String toString() {
            return "','";
        }

    }

    class Text implements SNBTToken {
        private final boolean quoted;
        @NotNull
        private final String content;

        public Text(boolean quoted, @NotNull String content) {
            Objects.requireNonNull(content);
            this.quoted = quoted;
            this.content = content;
        }

        public boolean getQuoted() {
            return this.quoted;
        }

        @NotNull
        public String getContent() {
            return this.content;
        }

        public boolean component1() {
            return this.quoted;
        }

        @NotNull
        public String component2() {
            return this.content;
        }

        @NotNull
        public Text copy(boolean quoted, @NotNull String content) {
            Objects.requireNonNull(content);
            return new Text(quoted, content);
        }

        @NotNull
        public String toString() {
            return "Text(quoted=" + this.quoted + ", content=" + this.content + ')';
        }

        public int hashCode() {
            int result = Boolean.hashCode(this.quoted);
            result = result * 31 + this.content.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            } else if (!(other instanceof Text var2)) {
                return false;
            } else {
                if (this.quoted != var2.quoted) {
                    return false;
                } else {
                    return Intrinsics.areEqual(this.content, var2.content);
                }
            }
        }
    }
}
