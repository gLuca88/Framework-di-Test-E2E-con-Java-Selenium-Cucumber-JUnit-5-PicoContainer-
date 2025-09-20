package gianluca.com.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import gianluca.com.SeleniumWrapper;
import gianluca.com.UiLocators;

public class AllProductsPage extends SeleniumWrapper {

	public AllProductsPage(WebDriver driver) {
		super(driver);
	}

	private By containerTitle = UiLocators.Common.CONTAINER_TITLE_ALLPRODUCTS_TESTCASES_page;
	private By containerProduct = By.xpath("//a[normalize-space(text())='View Product']");

	public List<WebElement> getListProduct() {

		return getDriver().findElements(containerProduct);
	}

	public String getUrl() {
		return getCurrentUrl();
	}

	public String extractedTextTitle() {

		return getText(containerTitle);
	}

	public boolean isProductListVisible() {
		return isElementVisible(containerProduct);
	}

	public void clickOnWievProduct() {
		WebElement el = waitForElementToBeClickable(containerProduct);
		scrollIntoView(getDriver(), el);
		List<WebElement> prod = getListProduct();
		prod.get(0).click();
	}
	
	

	public boolean isDetailVisible(String fieldName) {
		By locator;
		switch (fieldName.toLowerCase()) {
		case "name":
			locator = UiLocators.Common.PD_NAME;
			break;
		case "product_price":
			locator = UiLocators.Common.PD_PRICE;
			break;
		case "availability":
			locator = UiLocators.Common.PD_AVAILABILITY;
			break;
		case "condition":
			locator = UiLocators.Common.PD_CONDITIONS;
			break;
		case "brand":
			locator = UiLocators.Common.PD_BRAND;
			break;
		default:
			throw new IllegalArgumentException("Unknown field: " + fieldName);
		}
		WebElement el = waitForElementToBeVisible(locator);
		return el.isDisplayed();
	}
}
