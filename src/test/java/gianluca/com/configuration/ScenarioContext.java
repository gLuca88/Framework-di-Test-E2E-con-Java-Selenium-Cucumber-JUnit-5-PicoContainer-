package gianluca.com.configuration;

import java.util.EnumMap;
import java.util.Map;

public class ScenarioContext{
	private final Map<ContextKey, Object> data = new EnumMap<>(ContextKey.class);

	public <T> void set(ContextKey key, T value) {
		data.put(key, value);
	}

	public <T> T get(ContextKey key, Class<T> type) {
		Object v = data.get(key);
		return type.cast(v);
	}

	public boolean has(ContextKey key) {
		return data.containsKey(key);
	}

	public void clear() {
		data.clear();
	}
}
