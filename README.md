# jee-starter

JEE project with JAX-RS, eclipselink, security annotation (REST & Session), CORS annotation. This project has been designed using CA4.1.

# Components

This project is composed of the following components:
  - Common (com.aeox.business.common.*): In this packet there is a CRUDService and two default exception mappers.
  - Login (com.aeox.business.login.*): Login component, It has two filters (one for session secure and other for CORS management) and the annotations to enable it.
  - Logger (com.aeox.business.logger.*): Logger components is basically a interceptor to enable a default performance logger.
  

# Tests

Tests are being currently developed using mockito. Executing the tests is as follows:

```
mvn test
```

The output shows the test results. The tests implemented are Unit tests with mockito and Integration tests with embedded database.


# Creating artifact
Creating an artifact is the best way to reuse this starter from multiple projects. Below it is explained how to create it en local maven repository.

1. mvn archetype:create-from-project
2. cd target/generated-sources/archetype/
2. mvn install

Now it is possible to create a new project from this archetype:
1. mvn archetype:generate -Dfilter=com.aeox:jee-starter

# MIT License

Copyright 2017 Pablo López Martínez

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
