package org.jboss.arquillian.examples.infra;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.*;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.*;
import javax.sql.DataSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.examples.domain.User;
import org.jboss.arquillian.examples.domain.UserRepository;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArquillianUserRepositoryTest {

    @EJB
    UserRepository userRepository;
    
    @Resource(mappedName = "jdbc/arqExampleDS")
    DataSource ds;
    
    @PersistenceContext
    EntityManager em;
    
    @PersistenceUnit
    EntityManagerFactory emf;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "arq-example.jar")
                .addClasses(User.class, UserRepository.class, UserJPARepository.class, TestDataUserBuilder.class)
                .addAsManifestResource("META-INF/orm.xml", "orm.xml")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setup() throws Exception {
        clearTables();
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = createTestUser();                    // Setup

        User createdUser = userRepository.create(user);  // Execute

        User foundUser = findUser(user.getUserId());     // Verify
        assertThat(createdUser, equalTo(foundUser));
    }

    private User findUser(String userId) throws Exception {
        Connection conn = null;
        PreparedStatement pStmt = null;
        try {
            conn = ds.getConnection();
            pStmt = conn.prepareStatement("SELECT u.userId, u.fullName, u.dateOfBirth FROM USER_ACCOUNT u WHERE u.userId = ?");
            pStmt.setString(1, userId);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString(1));
                user.setFullName(rs.getString(2));
                user.setDateOfBirth(rs.getDate(3));
                return user;
            }
        } finally {
            if (pStmt != null) {
                pStmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
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

    private void clearTables() throws Exception {
        Connection conn = null;
        PreparedStatement pStmt = null;
        try {
            conn = ds.getConnection();
            pStmt = conn.prepareStatement("DELETE FROM USER_ACCOUNT u");
            pStmt.executeUpdate();
        } finally {
            if (pStmt != null) {
                pStmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
