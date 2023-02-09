Feature: Person

  Scenario: Create Person

    Given Trevor is a user
    And the valid persons are
      """
      {
        "tenantId": "b7e813a2-bb28-11ec-8422-0242ac120002",
        "firstName": "John",
        "lastName": "Smith",
        "version": 1,
        "fteValue": 0.5,
        "termsAndConditions": "PRE_MODERNISED"
      }
      """
    And the invalid persons are
      """
      {
        "tenantId": "b7e813a2-bb28-11ec-8422-0242ac120002",
        "firstName": "John",
        "lastName": "Smith",
        "version": 1,
        "fteValue": 0.5,
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
      | fteValue   | float   | isEqualTo(0.5f)       |
      | termsAndConditions   | String   | isEqualTo("PRE_MODERNISED")       |

    When Trevor creates the invalid persons in the person service
    Then the last response should have a status code of 400