package tests;

import baseEntities.BaseTest;
import com.google.gson.Gson;
import core.ReadProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import models.Book;
import models.Books;
import models.User;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BookStorePage;
import pages.LoginPage;
import utils.Endpoints;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Tests extends BaseTest {
    public static Logger logger = LogManager.getLogger(Test.class);

    @BeforeMethod
    public void setupApiTest() {
        logger.info("Setup RestAssured");
        RestAssured.baseURI = ReadProperties.getApiUrl();

        logger.info("Setup request Object");
        RestAssured.requestSpecification = given()
                .header(HTTP.CONTENT_TYPE, ContentType.JSON);
    }

    @Test
    public void loginTest() {
        logger.info("Start login test");
        BookStorePage bookStorePage = new BookStorePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        User user = User.builder()
                .userName(ReadProperties.getUsername())
                .password(ReadProperties.getPassword())
                .build();

        given()
                .body(user, ObjectMapperType.GSON)
                .log().body()
                .when()
                .post(Endpoints.CREATE_ACCOUNT)
                .then().log().body()
                .statusCode(HttpStatus.SC_CREATED);

        bookStorePage.clickLoginButton();
        Assert.assertTrue(loginPage.isPageOpened(), "Login page wasn't opened");
        loginPage.enterUserName(user.getUserName());
        loginPage.enterPassword(user.getPassword());
        loginPage.clickLoginButton();

        Assert.assertTrue(bookStorePage.isLogOutButtonPresent(), "User didn't log in");
    }

    @Test
    public void comparisonOfBookListsTest() {
        logger.info("Start comparative of book lists test");

        BookStorePage bookStorePage = new BookStorePage(driver);
        List booksListWeb = bookStorePage.getBooksList();

        Gson gson = new Gson();
        Response response = given()
                .get(Endpoints.GET_BOOKS_LIST);

        Books books = gson.fromJson(response.getBody().asString(), Books.class);
        List<Book> fullBookList = books.getBooks();

        List booksListApi = new ArrayList<>();
        for (int i = 0; i < fullBookList.size(); i++) {
            booksListApi.add(i, fullBookList.get(i).getTitle());
        }
        Assert.assertEquals(booksListWeb, booksListApi, "Book lists don't match");
    }
}

