package gianluca.com;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWrapper {

	private WebDriver driver;
	private WebDriverWait wait;

	public SeleniumWrapper(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// === Wait Methods ===
	public WebElement waitForElementToBeVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementToBeClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public boolean waitForTitleToContain(String partialTitle) {
		try {
			return wait.until(ExpectedConditions.titleContains(partialTitle));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void navigateTo(String url) {
		driver.get(url);
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void click(By locator) {
		waitForElementToBeClickable(locator).click();
	}

	public void type(By locator, String text) {
		WebElement element = waitForElementToBeVisible(locator);
		element.clear();
		element.sendKeys(text);
	}

	public String getText(By locator) {
		return waitForElementToBeVisible(locator).getText();
	}

	public boolean isElementVisible(By locator) {
		try {
			return waitForElementToBeVisible(locator).isDisplayed();
		} catch (TimeoutException e) {
			return false;
		}
	}

	// Verifica se l'elemento è presente nel DOM (anche se non visibile)
	public boolean isElementPresent(By locator) {
		List<WebElement> elements = driver.findElements(locator);
		return !elements.isEmpty();
	}

	// Verifica se l'elemento è visibile e cliccabile
	public boolean isElementVisibleAndClickable(By locator) {
		try {
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			return element.isDisplayed() && element.isEnabled();
		} catch (TimeoutException | NoSuchElementException e) {
			return false;
		}
	}

	public String waitForUrlToContain(String partialUrl, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		wait.until(ExpectedConditions.urlContains(partialUrl));
		return driver.getCurrentUrl();
	}

	public void scrollIntoView(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
				element);
	}

	public List<WebElement> waitForElementsPresent(By locator) {
		try {
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		} catch (TimeoutException e) {
			return Collections.emptyList();
		}
	}

	public void acceptAlert() {
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		System.out.println("Alert text: " + alert.getText());
		alert.accept();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

}
