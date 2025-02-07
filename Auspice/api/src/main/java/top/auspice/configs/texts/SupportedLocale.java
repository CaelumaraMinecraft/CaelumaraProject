package top.auspice.configs.texts;

import java.util.Locale;

@Deprecated
public enum SupportedLocale implements StyledLanguage {

    EN(Locale.ENGLISH),
    CH(Locale.CHINESE),



    ;


    public static final SupportedLocale[] VALUES = values();
    private final Locale locale;

    SupportedLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String getLanguageName() {
        return this.name();
    }

    @Override
    public Locale getLocal() {
        return this.locale;
    }

    @Override
    public String getStyleName() {
        return "default-format";
    }
}





