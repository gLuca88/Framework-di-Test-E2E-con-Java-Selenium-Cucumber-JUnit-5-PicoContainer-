package gianluca.com.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import gianluca.com.SeleniumWrapper;

public class LoginPage extends SeleniumWrapper {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	private By inputEmail = By.cssSelector("[data-qa='login-email']");
	private By inputPassword = By.cssSelector("[data-qa='login-password']");
	private By loginButton = By.cssSelector("[data-qa='login-button']");
	private By containerMexError = By.cssSelector("div.login-form p");
	private By contanerMexRegisterUser = By.cssSelector(".signup-form h2");
	private By inputNameRegister = By.cssSelector("[data-qa='signup-name']");
	private By inputEmailRegister = By.cssSelector("[data-qa='signup-email']");
	private By buttonRegister = By.cssSelector(".signup-form button");
	private By mexErrorRegister = By.cssSelector("div.signup-form p");

	public void insertCredential(String user, String password) {
		type(inputEmail, user);
		type(inputPassword, password);
		click(loginButton);
	}

	public boolean verifyMexError(String mex) {

		return mex.equals(getText(containerMexError));
	}

	public String getUrlLogin() {
		return waitForUrlToContain("login", 5);
	}

	public boolean messageVerificationRegisterUser() {
		return isElementVisible(contanerMexRegisterUser);
	}

	public String getTextContanerRegisterUser() {
		return getText(contanerMexRegisterUser);
	}

	public void registrationDataEntry(String name, String email) {
		type(inputNameRegister, name);
		type(inputEmailRegister, email);
	}

	public RegisterPage clickRegisterSubmit() {

		click(buttonRegister);
		return new RegisterPage(getDriver());
	}

	public String getTextMexErrorRegister() {

		return getText(mexErrorRegister);
	}
}
