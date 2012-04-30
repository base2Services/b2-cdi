package com.base2services.b2cdi.config.resolvers;

import com.base2services.b2cdi.config.ConfigProperties;
import com.base2services.b2cdi.config.ConfigResolver;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Properties;

/**
 * Resolves Properties from a Properties file
 *
 * @author aaronwalker
 */
public class PropertiesFileConfigResolver implements ConfigResolver {

    @Inject
    @Any
    @ConfigProperties
    private Instance<Properties> propertyInstance;

    @Override
    public String resolve(String name) {
        for(Properties properties : propertyInstance) {
            if(properties.containsKey(name)) {
                return properties.getProperty(name);
            }
        }
        return null;
    }

    @Override
    public Integer salience() {
        return 20;
    }

}
