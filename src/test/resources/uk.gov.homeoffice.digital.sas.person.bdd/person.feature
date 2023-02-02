Feature: Person

  Scenario: Create Person

    Given Trevor is a user
    And the valid persons are
      """
      {
        "firstName": "John",
        "lastName": "Smith"
      }
      """
    When Trevor creates the valid persons in the person service
    Then the last response should have a status code of 200
    Then the 1st of the persons in the last response should contain
      | field      | type    | expectation           |
      | firstName  | String  | isEqualTo("John")     |
      | lastName   | String  | isEqualTo("Smith")    |
