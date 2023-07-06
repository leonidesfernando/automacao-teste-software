package br.com.home.lab.softwaretesting.automation.cucumber;


import java.util.Hashtable;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScenarioContextData {

    private Map<String, Object> context;

    public ScenarioContextData(){
        context = new Hashtable<>();
    }

    public <T> void setContext(String key, T value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key){
        T value = (T)context.get(key);
        assertNotNull(value, "The retrieved value cannot be null");
        return value;
    }
}
