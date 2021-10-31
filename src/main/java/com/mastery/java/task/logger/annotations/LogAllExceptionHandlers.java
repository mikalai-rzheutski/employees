package com.mastery.java.task.logger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.logging.Level;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAllExceptionHandlers {
}
