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

}
