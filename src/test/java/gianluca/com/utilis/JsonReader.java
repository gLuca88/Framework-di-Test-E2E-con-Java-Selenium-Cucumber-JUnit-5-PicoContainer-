package gianluca.com.utilis;

import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonReader {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static <T> List<T> readList(String jsonClasspathPath, Class<T> elementClass) {
		try (InputStream is = getResource(jsonClasspathPath)) {
			CollectionType listType = MAPPER.getTypeFactory().constructCollectionType(List.class, elementClass);
			return MAPPER.readValue(is, listType);
		} catch (Exception e) {
			throw new RuntimeException(
					"Errore nella lettura di " + jsonClasspathPath + " come List<" + elementClass.getSimpleName() + ">",
					e);
		}
	}

	public static <T> T readObject(String file, Class<T> clazz) {

		try (InputStream is = getResource(file)) {

			if (is != null) {
				return MAPPER.readValue(is, clazz);
			} else {
				throw new RuntimeException(file + ": conversione non riuscita");
			}

		} catch (Exception e) {
			throw new RuntimeException("file non trivato al path: " + file);
		}

	}

	private static InputStream getResource(String jsonClasspathPath) {
		InputStream is = JsonReader.class.getClassLoader().getResourceAsStream(jsonClasspathPath);
		if (is == null) {
			throw new IllegalArgumentException("File JSON non trovato nel classpath: " + jsonClasspathPath);
		}
		return is;
	}

}
