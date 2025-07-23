Feature: View password icon interaction

  Scenario: Toggle visibility icon based on field interaction
    Given I am on the login page
    When I perform the following password field interactions:
      | Action                    | Input | ExpectedOutcome                                 |
      | Click field              | -     | View icon hidden                                |
      | Type characters          | abcd  | View icon appears                               |
      | Deselect field           | -     | Cursor not in field                             |
      | Re-select field          | -     | Cursor appears, view icon visible               |
    Then the UI should reflect each expected outcome