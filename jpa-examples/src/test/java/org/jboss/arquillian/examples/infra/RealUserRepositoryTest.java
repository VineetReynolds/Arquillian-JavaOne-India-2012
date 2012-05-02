package org.jboss.arquillian.examples.infra;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.*;
import java.util.Calendar;

import javax.persistence.*;

import org.jboss.arquillian.examples.domain.User;
import org.jboss.arquillian.examples.domain.UserRepository;
import org.junit.*;

public class RealUserRepositoryTest {

    static EntityManagerFactory emf;
    EntityManager em;
    UserRepository userRepository;

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("jpa-examples");
    }

    @AfterClass
    public static void afterClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @Before
    public void setup() throws Exception {
        // Initialize a real EntityManager
        em = emf.createEntityManager();
        userRepository = new UserJPARepository(em);
        em.getTransaction().begin();
    }

    @After
    public void tearDown() {
        if (em != null) {
            em.getTransaction().rollback();
            em.close();
        }
    }

    @Test
    public void testCreateUser() throws Exception {
        // Setup
        User user = createTestUser();

        // Execute
        User createdUser = userRepository.create(user);
        em.flush();
        em.clear();

        // Verify
        User foundUser = em.find(User.class, createdUser.getUserId());
        assertThat(foundUser, equalTo(createdUser));
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
