package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StockPage {
    WebDriver driver;

    @FindBy(xpath = "//span[@data-testid='qsp-price']")
    private WebElement stockPrice;

    @FindBy(xpath = "//fin-streamer[@data-field='regularMarketPreviousClose' and @class='yf-1jj98ts']")
    private WebElement previousClose;

    @FindBy(xpath ="//fin-streamer[@data-symbol='TSLA' and @data-field='regularMarketVolume' and @class='yf-1jj98ts']")
    private WebElement volume;

    public StockPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public double getStockPrice() {
    	
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    	    wait.until(ExpectedConditions.visibilityOf(stockPrice));
    	    
    	    String priceText=stockPrice.getText().replace(",", "");
    	    return priceText.isEmpty() ? 0.0 : Double.parseDouble(priceText);
    	}


    public String getPreviousClose() {
        return previousClose.getText();
    }

    public String getVolume() {
        return volume.getText();
    }
}
