package net.aurika.auspice.user;

import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;

import java.lang.annotation.*;

@Documented
@Pattern(AuspiceUserName.ALLOWED_USER_NAME)
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface AuspiceUserName {

  @Language("RegExp")
  String ALLOWED_USER_NAME = "^[A-Za-z0-9_.-]+$";
  java.util.regex.Pattern USER_NAME_PATTERN = java.util.regex.Pattern.compile(ALLOWED_USER_NAME);

}
