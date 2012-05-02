package org.jboss.arquillian.examples.domain;

import java.util.List;

public interface UserRepository {

    public User create(User user);

    public User modify(User user);

    public void delete(User user);

    public User findById(String userId);

    public List<User> findAll();
}
