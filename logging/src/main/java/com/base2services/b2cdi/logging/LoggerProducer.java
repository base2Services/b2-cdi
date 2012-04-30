package com.base2services.b2cdi.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * SLF4J CDI Producer
 *
 * To use simple Inject a SLF4J Logger into any cdi bean
 * <pre>
 *     ....
 *     public class MyBean {
 *
 *         @Inject Logger log;
 *
 *         .....
 *
 *         public void someMethod() {
 *             log.info("look mum I've been injected");
 *             .....
 *         }
 *     }
 *
 * </pre>
 * The category of the injected logger is the fully qualified class name of the injected class
 *
 * If you want to set the logging category you can add an @Category annotation
 * <pre>
 *     ....
 *     public class MyBean {
 *
 *         @Inject
 *         @Category("mycategory")
 *         Logger log;
 *
 *         .....
 *
 *         public void someMethod() {
 *             log.info("look mum I've been injected with a custom category of mycategory");
 *             .....
 *         }
 *     }
 * </pre>
 *
 * @author aaronwalker
 */
public class LoggerProducer {

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        if(injectionPoint.getAnnotated().isAnnotationPresent(Category.class)) {
            Category category = injectionPoint.getAnnotated().getAnnotation(Category.class);
            return LoggerFactory.getLogger(category.value());
        } else {
            return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
        }
    }
}
