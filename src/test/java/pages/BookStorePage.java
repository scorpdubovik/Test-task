package pages;

import baseEntities.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Waits;

import java.util.ArrayList;
import java.util.List;

public class BookStorePage extends BasePage {
    public static Logger logger = LogManager.getLogger(BookStorePage.class);

    private By PAGE_OPENED_IDENTIFIER = By.id("searchBox-wrapper");
    private By loginButtonSelector = By.id("login");
    private By logOutButtonSelector = By.id("submit");
    private By bookTitleSelector = By.className("action-buttons");

    public BookStorePage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        return super.isPageOpened(PAGE_OPENED_IDENTIFIER);
    }

    private WebElement loginButton() {
        return driver.findElement(loginButtonSelector);
    }
    private List<WebElement> bookTitle() {return driver.findElements(bookTitleSelector);}

    public void clickLoginButton() {
        loginButton().click();
    }

    public boolean isLogOutButtonPresent() {
        Waits waits = new Waits(driver);
        try {
            waits.waitForVisibility(logOutButtonSelector);
            return true;
        } catch (NoSuchElementException Ex) {
            logger.error("LogOut Button didn't appear");
            return false;
        }
    }

    public List getBooksList() {
        List booksList = new ArrayList<>();
        for (int i = 0; i < bookTitle().size(); i++) {
            booksList.add(i, bookTitle().get(i).getText());
        }
        return booksList;
    }
}

