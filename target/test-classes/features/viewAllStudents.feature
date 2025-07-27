Feature: View All Students Page

  As an admin user  
  I want to verify the details of all students loaded from a CSV file  
  So that the UI data matches our expected source

---

Scenario: Validate all student details against CSV data

  Given I navigate to the View All Students page

  And student data is loaded from "studentData.csv"

  When the user validates each student by roll number

  Then all student details should match the expected values
