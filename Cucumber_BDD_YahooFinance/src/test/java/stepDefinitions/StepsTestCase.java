package stepDefinitions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import config.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import pages.StockPage;
import utils.ExtentManager;

public class StepsTestCase {
    public WebDriver driver;
    public HomePage homePage;
    public StockPage stockPage;
    public ExtentReports extent;
    public ExtentTest test;
    public WebDriverWait wait;

   @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(90));//increase time to 60
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("baseURL"));
        homePage = new HomePage(driver);
        stockPage = new StockPage(driver);
        extent = ExtentManager.getInstance();
        
    }

    @Given("User is on Yahoo Finance homepage")
    public void user_is_on_homepage() {
    	System.out.println("Current url:" + driver.getCurrentUrl());
    	System.out.println("Page title:" + driver.getTitle());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='ybar-inner-wrap' and @class='_yb_1532fab ']"))); //check the xpath
        Assert.assertTrue(driver.getTitle().contains("Yahoo Finance"));
    }

    @When("User searches for {string}")
    public void user_searches_for_stock(String stockSymbol) {
        test = ExtentManager.createTest("Search for " + stockSymbol);
        homePage.enterSearchTerm(stockSymbol);
    }

    @Then("First autosuggest entry should be {string}")
    public void verify_autosuggest(String expectedText) {
        Assert.assertTrue(homePage.getFirstSuggestionText().equalsIgnoreCase(expectedText));
        ExtentManager.getTest().pass("Autosuggested matched: " + expectedText);
    }
    
    @When("User clicks on the first autosuggested entry")
    public void click_first_entry() {
        homePage.clickFirstSuggestion();
    }

    @Then("Stock price should be greater than {double}")
    public void verify_stock_price(double minPrice) {
        double price = stockPage.getStockPrice();
        Assert.assertTrue(price > minPrice, "Stock price is below threshold.");
        ExtentManager.getTest().pass("Stock price :" + price);
    }

    @Then("Capture additional stock details")
    public void capture_stock_details() {
        String prevClose = stockPage.getPreviousClose();
        String volume = stockPage.getVolume();
        System.out.println("Previous Close: " + prevClose);
        System.out.println("Volume: " + volume);
        ExtentManager.getTest().pass("Additional stock details -Previous Close: "+ prevClose + "Volume: " + volume);
    }

    @When("User clicks on the search box but does not enter any text")
    public void user_clicks_on_the_search_box_but_does_not_enter_any_text() {
        homePage.searchBox.click();
    }
    
    @Then("No autosuggest should be displayed")
    public void verify_no_autosuggest() {
        boolean isNotDisplayed = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//ul[contains(@class, 'suggestions-list')]")));
        Assert.assertTrue(isNotDisplayed, "Autosuggest is unexpectedly displayed.");
       ExtentManager.getTest().pass("No text displayed for empty search"); 
    }

    @Then("Search results should show {string}")
    public void verify_invalid_search_result(String expectedMessage) {
        WebElement noResultsMessage = driver.findElement(By.xpath("//div[contains(text(),'No results found')]"));
        Assert.assertEquals(noResultsMessage.getText(), expectedMessage);
        ExtentManager.getTest().pass("Verified search results");
    }

    @When("The network is slow")
    public void simulate_slow_network() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Then("The test should wait for results up to {int} seconds")
    public void wait_for_results(int timeout) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class,'suggestions-list')]/li[1]")));
        ExtentManager.getTest().pass("Verified search result loaded");
    }

    @When("The search feature is disabled")
    public void disable_search_feature() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].disabled = true;", homePage.searchBox);
    }

    @Then("The test should log an error message")
    public void log_error_message() {
    	ExtentManager.getTest().fail("Search feature is disabled");
    }

   @After
    public void tearDown() {
        if(driver !=null) {
        	driver.quit();
        }
        extent.flush();
    }
}