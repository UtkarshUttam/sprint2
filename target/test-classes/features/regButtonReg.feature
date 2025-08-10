Feature: Registration Button

  Scenario: Click register without entering any data
    Given the user is on the registration page
    When the user clicks the register button without filling the form
    Then the registration result should be "Provide value for all fields"

  Scenario: Click register with valid student name
    Given the user is on the registration page
    When the user enters all the fields with valid data
    And clicks the register button
    Then the registration result should be "Registration was successful"
