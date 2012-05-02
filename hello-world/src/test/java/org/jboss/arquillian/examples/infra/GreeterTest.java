package org.jboss.arquillian.examples.infra;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.examples.Greeter;

@RunWith(Arquillian.class)            // #1
public class GreeterTest {

    @Deployment                       // #2
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(Greeter.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject                           // #3
    Greeter greeter;

    @Test                             // #4
    public void should_create_greeting() {
        assertEquals("Hello, Earthling!", greeter.greet("Earthling"));
    }
}
