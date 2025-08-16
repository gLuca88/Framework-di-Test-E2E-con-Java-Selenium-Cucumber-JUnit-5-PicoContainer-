@contact @ui
Feature: Contact Us form submission on Automation Exercise
  As a visitor
  I want to submit the contact form with an attachment
  So that I receive a success confirmation

  Background:
    Given the browser is launched
    And I navigate to http_automationexercise_com
    Then the home page should be visible successfully

  Scenario Outline: Submit the contact form with file upload
    When I click on Contact Us
    Then GET IN TOUCH is visible
    When I enter name name, email , subject , and message
    And I upload the file filePath
    And I click Submit
    And I accept the confirmation dialog
    Then the success message Success! Your details have been submitted successfully. is visible
    When I click on Home
    Then the home page should be visible successfully
