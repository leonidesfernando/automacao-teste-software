package br.com.home.lab.softwaretesting.automation.selenium.webdriver.config;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class ScreenshotListener implements TestWatcher {

    private WebDriver driver;

    public void init(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        StringJoiner fileName = new StringJoiner("-");
        fileName.add(context.getTestClass().get().getSimpleName())
                .add(context.getDisplayName().replaceAll("[()]", ""))
                .add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_hh_mm_ss")));

        captureScreenshot(fileName.toString());
    }


    public void captureScreenshot(String fileName) {
        try {
            final String path = "target/surefire-reports/";
            new File(path).mkdirs();
            try (FileOutputStream out = new FileOutputStream(path + File.separator +
                    "screenshot-" + fileName + ".png")
            ) {
                out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            }
        } catch (IOException | WebDriverException e) {
            System.out.println("screenshot failed:" + e.getMessage());
        }
    }
}
