@registration
Feature: New user registration and account deletion
 
Background:
 
    Given the browser is launched
    And I navigate to http_automationexercise_com
    Then the home page should be visible successfully
@smoke
Scenario: Registration, automatic login and account deletion
    When the user clicks on the signup_login button
    Then The text Register new user! must be visible
    When The user enters his_her name and email address to register
    And the user clicks on the Register button
    Then the text ENTER ACCOUNT INFORMATION must be visible
    When the user enters the account data
    And select the Subscribe to our newsletter! checkbox
    And Select the Receive special offers from our partners! checkbox
    And the user enters the address data
    And the user clicks on the Create Account button
    Then the text ACCOUNT CREATED! must be visible
    When the user clicks the Continue button
    Then You are logged in with your username should be visible
    When the user clicks the Delete Account button
    Then the text ACCOUNT DELETED! should be visible
    And  the user clicks on the Continue button
    
