package net.aurika.common.examination.reflection;

import net.kyori.examination.Examinable;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface ExaminableConstructor {

  Class<? extends Examinable> publicType();

  String[] properties();

}
