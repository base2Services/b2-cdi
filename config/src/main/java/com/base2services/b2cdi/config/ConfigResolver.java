package com.base2services.b2cdi.config;

/**
 * Configuration Resolver
 *
 * @author aaronwalker
 */
public interface ConfigResolver {
    String resolve(String name);
    Integer salience();
}
