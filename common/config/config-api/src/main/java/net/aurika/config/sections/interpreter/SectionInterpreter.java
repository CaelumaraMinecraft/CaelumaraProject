package net.aurika.config.sections.interpreter;

import net.aurika.auspice.utils.compiler.math.MathCompiler;
import net.aurika.auspice.utils.math.MathUtils;
import net.aurika.auspice.utils.time.TimeUtils;
import net.aurika.config.sections.ConditionalConfigSection;
import net.aurika.config.sections.ConfigSection;
import net.aurika.config.sections.interpreter.SectionInterpretContext.Result;
import net.aurika.config.validation.ConfigValidators;
import net.aurika.text.placeholders.context.PlaceholderProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.Duration;

/**
 * 配置节的解释器. 作用是将配置节尝试解释为某种对象, 解释失败会抛出 {@linkplain ConfigInterpretException} 等异常
 */
public interface SectionInterpreter<T> {

  SectionInterpreter<ConditionalConfigSection> CONDITIONAL_SECTION = new SectionInterpreter<ConditionalConfigSection>() {

    public ConditionalConfigSection parse(@NotNull SectionInterpretContext<ConditionalConfigSection> context) throws ConfigInterpretException {
      ConfigSection section = context.getSection();
      if (section == null) {
        return null;
      }

      ConfigSection finalValueSection;
      finalValueSection = section.getSubSection("others");

      for (String conditionalKey : section.getKeys()) {
        if ("others".equals(conditionalKey)) {
          continue;
        }
      }

      return null;
    }

    private static ConditionalConfigSection a(ConfigSection section) {

    }
  };

  SectionInterpreter<java.awt.Color> COLOR = new SectionInterpreter<Color>() {

    public Color parse(@NotNull SectionInterpretContext<Color> context) throws ConfigInterpretException {
      ConfigSection section = context.getSection();
      if (section == null) {
        return null;
      }

      Object parsed = section.getParsedValue();
      if (parsed instanceof Color) {
        return (Color) parsed;
      }

      if (section.hasSubSections()) {
        Color color = a(section);

        //noinspection ConstantValue
        if (color != null) {
          section.setParsedValue(color);
        }
        return color;
      }

      String str = section.getConfigureString();
      if (str == null) {
        throw new ConfigInterpretException();  //TODO
      }
    }

    private static Color a(ConfigSection colorSection) {
      Integer R = colorSection.getInteger(new String[]{"r"}) == null ? colorSection.getInteger(
          new String[]{"R"}) : null;
      Integer G = colorSection.getInteger(new String[]{"g"}) == null ? colorSection.getInteger(
          new String[]{"G"}) : null;
      Integer B = colorSection.getInteger(new String[]{"b"}) == null ? colorSection.getInteger(
          new String[]{"B"}) : null;
      Integer A = colorSection.getInteger(new String[]{"a"}) == null ? colorSection.getInteger(
          new String[]{"A"}) : null;

      if (R == null || G == null || B == null) {
        throw new ConfigInterpretException();  //TODO
      } else {
        return new Color(R, G, B, A == null ? 255 : A);
      }
    }
  };

  SectionInterpreter<Duration> TIME = context -> getTime(context.getSection(), PlaceholderProvider.EMPTY);

  static Duration getTime(@Nullable ConfigSection section, @Nullable PlaceholderProvider placeholderProvider) {
    if (section == null) {
      return null;
    } else {
      Object constructed;
      if (section.getLabel() != ConfigValidators.PERIOD) {
        String s = section.getConfigureString();

        try {
          constructed = MathCompiler.compile(s);
        } catch (Throwable var5) {
          Long var10000 = TimeUtils.parseTime(s);
          if (var10000 == null) {
            return null;
          }

          constructed = Duration.ofMillis(var10000);
        }

        if (constructed == null) {
          return null;
        }

        section.setLabel(ConfigValidators.PERIOD);
        section.setParsedValue(constructed);
      } else {
        constructed = section.getParsedValue();
      }

      if (constructed instanceof Number) {
        return Duration.ofMillis(((Number) constructed).longValue());
      } else {
        return constructed instanceof Duration ? (Duration) constructed : Duration.ofMillis((long) MathUtils.eval(
            (MathCompiler.Expression) constructed,
            placeholderProvider == null ? PlaceholderProvider.EMPTY : placeholderProvider
        ));
      }
    }
  }

  SectionInterpreter<Integer> INTEGER = new SectionInterpreter<Integer>() {
    @Override
    public Integer parse(@NotNull SectionInterpretContext<Integer> context) throws ConfigInterpretException {
      ConfigSection section = context.getSection();
      Integer i;
      if (section != null) {
        if ((i = section.getInteger()) != null) {
          return i;
        }
        if (section.getParsedValue() instanceof String s) {
          try {
            return Integer.parseInt(s);
          } catch (NumberFormatException ignored) {
          }
        }
      }
      return null;
    }
  };

  T parse(@NotNull SectionInterpretContext<T> context) throws ConfigInterpretException;

  default T parse(@Nullable ConfigSection section) throws ConfigInterpretException {
    return this.parse(new SectionInterpretContext<>(section));
  }

  static Boolean getBoolean(SectionInterpretContext<Boolean> context) {
    ConfigSection section = context.getSection();
    if (section == null) {
      return context.asResult(Result.NULL, Boolean.FALSE);
    } else {
      if (!(section.getParsedValue() instanceof Boolean)) {
        return context.asResult(Result.DIFFERENT_TYPE, Boolean.FALSE);
      } else {
        return ((Boolean) section.getParsedValue());
      }
    }
  }

}
