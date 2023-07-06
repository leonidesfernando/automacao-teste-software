package br.com.home.lab.softwaretesting.automation.cucumber;

import org.testng.Assert;

import java.util.Hashtable;
import java.util.Map;

public class ScenarioContextData {

    private final Map<String, Object> context = new Hashtable<>();

    public ScenarioContextData() {
    }

    public <T> void setContext(String key, T value){
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key){
        T value = (T)context.get(key);
        Assert.assertNotNull(value, "O valor a ser recuperado nao pode ser nulo");
        return value;
    }
}
