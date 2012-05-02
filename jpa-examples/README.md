# Introduction

A very simple example demonstrating the testing of a JPA-based Repository using a mock EntityManager and a real EntityManager.

## Setup

This example requires a Derby database installation installation.

### Derby Setup Instructions

Download Derby 10.x from `http://db.apache.org/derby/`.
Extract the distribution to some location.

### Setting up the schema

Start the Derby server using the `startNetworkServer` command.

Start the Derby ij tool:

    java -jar %DERBY_HOME%/lib/derbyrun.jar ij
    
Connect to the Derby instance using the listed connect string. This will create a Derby instance, if it does not exist.

    connect 'jdbc:derby://localhost:1527/JAVAONE;create=true';
    
Create the `USER_ACCOUNT` table in the database:

    create table APP.USER_ACCOUNT (
        userId varchar(255) not null,
        dateOfBirth date,
        fullName varchar(255),
        primary key (userId)
    );

The DDL can also be found in the `src/main/sql` directory.

## Running the example

### Command-line (Maven)

`mvn clean test`

### Eclipse

Run the tests in the `MockUserRepositoryTest` and the `RealUserRepositoryTest` classes using the Eclipse JUnit runner (Alt+Shift+X, T).