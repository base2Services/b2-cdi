package com.base2services.b2cdi.config.resolvers;

import com.base2services.b2cdi.config.ConfigResolver;

/**
 * resolves configuration values from System Properties
 *
 * @author aaronwalker
 */
public class SystemPropertyConfigResolver implements ConfigResolver {

    @Override
    public String resolve(String name) {
        return System.getProperty(name);
    }

    @Override
    public Integer salience() {
        return 10;
    }
}
