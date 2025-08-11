@purchase @login @products
Feature: Purchase via product search (data from JSON)
  As a logged-in user
  I want to search a product and complete the purchase
  So that I can place an order successfully

  Background:
    Given the user opens the browser and navigates to the homepage
    When the user clicks on the signup_login button
    And the user enters valid email and password and clicks the login button

  Scenario: Successful purchase of a product found via search
    Given the user is on the Products page
    When the user searches for a product
    Then the search results contain the searched product
    And the user adds the product to the cart
    Then the cart contains the selected product
    When the user proceeds to checkout
    Then the delivery address is correct
    And the user confirms the order
    When the user enters the payment card details
    Then the confirmation message is present
