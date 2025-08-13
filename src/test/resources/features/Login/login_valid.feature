@loginTests
Feature: Login with valid credentials

  Background:
    Given the user opens the browser and navigates to the homepage
    When the user clicks on the signup_login button

  @login
  Scenario: User logs in with the correct email and password
    And the user enters valid email and password and clicks the login button
    Then  the user should see the logout button and delete account button

  Scenario Outline: User tries to log in with incorrect credentials
    And the user enters "<email>" and "<password>" as invalid credentials
    Then the user should see the error message "<errorMessage>"

    Examples:@loginTests
      | email                       | password                   | errorMessage                         |
      | automationtest@mail.com     | passwordErrata             | Your email or password is incorrect! |
      | emailnonregistrata@fake.com | password123                | Your email or password is incorrect! |
      | utente@sbagliato            | 123456@                    | Your email or password is incorrect! |
      | automationtest@mail.com     | 123                        | Your email or password is incorrect! |
      | automationtes@mail.com      | unoduetrequattrocinquesei@ | Your email or password is incorrect! |

  @login
  Scenario: User logs out successfully
    And the user enters valid email and password and clicks the login button
    And the user clicks on the logout button
    Then the user should be redirected to the login page and the user info should not be visible