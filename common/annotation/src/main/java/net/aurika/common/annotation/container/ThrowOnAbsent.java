package net.aurika.common.annotation.container;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

@Documented
@NotNull
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ThrowOnAbsent { }
