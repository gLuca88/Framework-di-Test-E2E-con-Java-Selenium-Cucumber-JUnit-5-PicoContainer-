package gianluca.com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

	public static WebDriver createBrowser(String browserName) {

		if (browserName == null || browserName.isEmpty()) {
			throw new IllegalArgumentException("name of browser cannot be null or empty");
		}

		switch (browserName) {

		case "chrome":
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver();
		case "edge":
			return new EdgeDriver();
		case "firefox":
			return new FirefoxDriver();

		default:
			throw new IllegalArgumentException("the browser is not supported-->" + browserName);

		}

	}

}
