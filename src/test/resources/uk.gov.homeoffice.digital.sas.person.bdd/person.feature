Feature: Person

  Background:
    Given Trevor is a user

  Scenario: Create Person

    And the valid persons are
      """
      {
        "firstName": "John",
        "lastName": "Smith",
        "version": 1,
        "fteValue": 0.5405,
        "termsAndConditions": "PRE_MODERNISED"
      }
      """
    And the invalid persons are
      """
      {
        "firstName": "John",
        "lastName": "Smith",
        "version": 1,
        "fteValue": 0.5405,
        "termsAndConditions": "PRE-MODERNISED"
      }
      """
    When Trevor creates the valid persons in the person service
    Then the last response should have a status code of 200
    Then the 1st of the persons in the last response should contain
      | field      | type    | expectation           |
      | firstName  | String  | isEqualTo("John")     |
      | lastName   | String  | isEqualTo("Smith")    |
      | version    | Integer | isEqualTo(1)          |
      | fteValue   | float   | isEqualTo(0.5405f)       |
      | termsAndConditions   | String   | isEqualTo("PRE_MODERNISED")       |

    When Trevor creates the invalid persons in the person service
    Then the last response should have a status code of 400

  Scenario: Create Person with invalid FTE value

    And the invalid persons are
      """
      {
        "firstName": "John",
        "lastName": "Smith",
        "version": 1,
        "fteValue": 0.54051,
        "termsAndConditions": "PRE_MODERNISED"
      },
      {
        "firstName": "Adam",
        "lastName": "Smith",
        "version": 1,
        "fteValue": 0.54051,
        "termsAndConditions": "PRE_MODERNISED"
      },
      {
        "firstName": "Maria",
        "lastName": "Smith",
        "version": 1,
        "fteValue": 1.0,
        "termsAndConditions": "MODERNISED"
      }
      """
    When Trevor creates the invalid persons in the person service
    Then the last response should have a status code of 400
    Then the last response body should not be empty

    Scenario: Retrieve all Person resources

      When Trevor retrieves persons from the person service
      Then the last response body should contain
        | field | type | expectation           |
        | items | List | hasSizeGreaterThan(3) |
      When Trevor retrieves persons from the person service with
        | size | 2 |
      Then the last response body should contain
        | field | type | expectation |
        | items | List | hasSize(2)  |
      When Trevor retrieves persons from the person service with
        | size | 2 |
        | page | 1 |
      Then the 1st of the persons in the 2nd response should not be equal to the 1st of the persons in the 3rd response

    Scenario: Get specific Person by reference

      Get a single resource from the endpoint using
      its identifier
      e.g. "/resources/persons/c0a80019-8636-1410-8186-36d41fe10000"

      And the valid persons are
      """
      {
        "firstName": "John",
        "lastName": "Smith",
        "version": 1,
        "fteValue": 0.5405,
        "termsAndConditions": "PRE_MODERNISED"
      }
      """

      When Trevor creates the valid persons in the person service

      When Trevor gets the profiles 'c0a80019-8636-1410-8186-36d41fe10000' from the person service
#      Then the last response body should contain
#        | field | type | expectation |
#        | items | List | hasSize(1)  |


    Scenario: Update Person resource

      Given the initial persons are
        """
        {
          "firstName": "John",
          "lastName": "Smith",
          "version": 1,
          "fteValue": 0.5405,
          "termsAndConditions": "PRE_MODERNISED"
        }
        """

      And Trevor creates the initial persons in the person service
      When the updated persons are
      """
        {
          "firstName": "John",
          "lastName": "Smith",
          "version": 1,
          "fteValue": 1.0,
          "termsAndConditions": "MODERNISED"
        }
        """

      And Trevor updates the 1st of the persons in the last response with the updated persons
      Then the 1st of the persons in the last response should contain
        | field      | type    | expectation           |
        | firstName  | String  | isEqualTo("John")     |
        | lastName   | String  | isEqualTo("Smith")    |
        | version    | Integer | isEqualTo(1)          |
        | fteValue   | float   | isEqualTo(1.0f)       |
        | termsAndConditions   | String   | isEqualTo("MODERNISED")       |


  Scenario: Delete a Person resource

    Given the valid persons are loaded from the file 'data/persons.json'
    And Trevor creates the valid persons in the person service
    When Trevor deletes the 1st of the persons in the last response from the person service
    Then the last response should have a status code of 200