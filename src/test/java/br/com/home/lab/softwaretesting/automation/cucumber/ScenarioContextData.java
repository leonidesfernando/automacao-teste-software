package br.com.home.lab.softwaretesting.automation.cucumber;


import java.util.Hashtable;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScenarioContextData {

    private final Map<String, Object> context = new Hashtable<>();

    public ScenarioContextData() {
    }

    public <T> void setContext(String key, T value) {
        context.put(key, value);
    }

    public boolean exists(String key) {
        return context.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key){
        T value = (T)context.get(key);
        assertNotNull(value, "The retrieved value cannot be null");
        return value;
    }
}
