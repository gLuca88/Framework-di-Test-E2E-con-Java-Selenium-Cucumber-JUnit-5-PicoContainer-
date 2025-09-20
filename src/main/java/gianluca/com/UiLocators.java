package gianluca.com;

import org.openqa.selenium.By;

public final class UiLocators {

	private UiLocators() {
	}

	public static final class Common {
		// unico locator condiviso per ora
		public static final By CONTAINER_MSG = By.cssSelector(".col-sm-9 b");
		public static final By BUTTON_CONTINUE = By
				.xpath("//div[contains(@class,'pull-right')]//a[@data-qa='continue-button']");
		public static final By CONTAINER_TITLE_ALLPRODUCTS_TESTCASES_page = By.cssSelector(".title");

		// scheda prodotto
		public static final By PD_NAME = By.xpath("//div[@class='product-information']/h2");
		public static final By PD_PRICE = By.xpath(
				"//div[contains(@class,'product-information')]//span[contains(normalize-space(.),'Rs.')][not(descendant::span)]");
		public static final By PD_AVAILABILITY = By.xpath(
				"//div[contains(@class,'product-information')]//b[contains(normalize-space(.),'Availability:')]");
		public static final By PD_CONDITIONS = By
				.xpath("//div[contains(@class,'')]//b[contains(normalize-space(.),'Condition:')]");

		public static final By PD_BRAND = By.xpath("//div[contains(@class,'product-information')]//b[contains(normalize-space(.),'Brand:')]");

	}
}
