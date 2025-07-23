Feature: Password length validation

  Scenario: Show error when password length is invalid
    Given I am on the login page
    When I test password length constraints:
      | Password                                      | ExpectedError                          |
      | averylongpasswordthatexceedsthemaximumlength | Password cannot exceed 20 characters   |
      | a                                            | Password must be at least 6 characters |
    Then each entry should trigger the correct inline error message