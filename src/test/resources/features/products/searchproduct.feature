@produtcs
Feature: tests products

Background:
Given the browser is launched
And I navigate to http_automationexercise_com
Then the home page should be visible successfully

@searchProduct @smoke
Scenario: search the product and verify deatils product
When I click on the Products_button
Then I should be on the ALL PRODUCTS_page
And the product list should be visible
When I click on  View Product for the first product
Then I should be on the product details page
And the product details should be visible:
  | name          |
  | product_price |
  | availability  |
  | condition     |
  | brand         |


