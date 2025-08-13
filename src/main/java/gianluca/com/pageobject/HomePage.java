package gianluca.com.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import gianluca.com.SeleniumWrapper;
import gianluca.com.UiLocators;

public class HomePage extends SeleniumWrapper {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	private final String url = "https://automationexercise.com/";
	private final By buttonLogin = By.xpath("//a[text()=' Signup / Login']");
	private final By buttonLogOut = By.xpath("//a[text()=' Logout']");
	private final By buttonDeleteAccount = By.xpath("//a[text()=' Delete Account']");
	private final By buttonAcconsento = By.xpath("//button[@class='fc-button fc-cta-consent fc-primary-button']");
	private final By userLoggerContainer = By.xpath("(//div[@class='shop-menu pull-right']//a)[10]");
	private final By containerLogo = By.cssSelector(".logo");
	private final By conatinerMex = UiLocators.Common.CONTAINER_MSG;
	private final By continueAfterDelete = UiLocators.Common.BUTTON_CONTINUE;

	public HomePage navigateHomePage() {
		navigateTo(url);
		return this; // restituisce se stesso per chaining
	}

	public LoginPage clickButtonLogin() {
		LoginPage login = new LoginPage(getDriver());
		click(buttonLogin);
		return login;
	}

	public boolean verifyButtonLogOut() {

		return isElementVisibleAndClickable(buttonLogOut);
	}

	public boolean verifyButtonDeleteAccount() {
		return isElementVisibleAndClickable(buttonDeleteAccount);
	}

	public void clickAccept() {
		if (isElementPresent(buttonAcconsento)) {
			click(buttonAcconsento);
		}
	}

	public boolean verifyContainerLoggerVisibility() {

		return isElementVisible(userLoggerContainer);
	}

	public void clickButtonLogOut() {
		click(buttonLogOut);
	}

	public boolean verifyLogoPageContainer() {
		return isElementVisibleAndClickable(containerLogo);
	}

	public String getTextLoggedInAs() {
		return getText(userLoggerContainer);
	}

	public void clickDeleteButton() {
		click(buttonDeleteAccount);
	}

	public String getTextMex() {
		return getText(conatinerMex);
	}

	public void clickButtonContinue() {
		click(continueAfterDelete);
	}
}
