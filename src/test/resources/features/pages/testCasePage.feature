@pages
Feature: Test Cases Page

 
  Scenario Outline: Navigate to the Test Cases page
    Given the browser is launched
    And I navigate to http_automationexercise_com
    Then the home page should be visible successfully
    When I click on the button test cases
    Then I should be redirected to the "<pageTitle>" page successfully and "<url>" is correct

    Examples:
       |pageTitle |url                                      |
       |Test Cases|https://automationexercise.com/test_cases|
