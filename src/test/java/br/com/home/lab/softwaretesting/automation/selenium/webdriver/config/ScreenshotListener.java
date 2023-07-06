package br.com.home.lab.softwaretesting.automation.selenium.webdriver.config;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.BaseSeleniumTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);

        if (!result.isSuccess()) {
            WebDriver driver = getWebDriver(result);
            final var methodName = result.getName();
            try {
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/target/surefire-reports";
                String className = result.getTestClass().getName();
                className = className.substring(1 + className.lastIndexOf("."));
                File destFile = new File(reportDirectory + "/failure_screenshots/"
                        + className + "_" + methodName + "_"
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_hh_mm_ss")) + ".png"
                );
                FileUtils.copyFile(srcFile, destFile);
                Reporter.log("a href='" +
                        destFile.getAbsolutePath() + "'> <img src='"
                        + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>"
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private WebDriver getWebDriver(ITestResult result) {
        Object[] param = {};
        final Object instance = result.getInstance();
        try {
            Method method = instance.getClass().getMethod("getWebDriver", (Class<?>[]) null);
            if (method == null) {
                Method methodIntance = instance.getClass().getMethod("getInstance", (Class<?>[]) null);
                BaseSeleniumTest baseSeleniumTest = (BaseSeleniumTest) methodIntance.invoke(instance, param);
                return baseSeleniumTest.getWebDriver();
            }
            return (WebDriver) method.invoke(instance, param);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        //return BaseSeleniumTest.getWebDriver();
    }
}
