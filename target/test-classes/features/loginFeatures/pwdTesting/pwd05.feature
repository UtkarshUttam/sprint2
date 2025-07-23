
Feature: Clipboard paste policy for password field

  Scenario: Validate paste behavior into password field
    Given I am on the login page
    When I copy and test pasting the following password:
      | Password   | ExpectedOutcome                          |
      | Pass@123   | Allowed - Field masked or login result   |
    Then the application should allow pasting and the password is masked