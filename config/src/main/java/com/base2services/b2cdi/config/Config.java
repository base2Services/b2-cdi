package com.base2services.b2cdi.config;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Configuration Marker Annotation
 *
 * @author aaronwalker
 */
@Target(value={TYPE,METHOD,PARAMETER,FIELD})
@Retention(value=RUNTIME)
@Qualifier
@Documented
public @interface Config {
    @Nonbinding String value() default "";
    @Nonbinding String defaultValue() default "";
}
