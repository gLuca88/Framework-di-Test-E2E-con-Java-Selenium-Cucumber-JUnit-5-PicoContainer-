@signup @negative
Feature: Signup with existing email
  As a user of Automation Exercise
  I want to see an error when trying to sign up with an already registered email
  So that I am informed that the email address is already in use
  
  Background:
    Given the browser is launched
    And I navigate to http_automationexercise_com
    Then the home page should be visible successfully

  Scenario: Attempt to sign up with an existing email
    When I click on Signup_Login button
    Then I should see New User Signup! section
    When I enter name and email already registered 
    And I click on Signup_button
    Then I should see the error message Email Address already exist!