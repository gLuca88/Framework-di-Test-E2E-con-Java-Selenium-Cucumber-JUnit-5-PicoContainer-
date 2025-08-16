package gianluca.com.utilis;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathUtils {
	public static String toAbsolutePath(String filePath) {
		if (filePath == null || filePath.isBlank()) {
			throw new IllegalArgumentException("filePath is null or vuoto");
		}

		Path path = Paths.get(filePath);
		if (!path.isAbsolute()) {
			path = path.toAbsolutePath().normalize();
		}
		return path.toString();
	}
}
