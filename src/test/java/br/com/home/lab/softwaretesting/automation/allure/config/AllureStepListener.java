package br.com.home.lab.softwaretesting.automation.allure.config;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.BaseSeleniumTest;
import io.qameta.allure.Attachment;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AllureStepListener implements StepLifecycleListener {

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveAllureLog(String message) {
        return message;
    }

    public void beforeStepStop(final StepResult result) {

        if (result.getStatus().equals(Status.FAILED) || result.getStatus().equals(Status.BROKEN)) {
            WebDriver driver = BaseSeleniumTest.getWebDriver();
            if (driver != null) {
                screenshot(driver);
                saveAllureLog("Step: " + result.getName());
            }
            result.setStatus(Status.FAILED);
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] screenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}

