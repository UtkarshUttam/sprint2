Feature: Login error handling

  Scenario: Attempt login with blank password field
    Given I open the login page
    When I clear the password field and click the "Sign in" button
    Then an inline error "Invalid username/password" should be displayed