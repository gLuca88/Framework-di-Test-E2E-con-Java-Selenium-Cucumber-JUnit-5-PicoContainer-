package gianluca.com.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import gianluca.com.SeleniumWrapper;
import gianluca.com.UiLocators;

public class TestCasePage extends SeleniumWrapper {

	public TestCasePage(WebDriver driver) {
		super(driver);
	}

	private By titlePage = UiLocators.Common.CONTAINER_TITLE_ALLPRODUCTS_TESTCASES_page;

	public String getTextTitle() {

		return getText(titlePage);
	}

	public String urlPage() {
		return getCurrentUrl();
	}

}
