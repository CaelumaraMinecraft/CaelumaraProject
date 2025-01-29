package net.aurika.tasks.annotations;

import net.aurika.tasks.priority.PriorityPhase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Priority {
    PriorityPhase priority();
}
