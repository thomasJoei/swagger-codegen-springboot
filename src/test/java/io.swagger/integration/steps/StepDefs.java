package io.swagger.integration.steps;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.integration.SpringIntegrationTest;
import io.swagger.model.Pet;
import java.util.List;
import org.springframework.http.HttpStatus;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefs extends SpringIntegrationTest {

  @When("^the client calls /pets$")
  public void the_client_issues_GET_pets() throws Throwable {
    executeGet("http://localhost:8088/v1/pets");
  }

  @Then("^the client receives status code of (\\d+)$")
  public void the_client_receives_status_code_of(int statusCode) throws Throwable {
    final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
    assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
  }

  @And("^the response contains at least (\\d+) pet$")
  public void the_response_contains_at_least_(int nbPets) throws Throwable {
    String jsonArray = latestResponse.getBody();
    ObjectMapper mapper = new ObjectMapper();

    CollectionType javaType = mapper.getTypeFactory()
        .constructCollectionType(List.class, Pet.class);
    List<Pet> asList = mapper.readValue(jsonArray, javaType);

    assertThat("List size is >=" + nbPets,asList.size() >= nbPets);
    assertThat(asList.get(0), instanceOf(Pet.class));
  }
}