package baseEntities;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isPageOpened(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException nsex) {
            return false;
        }

    }
}
