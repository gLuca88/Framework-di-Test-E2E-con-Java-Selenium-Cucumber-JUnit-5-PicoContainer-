package gianluca.com.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import gianluca.com.SeleniumWrapper;

public class LoginPage extends SeleniumWrapper {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	private By inputEmail = By.xpath("//div[@class='login-form']//input[@name='email']");
	private By inputPassword = By.xpath("//div[@class='login-form']//input[@name='password']");
	private By loginButton = By.xpath("//div[@class='login-form']//button[@class='btn btn-default']");
	private By containerMexError = By.cssSelector("div.login-form p");
	private By contanerMexRegisterUser = By.cssSelector(".signup-form h2");
	private By inputNameRegister = By.xpath("//div[@class='signup-form']//input[@name='name']");
	private By inputEmailRegister = By.xpath("//div[@class='signup-form']//input[@name='email']");
	private By buttonRegister = By.cssSelector(".signup-form button");
	private By mexErrorRegister = By.cssSelector("div.signup-form p");

	public void insertCredential(String user, String password) {
		type(inputEmail, user);
		type(inputPassword, password);
		click(loginButton);
	}

	public boolean verifyMexError(String mex) {

		return mex.equals(extractTextElement(containerMexError));
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
		RegisterPage registration = new RegisterPage(getDriver());
		click(buttonRegister);
		return registration;
	}

	public String getTextMexErrorRegister() {

		return getText(mexErrorRegister);
	}
}
