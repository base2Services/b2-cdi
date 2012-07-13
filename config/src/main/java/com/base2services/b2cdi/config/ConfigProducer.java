package com.base2services.b2cdi.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Lookups configuration inject points using the available config resolver
 * instances
 *
 * @author aaronwalker
 */
@ApplicationScoped
public class ConfigProducer {
    @Inject
    @Any
    private Instance<ConfigResolver> resolverInstances;

    @Produces
    @Config
    public String produceStringConfig(InjectionPoint ip) {
        return resolve(ip);
    }

    @Produces
    @Config
    public Boolean produceBooleanConfig(InjectionPoint ip) {
        return Boolean.valueOf(resolve(ip));
    }

    @Produces
    @Config
    public Integer produceIntegerConfig(InjectionPoint ip) {
        return Integer.valueOf(resolve(ip));
    }

    @Produces
    @Config
    public Long produceLongConfig(InjectionPoint ip) {
        return Long.valueOf(resolve(ip));
    }

    @Produces
    @Config
    public Float produceFloatConfig(InjectionPoint ip) {
        return Float.valueOf(resolve(ip));
    }

    @Produces
    @Config
    public Double produceDoubleConfig(InjectionPoint ip) {
        return Double.valueOf(resolve(ip));
    }

    private String resolve(InjectionPoint ip) {
        String name = ip.getAnnotated().getAnnotation(Config.class).value();
        if("".equals(name)) {
            name = ip.getMember().getName();
        }
        String defaultValue = ip.getAnnotated().getAnnotation(Config.class).defaultValue();
        for(ConfigResolver resolver : getResolvers()) {
            String value = resolver.resolve(name);
            if(value != null && value.length() > 0) {
                return value;
            }
        }
        return "".equals(defaultValue) ? null : defaultValue;

    }

    private List<ConfigResolver> getResolvers() {
        List<ConfigResolver> resolvers = new ArrayList<ConfigResolver>();
        for(ConfigResolver instance :resolverInstances) {
            resolvers.add(instance);
        }
        Collections.sort(resolvers, new Comparator<ConfigResolver>() {
            @Override
            public int compare(ConfigResolver configResolver, ConfigResolver configResolver1) {
                return configResolver.salience().compareTo(configResolver1.salience());
            }
        });
        return resolvers;
    }
}
