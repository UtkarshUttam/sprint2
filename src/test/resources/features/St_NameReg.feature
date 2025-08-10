# Author: Sambhram
Feature: Student Name Field Validation

  Background:
    Given the user is on the registration page

  Scenario: Student name is empty
    When the user enters "" into the student name field
    And fills all other fields with valid data
    And clicks the register button
    Then the name validation result should be "Provide value for student name"

  Scenario: Student name has only 1 character
    When the user enters "A" into the student name field
    And fills all other fields with valid data
    And clicks the register button
    Then the name validation result should be "Minimum 2 characters required"

  Scenario: Student name has 2 characters
    When the user enters "Ab" into the student name field
    And fills all other fields with valid data
    And clicks the register button
    Then the name validation result should be "Registration was successful. The roll number generated"

  Scenario: Student name contains numbers
    When the user enters "John123" into the student name field
    And fills all other fields with valid data
    And clicks the register button
    Then the name validation result should be "No numbers allowed in student name"

  Scenario: Student name contains special characters
    When the user enters "@John!" into the student name field
    And fills all other fields with valid data
    And clicks the register button
    Then the name validation result should be "No special characters allowed"

  Scenario: Student name exceeds 30 characters
    When the user enters "Johnathan Edward Christopher Maxwell" into the student name field
    And fills all other fields with valid data
    And clicks the register button
    Then the name validation result should be "Maximum 30 characters allowed"

  Scenario: Student name is valid
    When the user enters "John Smith" into the student name field
    And fills all other fields with valid data
    And clicks the register button
    Then the name validation result should be "Registration was successful. The roll number generated"