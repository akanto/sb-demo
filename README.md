# Spring Boot Demo
[![Maintainability](https://api.codeclimate.com/v1/badges/182a1ea33d50b571632a/maintainability)](https://codeclimate.com/github/akanto/sb-demo/maintainability)

Demonstrate how to use JAX-RS instead of Spring MVC.

Test it with:
- JSON response: [http://localhost:8080/greeting?name=World](http://localhost:8080/greeting?name=World)
- Hello Akka: [http://localhost:8080/akka](http://localhost:8080/akka)
- Forbidden: [http://localhost:8080/greeting/error](http://localhost:8080/greeting/error)

References:
- [http://www.insaneprogramming.be/blog/2015/09/04/spring-jaxrs/](http://www.insaneprogramming.be/blog/2015/09/04/spring-jaxrs/)
- [http://www.mkyong.com/webservices/jax-rs/json-example-with-jersey-jackson/](http://www.mkyong.com/webservices/jax-rs/json-example-with-jersey-jackson/)
- [https://spring.io/guides](https://spring.io/guides)

Access to WADL: [http://localhost:8080/application.wadl](http://localhost:8080/application.wadl)

Access to Swagger: [http://localhost:8080/swagger.json](http://localhost:8080/swagger.json)

Generate swagger goloang binding:  `swagger generate client -f http://localhost:8080/swagger.json`