# Introduction

A very simple example demonstrating the use of Arquillian.
Derived from the Arquillian Getting Started Guide.

## Setup

This examples requires a JBoss AS 7.1 or a GlassFish 3.1 installation.

### JBoss AS 7 Instructions

Download JBoss AS 7.1 from `http://www.jboss.org/jbossas/downloads/`.
Extract the distribution to some location.
Update the `jbossHome` property in the `src/test/resources/arquillian.xml` file, to the location of the JBoss AS installation.

### GlassFish 3.1 Instructions

Download JBoss AS 7.1 from `http://www.jboss.org/jbossas/downloads/`.
Extract the distribution to some location.
Update the `glassFishHome` property in the `src/test/resources/arquillian.xml` file, to the location of the GlassFish 3.1 installation.

## How do I run it?

### From Eclipse

This works best with the m2eclipse plugin.
Enable the jbossas-7-managed, if you want to run the test on a JBoss AS 7 instance, that is started or stopped by Arquillian,
Or enable the glassfish-31-remote profile to run the test on a GlassFish 3.1. instance that you've already started.

Run the JUnit test in the GreeterTest class, as usual. Right-click on the class in Project Explorer, and run the JUnit Test from the "Run As" menu item.

### From the command-line (Maven)

To test against a managed JBoss AS 7 instance:

`mvn -Pjbossas-7-managed clean test`

or to test against a managed GlassFish 3.1 instance:

`mvn -Pglassfish-31-managed clean test`