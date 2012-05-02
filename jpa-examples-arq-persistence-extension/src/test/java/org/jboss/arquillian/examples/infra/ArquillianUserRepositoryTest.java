package org.jboss.arquillian.examples.infra;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.*;
import java.util.Calendar;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.examples.domain.User;
import org.jboss.arquillian.examples.domain.UserRepository;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArquillianUserRepositoryTest {

    @EJB
    UserRepository userRepository;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "arq-example.jar")
                .addClasses(User.class, UserRepository.class, UserJPARepository.class, TestDataUserBuilder.class)
                .addAsManifestResource("META-INF/orm.xml", "orm.xml")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @UsingDataSet("original.xml")
    @ShouldMatchDataSet("expected.xml")
    public void testCreateUser() throws Exception {
        User user = createTestUser();                    // Setup

        User createdUser = userRepository.create(user);  // Execute

        assertThat(createdUser, equalTo(user));          // Verify
    }

    private User createTestUser() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1883, 7, 3);
        return new TestDataUserBuilder()
                .withUserId("fkafka")
                .withFullName("Franz Kafka")
                .withDateOfBirth(new Date(calendar.getTimeInMillis()))
                .create();
    }

}
