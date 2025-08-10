# Author: Utkarsh
Feature: Email field edge‐case validation

  Background:
    Given I navigate to the Student Registration page

  Scenario: Blank email validation
    When I validate email of type "blank" using data from "src/test/resources/data/emailData.csv"
    Then email validation should be asserted

  Scenario: Missing '@' symbol validation
    When I validate email of type "missingAtSymbol" using data from "src/test/resources/data/emailData.csv"
    Then email validation should be asserted

  Scenario: Missing domain validation
    When I validate email of type "missingDomain" using data from "src/test/resources/data/emailData.csv"
    Then email validation should be asserted

  Scenario: Valid email acceptance
    When I validate email of type "valid" using data from "src/test/resources/data/emailData.csv"
    Then email validation should be asserted

  Scenario: Complex email acceptance
    When I validate email of type "complex" using data from "src/test/resources/data/emailData.csv"
    Then email validation should be asserted
