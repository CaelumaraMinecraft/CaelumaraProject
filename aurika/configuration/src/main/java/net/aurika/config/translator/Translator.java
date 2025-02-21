package net.aurika.config.translator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.text.compiler.TextCompilerSettings;
import net.aurika.auspice.text.compiler.TextObject;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.compiler.math.MathCompiler;
import top.auspice.utils.number.RomanNumber;

public interface Translator<O> {

    Translator<MathCompiler.Expression> MATH = new Translator<>() {
        @Override
        public @NotNull Class<MathCompiler.Expression> getOutputType() {
            return MathCompiler.Expression.class;
        }

        @Override
        public MathCompiler.Expression translate(String string) {
            return MathCompiler.compile(string);
        }
    };

    Translator<ConditionCompiler.LogicalOperand> CONDITION = new Translator<>() {
        @Override
        public @NotNull Class<ConditionCompiler.LogicalOperand> getOutputType() {
            return ConditionCompiler.LogicalOperand.class;
        }

        @Override
        public ConditionCompiler.LogicalOperand translate(String string) {
            return ConditionCompiler.compile(string).evaluate();
        }
    };

    Translator<TextObject> MESSAGE = new Translator<>() {
        @Override
        public @NotNull Class<TextObject> getOutputType() {
            return TextObject.class;
        }

        @Override
        public TextObject translate(String string, Object[] settings) {
            if (settings.length >= 1) {
                if (settings[0] instanceof TextCompilerSettings compilerSettings) {
                    return TextCompiler.compile(string, compilerSettings);
                }
            }
            return TextCompiler.compile(string);
        }

        public TextObject translate(String string) {
            return TextCompiler.compile(string);
        }
    };

    Translator<OfflinePlayer> OFFLINE_PLAYER = new Translator<>() {
        @Override
        public OfflinePlayer translate(String string) {
            return PlayerUtils.getOfflinePlayer(string);
        }

        @NotNull
        @Override
        public Class<OfflinePlayer> getOutputType() {
            return OfflinePlayer.class;
        }

    };

    Translator<Boolean> BOOLEAN = new Translator<>() {

        @Override
        public Boolean translate(String string) {
            return Boolean.parseBoolean(string);
        }

        @NotNull
        @Override
        public Class<? super Boolean> getOutputType() {
            return Boolean.class;
        }
    };

    Translator<Byte> BYTE = new Translator<>() {
        @Override
        public Byte translate(String string) {
            return Byte.parseByte(string);
        }

        @NotNull
        @Override
        public Class<? super Byte> getOutputType() {
            return Byte.class;
        }
    };

    Translator<Short> SHORT = new Translator<>() {
        @Override
        public Short translate(String string) {
            return Short.parseShort(string);
        }

        @NotNull
        @Override
        public Class<? super Short> getOutputType() {
            return Short.class;
        }
    };

    Translator<Integer> INTEGER = new Translator<>() {

        @Override
        public Integer translate(String string) {
            return Integer.parseInt(string);    //todo
        }

        @NotNull
        @Override
        public Class<Integer> getOutputType() {
            return Integer.class;
        }
    };

    Translator<Long> LONG = new Translator<>() {

        @Override
        public Long translate(String string) {
            return Long.parseLong(string);
        }

        @NotNull
        @Override
        public Class<? super Long> getOutputType() {
            return Long.class;
        }
    };

    Translator<Float> FLOAT = new Translator<Float>() {
        @Override
        public Float translate(String string) {
            return Float.parseFloat(string);
        }

        @NotNull
        @Override
        public Class<? super Float> getOutputType() {
            return Float.class;
        }
    };

    Translator<Double> DOUBLE = new Translator<>() {

        @Override
        public Double translate(String string) {
            return Double.parseDouble(string);
        }

        @NotNull
        @Override
        public Class<? super Double> getOutputType() {
            return Double.class;
        }
    };

    Translator<Integer> ROMAN_NUMBER = new Translator<Integer>() {
        @Override
        public Integer translate(String string) {
            return RomanNumber.fromRoman(string);
        }

        @Override
        public @NotNull Class<? super Integer> getOutputType() {
            return Integer.class;
        }
    };

    Translator<String> STRING = new Translator<>() {  //最水的一个 (

        @Override
        public String translate(String string) {
            return string;
        }

        @NotNull
        @Override
        public Class<String> getOutputType() {
            return String.class;
        }
    };

    default @Nullable O translate(@Nullable String string, @Nullable Object... settings) {
        return null;
    }

    @Nullable O translate(@Nullable String configString);

    @NotNull Class<? super O> getOutputType();

}
