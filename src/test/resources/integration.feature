Feature: the endpoint /pets return a valid response
  Scenario: client makes call to GET /pets
    When the client calls /pets
    Then the client receives status code of 200
    And the response contains at least 1 pet
