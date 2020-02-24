# ProductStore API

Contract first design using [swagger-codegen-maven-plugin](https://github.com/swagger-api/swagger-codegen/blob/master/modules/swagger-codegen-maven-plugin/README.md) 
to generate Controllers and DTOs.


## Quick start

Verify you have Maven and Java 8 installed.

```
# Build the jar
mvn clean install


# Run the jar
java -jar target/productstore-api-1.0-SNAPSHOT.jar
```
Then hit [localhost:8080/v1/swagger-ui.html](http://localhost:8080/v1/swagger-ui.html) for UI interface.


After starting the application you can connect the database on jdbc:h2:~/products;AUTO_SERVER=TRUE using `centric` user with no password.

## Tests

You can find tests under the src/test folder.
Functional tests have also been included in that folder for simplicity purpose, it wouldn't be the best in a real 
project since they would run with unit tests and take a long time to complete. Best option will be to run them into 
a separate folder as mentioned here https://dzone.com/articles/advanced-functional-testing-in-spring-boot-by-usin.

Functional tests show how the API handle concurrent reservations for the same dates.

To run all tests (Unit + Functional) do:
```
mvn clean test
```

Test are using an in-memory H2 database.


## Notes

- For simplicity API is using UTC timezone.
- Unit tests have not been pushed too far in this exercise except for controller validation, real project would require more for 
service layer and Util classes.