package gianluca.com.configurationReportScreen;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotManager {
	public static Path take(WebDriver driver, Path destDir, String name) throws Exception {
		Files.createDirectories(destDir);
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Path dest = destDir.resolve(name + ".png");
		Files.move(src.toPath(), dest);
		return dest;
	}
}
