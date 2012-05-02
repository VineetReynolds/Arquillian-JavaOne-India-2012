package org.jboss.arquillian.examples.infra;

import java.sql.Date;

import org.jboss.arquillian.examples.domain.User;

public class TestDataUserBuilder {

    User user;

    public TestDataUserBuilder() {
        this.user = new User();
    }

    public TestDataUserBuilder withUserId(String userId) {
        this.user.setUserId(userId);
        return this;
    }

    public TestDataUserBuilder withFullName(String fullName) {
        this.user.setFullName(fullName);
        return this;
    }

    public TestDataUserBuilder withDateOfBirth(Date dateOfBirth) {
        this.user.setDateOfBirth(dateOfBirth);
        return this;
    }

    public User create() {
        return user;
    }

}
