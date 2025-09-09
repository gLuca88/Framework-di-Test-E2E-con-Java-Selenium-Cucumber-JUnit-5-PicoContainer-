package gianluca.com.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import gianluca.com.SeleniumWrapper;

public class TestCasePage extends SeleniumWrapper {

	public TestCasePage(WebDriver driver) {
		super(driver);
	}

	private By titlePage = By.cssSelector(".title");

	public String getTextTitle() {

		return getText(titlePage);
	}

}
