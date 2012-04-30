package com.base2services.b2cdi.config.resolvers;

import com.base2services.b2cdi.config.ConfigProperties;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A default properties file producer
 *
 * @author aaronwalker
 */
@ApplicationScoped
public class DefaultPropertiesProducer {

    private Properties defaultProps = new Properties();

    @PostConstruct
    public void load() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("default.properties");
            if(is != null) {
                defaultProps.load(is);
                is.close();
            }
        } catch (IOException ex) {
            //ignore any errors
        }
    }

    @Produces
    @ConfigProperties
    public Properties produce() {
        return defaultProps;
    }

}
