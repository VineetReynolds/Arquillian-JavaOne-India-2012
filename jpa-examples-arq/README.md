# Introduction

A very simple example demonstrating the testing of a JPA-based Repository using Arquillian and a container-provided EntityManager.
Assertions are performed using an injected datasource.

## Setup

This example requires a Derby Database installation.
It also requires a JBoss AS 7.1 or a GlassFish 3.1 installation.


### Derby Setup Instructions

Download Derby 10.x from `http://db.apache.org/derby/`.
Extract the distribution to some location.

#### Setting up the schema

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


### JBoss AS 7 Instructions

Download JBoss AS 7.1 from `http://www.jboss.org/jbossas/downloads/`.
Extract the distribution to some location.
Update the `jbossHome` property in the `src/test/resources/arquillian.xml` file, to the location of the JBoss AS installation.

Deploy the derby JDBC client JAR - `derbyclient.jar` in JBoss. This is required since the Derby JDBC driver does not come pre-installed in a JBoss domain.
Create a new JNDI datasource registered at `java:/jdbc/arqExampleDS` which uses a JDBC connect string of `jdbc:derby://localhost:1527/javaone`.

### GlassFish 3.1 Instructions

Download JBoss AS 7.1 from `http://www.jboss.org/jbossas/downloads/`.
Extract the distribution to some location.
Update the `glassFishHome` property in the `src/test/resources/arquillian.xml` file, to the location of the GlassFish 3.1 installation.

Create a new JDBC connection pool that uses the previously created Derby instance. Specify a JDBC connect string of `jdbc:derby://localhost:1527/javaone`.
Create a JNDI datasource that is registered at `jdbc/arqExampleDS`.

## How do I run it?

### From Eclipse

This works best with the m2eclipse plugin.
Enable the jbossas-7-managed, if you want to run the test on a JBoss AS 7 instance, that is started or stopped by Arquillian,
Or enable the glassfish-31-managed profile to run the test on a GlassFish 3.1. instance that is also started or stopped by Arquillian.

Run the JUnit test in the GreeterTest class, as usual. Right-click on the class in Project Explorer, and run the JUnit Test from the "Run As" menu item.

### From the command-line (Maven)

To test against a managed JBoss AS 7 instance:

`mvn -Pjbossas-7-managed clean test`

or to test against a managed GlassFish 3.1 instance:

`mvn -Pglassfish-31-managed clean test`