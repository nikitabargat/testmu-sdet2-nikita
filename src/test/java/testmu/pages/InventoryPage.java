package testmu.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage {

    private WebDriver driver;

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".inventory_list")
    private WebElement inventoryList;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public boolean isInventoryListDisplayed() {
        return inventoryList.isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
