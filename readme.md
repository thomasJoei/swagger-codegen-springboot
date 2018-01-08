# Codegen Spring Boot example

Contract first design using [swagger-codegen-maven-plugin](https://github.com/swagger-api/swagger-codegen/blob/master/modules/swagger-codegen-maven-plugin/README.md) 
to generate Spring Boot server code.

At the time of writing, up to date documentation about the swagger-codegen-maven-plugin and how to use it is not so easy to find.
The goals of that project are :
 * To show how to use an OpenAPI specification file to generate server code
 (here interfaces and model classes).
 * To avoid the problem of **overwritten code** during code generation.
 * Give a already set up project, that you can use as a start to build your Rest API.


## Quick start

Verify you have Maven and Java 8 installed.

```
# Build the jar
mvn clean install

# Run it
java -jar target/io.swagger.petstore-1.0-SNAPSHOT.jar
```
Then hit [localhost:8088/v1/swagger-ui.html](http://localhost:8088/v1/swagger-ui.html) for UI interface.
Endpoints will return empty Pets except `/v1/pets` which contains dummy code to serve as an example.  


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


## Why `interfaceOnly` option ?
**Problem:**
Codegen can generate the code for the controllers, but it will be overwritten 
each time you change your API specifications and start a generation. **Not cool !**

**Solution:**
* Define your API specifications in the .yml file
* Compile, the Maven codegen plugin with the options `java8` and `interfaceOnly` will generate interface and 
model classes in the target directory.
* Manually write controllers that implement the generated interfaces.
 
