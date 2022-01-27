package com.justin.conway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class AmazonMainPage {
    private final WebDriver driver;

    @FindBy(xpath = "//input[@id='twotabsearchtextbox']")
    public WebElement searchInputField;

    @FindBy(xpath = "//input[@id='nav-search-submit-button']")
    public WebElement searchButton;

    @FindBy(xpath = "//select[@id='searchDropdownBox']")
    public WebElement searchCategoryInput;

    @FindBy(xpath = "//form[@id='attach-view-cart-button-form']//input")
    public WebElement sidePanelCartButton;

    @FindBy(xpath = "//a[@id='nav-cart']")
    public WebElement cartLink;

    @FindBy(xpath = "//span[text() = 'Free Shipping by Amazon']")
    public WebElement freeShippingByAmazonCheckbox;

    public AmazonMainPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getSearchCategoryInput(final String category) {
        final String xpath = String.format("//select[@id='searchDropdownBox']//option[text() = '%s']", category);
        return driver.findElement(By.xpath(xpath));
    }

    public List<WebElement> getSearchResultItems() {
        return driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
    }

    public List<WebElement> getPrimeResultItems() {
        return this.driver.findElements(By.xpath("//div[@data-component-type='s-search-result']" +
                "//i[contains(concat(' ', normalize-space(@class), ' '), ' a-icon-prime ')]"));
    }

    public WebElement getResultItem(int index) {
        return driver.findElement(By.xpath(String.format("//div[@data-component-type='s-search-result'][%s]//h2/a", index)));
    }

}
