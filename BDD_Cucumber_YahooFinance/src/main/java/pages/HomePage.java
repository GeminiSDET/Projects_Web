package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    
    @FindBy(xpath = "//input[@id='ybar-sbq']")
	public WebElement searchBox;

    @FindBy(xpath = "(//li[@role='option' and @class='modules-module_linkItemLg__bkFuZ modules-module_quoteItem__W8hI- modules-module_selectedBackground__6dnU7'])[1]")        
    private WebElement firstSuggestion;

    public void enterSearchTerm(String stockSymbol) {
    	searchBox.click();
        searchBox.sendKeys(stockSymbol);
    }

    public String getFirstSuggestionText() {
        return firstSuggestion.getText();
    }

    public void clickFirstSuggestion() {
        firstSuggestion.click();
    }
}
