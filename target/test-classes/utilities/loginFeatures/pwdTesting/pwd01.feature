Feature: Password Field Validation

  Scenario: Ensure password field masks input
    Given I open the login page
    When I click into the password field and type "Password123"
    Then the input should be masked, with the cursor inside and no visible characters
