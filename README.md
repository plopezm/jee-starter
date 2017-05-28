# jee-starter

JEE project with JAX-RS, eclipselink, security annotation (REST & Session), CORS annotation. This project has been designed using CA4.1.

# Components

This project is composed of the following components:
  - Common (com.aeox.business.common.*): In this packet there is a CRUDService and two default exception mappers.
  - Login (com.aeox.business.login.*): Login component, It has two filters (one for session secure and other for CORS management) and the annotations to enable it.
  - Logger (com.aeox.business.logger.*): Logger components is basically a interceptor to enable a default performance logger.
  

# Tests

Tests are being currently developed using mockito.
