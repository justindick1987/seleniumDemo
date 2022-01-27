package com.justin.conway.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

    @FindBy(xpath = "//span[@id='sc-subtotal-label-buybox']")
    public WebElement subtotalItems;

    @FindBy(xpath = "//span[@data-action='compare']//input")
    public WebElement compareWithSimilarItemsLink;

    public CartPage(final WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
