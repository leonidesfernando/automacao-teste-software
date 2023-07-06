package br.com.home.lab.softwaretesting.automation.cucumber;

import org.testng.Assert;

import java.util.Hashtable;
import java.util.Map;

public class ScenarioContextData {

    private Map<String, Object> context;

    public ScenarioContextData(){
        context = new Hashtable<>();
    }

    public <T> void setContext(String key, T value){
        context.put(key, value);
    }

    public <T> T get(String key){
        T value = (T)context.get(key);
        Assert.assertNotNull(value, "O valor a ser recuperado nao pode ser nulo");
        return value;
    }
}
