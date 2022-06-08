package com.sevenbridges;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.List;

public class Task02_03_04_AmazonGuitar {


    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");
    }

//    @AfterMethod
//    public void tearDown() {
//        driver.close();
//    }

    @Test
    public void testAveragePrice() {
        // Search for Guitar
        WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
        searchInput.sendKeys("Guitar"+ Keys.ENTER);

        // Wait till all items are loaded
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy( By.xpath("//span[@data-a-size='l']//span[@class='a-price-whole']") ));

        // Make sure user on page 1
        try{
            WebElement pageOneLink = driver.findElement(By.xpath("//a[@aria-label='Go to page 1']"));
            pageOneLink.click();
        }catch (Exception e){
            System.out.println("User on page one");
        }

        // Calculate average price from 1st page
        List<WebElement> priceWhole = driver.findElements(By.xpath("//span[@data-a-size='l']//span[@class='a-price-whole']"));
        List<WebElement> priceFraction = driver.findElements(By.xpath("//span[@data-a-size='l']//span[@class='a-price-fraction']"));
        double totalPrice=0;
        int totalElements=priceWhole.size();
        for (int i = 0; i < priceWhole.size(); i++) {
            if ( !(priceWhole.get(i).getText().isEmpty() && priceFraction.get(i).getText().isEmpty()) ){
                totalPrice += Double.parseDouble(
                        priceWhole.get(i).getText().replaceAll("[^0-9]", "")
                                +"."+priceFraction.get(i).getText()
                );
            }
        }
        double average = totalPrice/totalElements;
        System.out.println("Average price of "+ totalElements + " items is = $" + new DecimalFormat("#,###.00").format(average));

        // Check random element
        int randomNum =  (int)Math.floor(Math.random()*totalElements+1);
        WebElement randomElement = driver.findElement(By.xpath("(//span[contains(@class, 'a-text-normal')])["+randomNum+"]/.."));
        // Get random elements name before click
        String expectedName = randomElement.getText();
        randomElement.click();

        // Get random elements name after click
        WebElement productTitle = driver.findElement(By.id("productTitle"));
        wait.until(ExpectedConditions.visibilityOf(productTitle));
        String actualName = productTitle.getText();

        WebElement productImage = driver.findElement(By.xpath("//div[@id='imgTagWrapperId']/img"));
        wait.until(ExpectedConditions.visibilityOf(productImage));

        // Asser actual equals with expected
        Assert.assertEquals(actualName, expectedName);
        Assert.assertTrue(productImage.isDisplayed());
        System.out.println("actualName = " + actualName);
        System.out.println("expectedName = " + expectedName);
    }
}