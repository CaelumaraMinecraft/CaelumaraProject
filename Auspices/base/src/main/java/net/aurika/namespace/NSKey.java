package net.aurika.namespace;

import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
@Pattern(NSKey.ALLOWED_NAMESPACE + "+:+" + NSKey.ALLOWED_KEY)
public @interface NSKey {
    @Language("RegExp")
    String ALLOWED_NAMESPACE = "[a-zA-Z]{3,20}";
    java.util.regex.Pattern NAMESPACE_PATTERN = java.util.regex.Pattern.compile(ALLOWED_NAMESPACE);
    @Language("RegExp")
    String ALLOWED_KEY = "[A-Z0-9_]{3,50}";
    java.util.regex.Pattern KEY_PATTERN = java.util.regex.Pattern.compile(ALLOWED_KEY);

    @Documented
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.CLASS)
    @org.intellij.lang.annotations.Pattern(ALLOWED_KEY)
    @interface Key {
    }

    @Documented
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.CLASS)
    @org.intellij.lang.annotations.Pattern(ALLOWED_NAMESPACE)
    @interface Namespace {
    }
}
