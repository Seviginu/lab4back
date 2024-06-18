package com.example.lab4.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MainTest {
    private static final String BASE_URL = "http://localhost:3000/";
    private static final String WEBDRIVER_PATH = "C:\\Users\\Konstantin\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String WEBDRIVER_PROPERTY = "webdriver.chrome.driver";

    private static void fillUsernameAndPassword(WebDriver driver, String username, String password) {
        WebElement loginBox = driver.findElement(By.id("username"));
        loginBox.sendKeys(username);

        WebElement passwordBox = driver.findElement(By.id("password"));
        passwordBox.sendKeys(password);
    }

    private static void login(WebDriver driver, String username, String password) {
        fillUsernameAndPassword(driver, username, password);
        WebElement loginButton = driver.findElement(By.cssSelector("[aria-label=\"Login\"]"));
        loginButton.click();

    }

    private static void waitForMainPage(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.urlContains("/main"));
    }

    private static void waitForLoginPage(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.urlMatches(BASE_URL));
    }

    private static void register(WebDriver driver, String username, String password) {
        fillUsernameAndPassword(driver, username, password);
        WebElement loginButton = driver.findElement(By.cssSelector("[aria-label=\"Register\"]"));
        loginButton.click();
    }

    private static void loginWithCorrectData(WebDriver driver) {
        login(driver, "ADMIN", "123");
    }

    private static void loginWithIncorrectPassword(WebDriver driver) {
        login(driver, "ADMIN", "1234");
    }

    private static void logout(WebDriver driver) {
        WebElement logoutButton = driver.findElement(By.cssSelector("[aria-label=\"Logout\"]"));
        logoutButton.click();
    }

    private static void checkForSuccessLogout(WebDriver driver) {
        String currentUrl = driver.getCurrentUrl();
        if(!currentUrl.equals(BASE_URL)) {
            throw new RuntimeException("Current URL is not login page");
        }
    }

    private static void checkForIncorrectPasswordDialogMessage(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("p-confirm-dialog-message"), 1));


        WebElement dialog = driver.findElement(By.className("p-confirm-dialog-message"));
        assert dialog.getText().equals("Incorrect password");
    }

    private static void setTableSortByDateAsc(WebDriver driver) {
        WebElement dateColumn = driver.findElement(By.className("p-sortable-column"));
        dateColumn.click();
        dateColumn.click();
    }

    private static void createElement(WebDriver driver, Integer xValueOrder, Double yValue, Integer rValueOrder) {
        WebElement xValueDropdown = driver.findElement(By.className("p-dropdown"));
        xValueDropdown.click();
        List<WebElement> xValueDropdownList = driver.findElements(By.className("p-dropdown-item"));
        xValueDropdownList.get(xValueOrder).click();

        WebElement yValueInputField = driver.findElement(By.cssSelector("input[placeholder=\"Select Y\"]"));
        yValueInputField.sendKeys(yValue.toString());


        List<WebElement> rValueRadioButtons = driver.findElements(By.className("p-radiobutton"));
        rValueRadioButtons.get(rValueOrder).click();

        WebElement submitButton = driver.findElement(By.cssSelector("button[aria-label=\"Submit\"]"));
        submitButton.click();
    }

    private static void checkFirstElement(WebDriver driver,
                                     Double expectedXValue,
                                     Double expectedYValue,
                                     Double expectedRValue,
                                     Boolean expectedResultValue) {
        List<WebElement> columns = driver.findElements(By.cssSelector("td"));

        if(
                Double.parseDouble(columns.get(0).getText()) != expectedXValue ||
                Double.parseDouble(columns.get(1).getText()) != expectedYValue ||
                Double.parseDouble(columns.get(2).getText()) != expectedRValue ||
                !Objects.equals(columns.get(3).getText(), expectedResultValue.toString())
        ) {
            throw new RuntimeException("Check value fail");
        }
    }

    private static void createElementAndCheck(WebDriver driver) {
        createElement(driver, 1, 2., 2);
        checkFirstElement(driver, 2., 2., 3., true);
    }


    /**
     * Принимает список тестов, вызывает каждый из тестов последовательно
     * @param tests Список тестов
     */
    private static void runTests(List<Consumer<WebDriver>> tests) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);

        for (Consumer<WebDriver> test : tests) {
            test.accept(driver);
        }
        driver.quit();
    }

    private static void loginLogoutTest() {
        try {
            runTests(List.of(
                    MainTest::loginWithCorrectData,
                    MainTest::waitForMainPage,
                    MainTest::logout,
                    MainTest::waitForLoginPage,
                    MainTest::checkForSuccessLogout));
            System.out.println("Successful login and logout test");
        }
        catch (Exception e) {
            System.err.println("FAILED login and logout test");
        }

    }

    private static void tryInvalidPasswordLoginTest() {
        try {
            runTests(List.of(
                    MainTest::loginWithIncorrectPassword,
                    MainTest::checkForIncorrectPasswordDialogMessage));
            System.out.println("Successful invalid password login test");
        }
        catch (Exception e) {
            System.err.println("FAILED invalid password login test");
            e.printStackTrace();
        }

    }

    private static void newEntityCreationTest() {
        try {
            runTests(List.of(
                    MainTest::loginWithCorrectData,
                    MainTest::waitForMainPage,
                    MainTest::setTableSortByDateAsc,
                    MainTest::createElementAndCheck));
            System.out.println("Successfull new entity creation test");
        }
        catch (Exception e) {
            System.err.println("FAILED new entity creation test");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.setProperty(WEBDRIVER_PROPERTY, WEBDRIVER_PATH);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(MainTest::loginLogoutTest);
        executorService.execute(MainTest::tryInvalidPasswordLoginTest);
        executorService.execute(MainTest::newEntityCreationTest);

        executorService.shutdown();


    }
}
