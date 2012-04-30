package com.base2services.b2cdi.logging;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import javax.inject.Inject;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Unit test for com.base2services.b2cdi.logging.LoggerProducer
 *
 * @author aaronwalker
 */
@RunWith(Arquillian.class)
public class LoggerProducerTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(LoggerProducer.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private Logger log;

    @Inject
    @Category("myCategory")
    private Logger log2;

    @Test
    public void testInjectedLogger() {
        assertNotNull(log);
        log.info("test from inject logger " + log.getName());
        assertThat(log.getName(), equalTo(LoggerProducerTest.class.getName()));
    }

    @Test
    public void testInjectedLoggerWithCategory() {
        assertNotNull(log2);
        log.info("test from inject logger with category " + log2.getName());
        assertThat(log2.getName(), equalTo("myCategory"));
    }
}
