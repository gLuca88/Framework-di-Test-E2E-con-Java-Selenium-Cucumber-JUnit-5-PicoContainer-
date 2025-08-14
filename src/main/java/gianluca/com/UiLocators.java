package gianluca.com;

import org.openqa.selenium.By;

public final class UiLocators {
	
	private UiLocators() {}

	
	public static final class Common {
        // unico locator condiviso per ora
        public static final By CONTAINER_MSG = By.cssSelector(".col-sm-9 b");
        public static final By BUTTON_CONTINUE=By.xpath("//div[contains(@class,'pull-right')]//a[@data-qa='continue-button']");
    }
}
