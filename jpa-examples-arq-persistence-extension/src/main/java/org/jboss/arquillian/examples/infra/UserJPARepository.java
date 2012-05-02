package org.jboss.arquillian.examples.infra;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.ejb.*;
import javax.persistence.*;

import org.jboss.arquillian.examples.domain.User;
import org.jboss.arquillian.examples.domain.UserRepository;

@Stateless
@Local(UserRepository.class)
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class UserJPARepository implements UserRepository {

    private static final String FIND_ALL_USERS = "User.findAll";
    
    @PersistenceContext
    EntityManager em;
    
    public UserJPARepository() {
        // no-op constructor for the EJB container
    }
    
    public UserJPARepository(EntityManager em) {
        // injected by the test suite
        this.em = em;
    }

    @Override
    public User create(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User modify(User user) {
        em.find(User.class, user.getUserId());
        User mergedUser = em.merge(user);
        return mergedUser;
    }

    @Override
    public void delete(User user) {
        User mergedUser = em.merge(user);
        em.remove(mergedUser);
    }

    @Override
    public User findById(String userId) {
        User user = em.find(User.class, userId);
        return user;
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> findAllQuery = em.createNamedQuery(FIND_ALL_USERS, User.class);
        List<User> userList = findAllQuery.getResultList();
        return userList;
    }

}
