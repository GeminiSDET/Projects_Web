package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='ybar-sbq']")
    public WebElement searchBox;

    @FindBy(css ="(//li[@role='option' and contains(., 'TSLA')])[1]")
    public WebElement firstSuggestion;

    public void enterSearchTerm(String stockSymbol) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        searchBox.click();
        searchBox.sendKeys(stockSymbol);
    }

    public String getFirstSuggestionText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOf(firstSuggestion));
        return firstSuggestion.getText();
    }

    public void clickFirstSuggestion() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(firstSuggestion));
        firstSuggestion.click();
    }
}