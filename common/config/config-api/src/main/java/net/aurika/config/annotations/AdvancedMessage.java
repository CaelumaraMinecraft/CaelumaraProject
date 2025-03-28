package net.aurika.config.annotations;

import com.cryptomorin.xseries.XSound;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdvancedMessage {

  XSound DEFAULT_SOUND = XSound.ENTITY_PARROT_IMITATE_HUSK;

  String actionbar() default "";

  String title() default "";

  String subtitle() default "";

  XSound sound() default XSound.ENTITY_PARROT_IMITATE_HUSK;

}