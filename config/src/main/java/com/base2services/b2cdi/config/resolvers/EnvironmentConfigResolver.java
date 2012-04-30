package com.base2services.b2cdi.config.resolvers;

import com.base2services.b2cdi.config.ConfigResolver;

/**
 * Resolves config from the System environment variables
 *
 * @author aaronwalker
 */
public class EnvironmentConfigResolver implements ConfigResolver {


    @Override
    public String resolve(String name) {
        return System.getenv(name);
    }

    @Override
    public Integer salience() {
        return 5;
    }
}
