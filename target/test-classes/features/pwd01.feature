Feature: Password field edge‐case validation

  Background:
    Given I open the login page

  Scenario: Blank password validation
    When I validate password of type "blank" using data from "src/test/resources/data/passwordData.csv"
    Then validation should be asserted

  Scenario: Short password validation
    When I validate password of type "short" using data from "src/test/resources/data/passwordData.csv"
    Then validation should be asserted

  Scenario: Long password validation
    When I validate password of type "long" using data from "src/test/resources/data/passwordData.csv"
    Then validation should be asserted

  Scenario: Valid password acceptance
    When I validate password of type "valid" using data from "src/test/resources/data/passwordData.csv"
    Then validation should be asserted

  Scenario: Special characters allowed
    When I validate password of type "special" using data from "src/test/resources/data/passwordData.csv"
    Then validation should be asserted
