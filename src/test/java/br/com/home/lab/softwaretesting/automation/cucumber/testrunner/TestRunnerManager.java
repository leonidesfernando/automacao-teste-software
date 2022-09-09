package br.com.home.lab.softwaretesting.automation.cucumber.testrunner;


import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")

public class TestRunnerManager {
}
