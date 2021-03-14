# sanntidssykkeldata

How to build the sanntidssykkeldata application
---

1. Run `mvn compile jib:dockerBuild` to build docker container of the application
1. Start application with `docker run sanntids-sykkel-service`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
