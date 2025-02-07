package top.auspice.utils.time;

import com.google.common.base.Strings;
import top.auspice.configs.texts.AuspiceLang;
import top.auspice.configs.texts.MessageHandler;
import top.auspice.configs.texts.compiler.builders.MessageObjectBuilder;
import top.auspice.configs.texts.compiler.builders.RawLanguageEntryObjectBuilder;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.utils.AuspiceLogger;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeFormatter {
    public final long millis;
    public final long absoluteSeconds;
    public final long absoluteMinutes;
    public final long absoluteHours;
    public final long absoluteDays;
    public final long absoluteWeeks;
    public final long absoluteMonths;
    public final long years;
    public final long seconds;
    public final long minutes;
    public final long hours;
    public final long days;
    public final long weeks;
    public final long months;
    public final boolean negative;

    public TimeFormatter(long var1, TimeUnit var3) {
        this.negative = var1 < 0L;
        this.millis = var3.toMillis(Math.abs(var1));
        this.absoluteSeconds = Math.round((double)this.millis / 1000.0);
        this.absoluteMinutes = this.absoluteSeconds / 60L;
        this.absoluteHours = this.absoluteMinutes / 60L;
        this.absoluteDays = this.absoluteHours / 24L;
        this.absoluteWeeks = this.absoluteDays / 7L;
        this.absoluteMonths = this.absoluteDays / 30L;
        this.years = this.absoluteDays / 365L;
        this.seconds = this.absoluteSeconds % 60L;
        this.minutes = this.absoluteMinutes % 60L;
        this.hours = this.absoluteHours % 24L;
        this.days = this.absoluteDays % 7L;
        this.weeks = this.absoluteWeeks % 4L;
        this.months = this.absoluteMonths % 12L;
    }

    private static String a(String var0, Object... var1) {
        for(int var2 = var1.length - 1; var2 > 0; --var2) {
            String var3 = String.valueOf(var1[var2]);
            --var2;
            String var4 = String.valueOf(var1[var2]);
            var0 = MessageHandler.replace(var0, var4, var3);
        }

        return var0;
    }

    private static String a(long var0) {
        return var0 >= 10L ? Long.toString(var0) : "0" + Long.toString(var0);
    }

    public static MessageObjectBuilder of(Duration var0) {
        return of(var0.toMillis());
    }

    public static MessageObjectBuilder of(long var0) {
        return (new TimeFormatter(var0, TimeUnit.MILLISECONDS)).getFormat();
    }

    public static String ofRaw(long var0) {
        return (new TimeFormatter(var0, TimeUnit.MILLISECONDS)).toString();
    }

    public String toString() {
        String var1 = this.negative ? "-" : "";
        if (this.absoluteMinutes == 0L) {
            return var1 + "00:00:" + a(this.absoluteSeconds);
        } else if (this.absoluteHours == 0L) {
            return var1 + "00:" + a(this.absoluteMinutes) + ':' + a(this.seconds);
        } else if (this.absoluteDays == 0L) {
            return var1 + a(this.absoluteHours) + ':' + a(this.minutes) + ':' + a(this.seconds);
        } else if (this.absoluteWeeks == 0L) {
            return var1 + this.absoluteDays + " day(s), " + a(this.hours) + ':' + a(this.minutes) + ':' + a(this.seconds);
        } else {
            return this.absoluteMonths == 0L ? var1 + this.a("wwwwa week(s), dd day(s), hh:mm:ss") : var1 + this.a("yyyy years, MMA month(s), wwww week(s), dd day(s), hh:mm:ss");
        }
    }

    private Object[] a() {
        return new Object[]{"yyyy", this.years, "MM", this.months, "MMA", this.absoluteMonths, "wwww", this.weeks, "wwwwa", this.absoluteWeeks, "dd", this.days, "dda", this.absoluteDays, "hh", this.hours, "hha", this.absoluteHours, "hhf", a(this.hours), "hhaf", a(this.absoluteHours), "mm", this.minutes, "mmf", a(this.minutes), "mma", this.absoluteMinutes, "mmaf", a(this.absoluteMinutes), "ss", this.seconds, "ssa", this.absoluteSeconds, "ssf", a(this.seconds), "ssaf", a(this.absoluteSeconds)};
    }

    private String a(String var1) {
        return Strings.isNullOrEmpty(var1) ? var1 : a(var1, this.a());
    }

    public static MessageObjectBuilder dateOf(long var0) {
        return new RawLanguageEntryObjectBuilder(AuspiceLang.DATE_FORMATTER, (var2) -> {
            try {
                return TimeUtils.formatTime(DateTimeFormatter.ofPattern(var2).withZone(TimeZoneHandler.SERVER_TIME_ZONE), Instant.ofEpochMilli(var0), 20).toString();
            } catch (IllegalArgumentException var4) {
                AuspiceLogger.error("Illegal character '" + var2 + "' inside 'date-formatter' inside language file: " + var4.getMessage());
                if (AuspiceLogger.isDebugging()) {
                    var4.printStackTrace();
                }

                return TimeUtils.getDate().toString();
            }
        });
    }

    public MessageObjectBuilder getFormat() {
        String var1;
        if (this.absoluteMinutes == 0L) {
            var1 = "SECONDS";
        } else if (this.absoluteHours == 0L) {
            var1 = "MINUTES";
        } else if (this.absoluteDays == 0L) {
            var1 = "HOURS";
        } else if (this.absoluteWeeks == 0L) {
            var1 = "DAYS";
        } else if (this.absoluteMonths == 0L) {
            var1 = "WEEKS";
        } else {
            var1 = "MONTHS";
        }

        TextPlaceholderProvider var2 = (new TextPlaceholderProvider()).raws(this.a());
        return new LanguageEntryWithContext(AuspiceLang.valueOf("TIME_FORMATTER_" + var1), var2);  //TODO
    }
}
