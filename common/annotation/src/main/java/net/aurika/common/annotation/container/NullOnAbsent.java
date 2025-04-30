package net.aurika.common.annotation.container;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.*;

@Documented
@Nullable
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface NullOnAbsent { }
