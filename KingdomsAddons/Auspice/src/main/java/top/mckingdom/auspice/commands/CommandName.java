package top.mckingdom.auspice.commands;

import org.intellij.lang.annotations.Pattern;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Documented
@Pattern("[a-zA-Z0-9]+")
@Retention(CLASS)
@Target({METHOD, FIELD, PARAMETER, LOCAL_VARIABLE, ANNOTATION_TYPE})
public @interface CommandName {
}
