package com.base2services.b2cdi.logging;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation that allows setting the log category
 *
 * @author aaronwalker
 */
@Target(value={TYPE,METHOD,PARAMETER,FIELD})
@Retention(value=RUNTIME)
@Documented
public @interface Category {
    String value();
}
