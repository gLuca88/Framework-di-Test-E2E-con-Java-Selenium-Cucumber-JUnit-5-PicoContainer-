package gianluca.com.configurationReportScreen;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PathManager {
	private static final String base = "target/reports/"
			+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

	public static Path baseDir() {
		return Paths.get(base);
	}

	public static Path reportHtml() {
		return baseDir().resolve("ExtentReport.html");
	}

	public static Path logsDir() {
		return baseDir().resolve("logs");
	}

	public static Path screenshotsDir() {
		return baseDir().resolve("screenshots");
	}

	public static Path scenarioDir(String safeName) {
		return screenshotsDir().resolve(safeName);
	}
}