package com.justin.conway.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


import java.time.Duration;

public class CompareSimilarModalPage {
    private final WebDriver driver;

    @FindBy(xpath = "//div[@id='a-popover-content-2']/h1[@class='a-size-large']")
    public WebElement modalTitle;

    @FindBy(xpath = "//button[contains(concat(' ', normalize-space(@class), ' '), ' a-button-close ')]")
    public WebElement closeButton;

    public CompareSimilarModalPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getModalTitle() {
        // wait for modal to load
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(modalTitle));
        return modalTitle;
    }

    public Boolean isCompareSimilarModalClosed() {
        try {
            // wait for modal to close
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOf(modalTitle));
            return false;
        } catch(Exception ex) {
            Reporter.log(String.format("Modal is still displayed: %s", ex), true);
            return true;
        }
    }
}
