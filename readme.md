# Codegen Spring Boot example

Contract first design using [swagger-codegen-maven-plugin](https://github.com/swagger-api/swagger-codegen/blob/master/modules/swagger-codegen-maven-plugin/README.md) 
to generate Spring Boot server code.


## Quick start

Verify you have Maven and Java 8 installed.

```
# Build the jar
mvn clean install

# Run it
java -jar target/io.swagger.petstore-1.0-SNAPSHOT.jar
```
Then hit [localhost:8088/v1/swagger-ui.html](http://localhost:8088/v1/swagger-ui.html) for UI interface.
Endpoints won't return anything except `/v1/pets which contains dummy code.  


## Plugin config
```
<plugin>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-codegen-maven-plugin</artifactId>
    <version>2.2.3</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>./api-contract/petstore.yml</inputSpec>
                <language>spring</language>
                <configOptions>
                    <sourceFolder>swagger</sourceFolder>
                    <java8>true</java8>
                    <interfaceOnly>true</interfaceOnly>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```


## Why `interfaceOnly` option
*Problem:*
Codegen can generate the code for the controllers, but it will be overwritten 
each time you change your API specifications and start a generation. **Not cool !**

*Solution*:
* Define your API specifications in the .yml file
* the Maven codegen plugin with the options `java8` and `interfaceOnly` will generate interface and 
model classes in the target directory.
* Manually define controllers that implement the generated interfaces.
 
