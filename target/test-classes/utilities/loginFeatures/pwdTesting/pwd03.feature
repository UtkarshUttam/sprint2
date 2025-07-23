Feature: Password field input validation

  Scenario: Accept various password formats
    Given I am on the login page
    When I test the following passwords in the password field:
      | Password         |
      | Password123!     |
      | password         |
      | PASSWORD         |
      | 123456           |
      | !@#$%^           |
    Then all inputs should be masked and accepted without validation error