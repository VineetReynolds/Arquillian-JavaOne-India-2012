package org.jboss.arquillian.examples.infra;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.Calendar;

import javax.persistence.EntityManager;

import org.jboss.arquillian.examples.domain.User;
import org.jboss.arquillian.examples.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;

public class MockUserRepositoryTest {

    private UserRepository userRepository;
    private EntityManager em;

    @Before
    public void injectDependencies() {
        em = mock(EntityManager.class);                     // Mock
        userRepository = new UserJPARepository(em);
    }

    @Test
    public void testCreateUser() throws Exception {        
        User user = createTestUser();                       // Setup

        User createdUser = userRepository.create(user);     // Execute

        verify(em, times(1)).persist(user);                 // Verify
        assertThat(createdUser, equalTo(user));
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
