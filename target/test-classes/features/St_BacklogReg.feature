#Author: Aman
Feature: Backlog Field Validation

    Background:
        Given the user is on the registration page for backlog

    Scenario: Backlog count is empty
        When the user enters "" into the Backlog field
        And fills all other fields with valid data except backlog
        And clicks the register button for backlog
        Then the result for backlog should be "Provide value for backlog"

    Scenario: Backlog count is negative
        When the user enters "-2" into the Backlog field
        And fills all other fields with valid data except backlog
        And clicks the register button for backlog
        Then the result for backlog should be "Provide value for backlog"

    Scenario: Backlog count is non-numeric
        When the user enters "two" into the Backlog field
        And fills all other fields with valid data except backlog
        And clicks the register button for backlog
        Then the result for backlog should be "Provide value for backlog"

    Scenario: Backlog count exceeds allowed limit
        When the user enters "20" into the Backlog field
        And fills all other fields with valid data except backlog
        And clicks the register button for backlog
        Then the result for backlog should be "Provide value for backlog"

    Scenario: Backlog count is valid
        When the user enters "2" into the Backlog field
        And fills all other fields with valid data except backlog
        And clicks the register button for backlog
        Then the result for backlog should be "Registration was successful. The roll number generated"
