package com.base2services.b2cdi.config;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Properties;

/**
 * Test Props File Producer
 *
 * @author aaronwalker
 */
@ApplicationScoped
public class PropsFileProducer {

    private Properties testProps = new Properties();

    @PostConstruct
    public void init() {
        testProps.setProperty("testProps","test2");
    }

    @Produces
    @ConfigProperties
    public Properties props() {
        return testProps;
    }
}
