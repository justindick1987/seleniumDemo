package com.justin.conway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ItemPage {
    private final WebDriver driver;

    @FindBy(xpath = "//span[@class='a-dropdown-label']")
    public WebElement quantityDropdown;

    @FindBy(xpath = "//input[@id='add-to-cart-button']")
    public WebElement addToCartButton;

    public ItemPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getQuantityOption(final int quantity) {
        final String xpath = String.format("//select[@id='quantity']/option[@value='%s']", quantity);
        return driver.findElement(By.xpath(xpath));
    }

    public WebElement getAddedToCartNotification() {
        final String xpath = "//div[@id='attachDisplayAddBaseAlert']" +
                "//h4[contains(concat(' ', normalize-space(@class), ' '), ' a-alert-heading ')]";
        // wait for notification to display
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return driver.findElement(By.xpath(xpath));
    }
}
