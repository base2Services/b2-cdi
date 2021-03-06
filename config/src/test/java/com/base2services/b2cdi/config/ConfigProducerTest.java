package com.base2services.b2cdi.config;

import com.base2services.b2cdi.config.resolvers.DefaultPropertiesProducer;
import com.base2services.b2cdi.config.resolvers.EnvironmentConfigResolver;
import com.base2services.b2cdi.config.resolvers.PropertiesFileConfigResolver;
import com.base2services.b2cdi.config.resolvers.SystemPropertyConfigResolver;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for com.base2services.b2cdi.config.ConfigProducer
 *
 * @author aaronwalker
 */
@RunWith(Arquillian.class)
public class ConfigProducerTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Config.class)
                .addClass(ConfigProducer.class)
                .addClass(ConfigResolver.class)
                .addClass(ConfigProperties.class)
                .addClass(SystemPropertyConfigResolver.class)
                .addClass(PropertiesFileConfigResolver.class)
                .addClass(EnvironmentConfigResolver.class)
                .addClass(DefaultPropertiesProducer.class)
                .addClass(PropsFileProducer.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    @BeforeClass
    public static void setup() {
        System.setProperty("testConfig","test1");
        System.setProperty("byName","byNameValue");
        Map<String,String> env = new HashMap<String,String>(System.getenv());
        env.put("myenv","testenv");
        setEnv(env);
        assertEquals("testenv", System.getenv("myenv"));
    }

    @Inject
    @Config("testConfig")
    private String testConfig;

    @Inject
    @Config("testProps")
    private String testProps;

    @Inject
    @Config("myenv")
    private String myenv;

    @Inject
    @Config
    private String byName;

    @Inject
    @Config(defaultValue = "default")
    private String myDefault;

    @Inject
    @Config("test.default.props")
    private String testDefaultProps;

    @Inject
    @Config(defaultValue = "true")
    private Boolean booleanDefault;

    @Inject
    @Config(defaultValue = "false")
    private Boolean booleanFalse;

    @Inject
    @Config(defaultValue = "100")
    private Integer defaultInteger;

    @Inject
    @Config(defaultValue = "100")
    private Long defaultLong;

    @Inject
    @Config(defaultValue = "100.5")
    private Float defaultFloat;

    @Inject
    @Config(defaultValue = "100.5")
    private Double defaultDouble;

    @Inject
    @Config("test.boolean.true.prop")
    private Boolean booleanTrueProp;

    @Inject
    @Config("test.boolean.false.prop")
    private Boolean booleanFalseProp;

    @Inject
    @Config("test.integer.prop")
    private Integer integerProp;

    @Inject
    @Config("test.long.prop")
    private Long longProp;

    @Inject
    @Config("test.float.prop")
    private Float floatProp;

    @Inject
    @Config("test.double.prop")
    private Double doubleProp;

    @Test
    public void testLoadDefaultProps() {
        assertEquals("defaultProps",testDefaultProps);
    }

    @Test
    public void testDefaultValue() {
        assertEquals("default",myDefault);
    }

    @Test
    public void testSystemProperty() {
        assertEquals(System.getProperty("testConfig"),testConfig);
    }

    @Test
    public void testPropsFile() {
        assertEquals("test2",testProps);
    }

    @Test
    public void testEnvironmentVariable() {
        assertEquals("testenv",myenv);
    }

    @Test
    public void testResolveByName() {
        assertEquals("byNameValue",byName);
    }

    @Test
    public void testBooleanWithDefault() {
        assertTrue(booleanDefault);
    }

    @Test
    public void testBooleanWithDefaultFalse() {
        assertFalse(booleanFalse);
    }

    @Test
    public void testDefaultInteger() {
        assertEquals(new Integer(100),defaultInteger);
    }

    @Test
    public void testDefaultLong() {
        assertEquals(new Long(100),defaultLong);
    }

    @Test
    public void testDefaultFloat() {
        assertEquals(new Float(100.5),defaultFloat);
    }

    @Test
    public void testDefaultDouble() {
        assertEquals(new Double(100.5),defaultDouble);
    }

    @Test
    public void testBooleanPropFromFile() {
        assertTrue(booleanTrueProp);
        assertFalse(booleanFalseProp);
    }

    @Test
    public void testIntegerPropFromFile() {
        assertEquals(new Integer(100),integerProp);
    }

    @Test
    public void testLongPropFromFile() {
        assertEquals(new Long(100),longProp);
    }

    @Test
    public void testFloatPropFromFile() {
        assertEquals(new Float(100.5),floatProp);
    }

    @Test
    public void testDoublePropFromFile() {
        assertEquals(new Double(100.5),doubleProp);
    }

    //This is a hack so I can test setting an environment variable
    //should work on all OS'es
    protected static void setEnv(Map<String, String> newenv)
    {
        try
        {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newenv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newenv);
        }
        catch (NoSuchFieldException e)
        {
            try {
                Class[] classes = Collections.class.getDeclaredClasses();
                Map<String, String> env = System.getenv();
                for(Class cl : classes) {
                    if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                        Field field = cl.getDeclaredField("m");
                        field.setAccessible(true);
                        Object obj = field.get(env);
                        Map<String, String> map = (Map<String, String>) obj;
                        map.clear();
                        map.putAll(newenv);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
