#Author: Aman
Feature: CGPA Field Validation

  Background:
    Given the user is on the registration page

  Scenario: CGPA is empty
    When the user enters "" into the CGPA field
    And fills all other fields with valid data except cgpa
    And clicks the register button
    Then the cgpa validation result should be "Provide value for cgpa"

  Scenario: CGPA is less than 0
    When the user enters "-1" into the CGPA field
    And fills all other fields with valid data except cgpa
    And clicks the register button
    Then the cgpa validation result should be "Provide value for cgpa"

  Scenario: CGPA is greater than 10
    When the user enters "10.5" into the CGPA field
    And fills all other fields with valid data except cgpa
    And clicks the register button
    Then the cgpa validation result should be "Provide value for cgpa"

  Scenario: CGPA has alphabetic characters
    When the user enters "nine" into the CGPA field
    And fills all other fields with valid data except cgpa
    And clicks the register button
    Then the cgpa validation result should be "Provide value for cgpa"

  Scenario: CGPA has special characters
    When the user enters "9.0*" into the CGPA field
    And fills all other fields with valid data except cgpa
    And clicks the register button
    Then the cgpa validation result should be "Provide value for cgpa"

  Scenario: CGPA is valid with one decimal
    When the user enters "7.5" into the CGPA field
    And fills all other fields with valid data except cgpa
    And clicks the register button
    Then the cgpa validation result should be "Registration was successful. The roll number generated"

  Scenario: CGPA is valid as integer
    When the user enters "8" into the CGPA field
    And fills all other fields with valid data except cgpa
    And clicks the register button
    Then the cgpa validation result should be "Registration was successful. The roll number generated"