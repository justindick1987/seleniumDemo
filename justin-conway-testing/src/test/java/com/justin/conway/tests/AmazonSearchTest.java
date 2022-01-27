package com.justin.conway.tests;

import com.justin.conway.TestEnvironment;
import com.justin.conway.pages.AmazonMainPage;
import com.justin.conway.pages.CartPage;
import com.justin.conway.pages.ItemPage;
import com.justin.conway.pages.CompareSimilarModalPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.Reporter;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


public class AmazonSearchTest{
    private WebDriver driver;
    private AmazonMainPage amazonMainPage;
    private ItemPage itemPage;
    private CartPage cartPage;
    private CompareSimilarModalPage compareSimilarModalPage;

    private String amazonAddress = "https://www.amazon.com";

    @BeforeSuite(alwaysRun = true)
    public void getEnvironment() throws IOException {
        String configFileName = "./%s.properties";
        String EnvironmentName = System.getProperty("TestEnvironment");
        Reporter.log(String.format("Test Environment: %s", EnvironmentName), true);

        configFileName = String.format(configFileName, EnvironmentName);
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(configFileName)));
        String url = properties.getProperty("url");
        if (url != null) {
            Reporter.log("URL: " + url, true);
            amazonAddress = url;
        }
    }


    @BeforeMethod(alwaysRun = true)
    public void setUp()  {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        amazonMainPage = new AmazonMainPage(driver);
        itemPage = new ItemPage(driver);
        cartPage = new CartPage(driver);
        compareSimilarModalPage = new CompareSimilarModalPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    @Test(description = "Search for magnifying glass, add 7 to cart, and compare to similar items.",
    groups = TestEnvironment.PROD)
    public void searchForMagnifyingGlassTest(){

        final String searchText = "Magnifying Glass";
        final String searchCategory = "Industrial & Scientific";
        int quantity = 7;

        Reporter.log(String.format("Go to %s", amazonAddress), true);
        driver.get(amazonAddress);

        Reporter.log("Enter 'Magnifying Glass' into the search input field", true);
        amazonMainPage.searchInputField.sendKeys(searchText);

        Reporter.log("Select 'Industrial & Scientific' category from the dropdown", true);
        amazonMainPage.searchCategoryInput.click();
        amazonMainPage.getSearchCategoryInput(searchCategory).click();

        Reporter.log("Select search", true);
        amazonMainPage.searchButton.click();

        Reporter.log("Verify search bar and category selected are still populated", true);
        Assert.assertEquals(amazonMainPage.searchInputField.getAttribute("value"), searchText);
        Assert.assertEquals(amazonMainPage.getSearchCategoryInput(searchCategory).getAttribute("selected"), "true");


        Reporter.log("Select 'Free Shipping by Amazon' checkbox", true);
        amazonMainPage.freeShippingByAmazonCheckbox.click();

        Reporter.log("Verify all items listed for 'Magnifying Glass' are for prime delivery only", true);
        int resultItemCount = amazonMainPage.getSearchResultItems().size();
        int primeResultItemCount = amazonMainPage.getPrimeResultItems().size();
        Assert.assertEquals(resultItemCount, primeResultItemCount);

        Reporter.log("Select a 'Magnifying Glass' to purchase", true);
        amazonMainPage.getResultItem(2).click();

        Reporter.log("Add QTY of '7' Magnifying Glass to cart", true);
        itemPage.quantityDropdown.click();
        itemPage.getQuantityOption(quantity).click();
        itemPage.addToCartButton.click();

        Reporter.log("Verify ‘Added to Cart’ displays", true);
        Assert.assertEquals(itemPage.getAddedToCartNotification().getText(), "Added to Cart");

        Reporter.log("Select ‘Cart’", true);
        // Handle scenario when side panel is not displayed
        if (amazonMainPage.sidePanelCartButton.isDisplayed()) {
            amazonMainPage.sidePanelCartButton.click();
        } else {
            amazonMainPage.cartLink.click();
        }

        Reporter.log("Verify correct quantity of items is in cart", true);
        Assert.assertTrue(cartPage.subtotalItems.getText().contains(String.format("Subtotal (%s items)", quantity)));

        Reporter.log("Select ‘Compare with similar items’", true);
        cartPage.compareWithSimilarItemsLink.click();

        Reporter.log("Verify pop up box of similar items displays", true);
        Assert.assertEquals(compareSimilarModalPage.getModalTitle().getText(), "Compare with similar items");

        Reporter.log("Close pop up box", true);
        compareSimilarModalPage.closeButton.click();

        Reporter.log("Verify pop up box of similar items is not displayed", true);
        Assert.assertFalse(compareSimilarModalPage.isCompareSimilarModalClosed());
    }

    @Test(description = "Test designed to fail due to attempting to select non-existent search category: 'Camping'",
            groups = TestEnvironment.PROD)
    public void failingSearchTest(){
        final String searchText = "Tent";
        final String searchCategory = "Camping";

        Reporter.log(String.format("Go to %s", amazonAddress), true);
        driver.get(amazonAddress);

        Reporter.log("Enter 'Tent' into the search input field", true);
        amazonMainPage.searchInputField.sendKeys(searchText);

        Reporter.log("Attempt to select non-existent 'Camping' category from the dropdown. " +
                "THIS WILL CAUSE TEST TO FAIL", true);
        amazonMainPage.searchCategoryInput.click();
        amazonMainPage.getSearchCategoryInput(searchCategory).click();
    }
}
