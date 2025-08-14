package gianluca.com.configuration;


import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ScenarioContext {

    private final Map<ContextKey, Object> store = new ConcurrentHashMap<>();

    public <T> void set(ContextKey key, T value) {
        Objects.requireNonNull(key, "key");
        if (value == null) store.remove(key);
        else store.put(key, value);
    }

    public <T> T get(ContextKey key, Class<T> type) {
        Objects.requireNonNull(key, "key");
        Object v = store.get(key);
        if (v == null) return null;
        if (!type.isInstance(v)) {
            throw new ClassCastException("Value for " + key + " is " +
                v.getClass().getName() + ", expected " + type.getName());
        }
        return type.cast(v);
    }

    /** Richiede il valore; se assente lancia IllegalStateException con chiavi presenti. */
    public <T> T require(ContextKey key, Class<T> type) {
        Objects.requireNonNull(key, "key");
        Object v = store.get(key);
        if (v == null) {
            throw new IllegalStateException(key + " missing in ScenarioContext. Present keys=" + store.keySet());
        }
        if (!type.isInstance(v)) {
            throw new ClassCastException("Value for " + key + " is " +
                v.getClass().getName() + ", expected " + type.getName());
        }
        return type.cast(v);
    }

    public boolean has(ContextKey key) { return store.containsKey(key); }

    public void remove(ContextKey key) { store.remove(key); }

    public void clear() { store.clear(); }
}
