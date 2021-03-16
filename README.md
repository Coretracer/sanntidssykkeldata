# sanntidssykkeldata

How to build the sanntidssykkeldata application
---

1. Run `mvn clean package` to build docker container of the application
1. Start application with `docker run --rm -p 8080:8080 sanntids-sykkel-service`
1. To check that  application is running enter url `http://localhost:8080`
1. To check that  all exposed resources `http://localhost:8080/swagger`
1. To get the stativer resource `http://localhost:8080/stativer`

Health Check
---

To see applications health enter url `http://localhost:8081/healthcheck`. Right now there is no custom healthcheck registerd. 

NOTE 
---
1. Error handling is missing in quite many places. IMO a heavy revisit require to do all corner case handling.   

Assumptions
---

1. Stasjon and Tilgjengelighet data are not corrupted i.e. station id are not null and unique.  

TODO and limitation.
---
1. Integration test not done.
1. Paginated response is also common practice and could have been done. 
1. Tons of unit test case is missing. A unit test case should be there for almost each file and each public function.
1. Content of SykkelStativ what goes to user needs relook. Currently I put some data, but what data should go there must be though again.
1. No security to access any of the resources. 

