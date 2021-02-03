package com.assertvar;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

public class test_assertverify
{
    WebDriver driver;
    String username = "user-name";
    String access_key = "access-key";

    @BeforeTest
    public void init() throws InterruptedException, MalformedURLException
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", "[Java] Assert and Verify in Selenium WebDriver");
        capabilities.setCapability("name", "[Java] Assert and Verify in Selenium WebDriver");
        capabilities.setCapability("platformName", "OS X Yosemite");
        capabilities.setCapability("browserName", "MicrosoftEdge");
        capabilities.setCapability("browserVersion","81.0");
        capabilities.setCapability("tunnel",false);
        capabilities.setCapability("network",true);
        capabilities.setCapability("console",true);
        capabilities.setCapability("visual",true);

        driver = new RemoteWebDriver(new URL("http://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"),
                capabilities);
        System.out.println("Started session");
    }

    @Test(description = "Demonstration of AssertEquals in Selenium Java", priority = 1, enabled = true)
    public void Test_assert_equals() throws IOException, InterruptedException
    {
        String exp_title = "Most Powerful Cross Browser Testing Tool Online | LambdaTest";
        String test_url = "https://www.lambdatest.com";

        driver.manage().window().maximize();
        driver.get(test_url);
        Thread.sleep(3000);

        String curr_window_title = driver.getTitle();
        /* Hard Assert */
        Assert.assertEquals(curr_window_title, exp_title);
        System.out.println("AssertEquals Test Passed\n");
    }

    @Test (description = "Demonstration of AssertNotEquals in Selenium Java", priority = 2, enabled = true)
    public void Test_assert_not_equals() throws IOException, InterruptedException
    {
        String test_url = "https://www.lambdatest.com";
        By by_emailpath = By.cssSelector("form.blue_form > #useremail");

        driver.manage().window().maximize();
        driver.get(test_url);
        Thread.sleep(3000);

        String curr_window_title = driver.getTitle();
        WebElement elem_email = driver.findElement(by_emailpath);
        try
        {
            elem_email.sendKeys("testing123456@testing123456.com");
            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[@id=\"testing_form\"]/div/button")).click();
            /* In case the email is not registered, the URL would be
            https://accounts.lambdatest.com/register?email=testing123456@testing123456.com
            */
            Assert.assertNotEquals(driver.getCurrentUrl(), "https://www.lambdatest.com/");
            Thread.sleep(3000);
        }
        catch (Exception ex)
        {
            System.out.print(ex.getMessage());
        }
        System.out.println("AssertNotEquals Test Passed\n");
    }

    @Test (description = "Demonstration of assertTrue in Selenium Java", priority = 3, enabled = true)
    public void Test_assert_true() throws IOException, InterruptedException
    {
        String test_url = "https://www.lambdatest.com";
        Actions actions = new Actions(driver);

        driver.get(test_url);

        WebElement elem_resources_menu = driver.findElement(By.xpath("//a[contains(.,'Resources')]"));
        actions.moveToElement(elem_resources_menu).build().perform();
        Thread.sleep(2000);

        By elem_blog_link = By.xpath("//a[.='Blog']");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(elem_blog_link)).click();
        Thread.sleep(2000);

        String expected_url = "https://www.lambdatest.com/blog/";
        String current_url = driver.getCurrentUrl();

        Assert.assertTrue(expected_url.equals(current_url), "URL does not match\n");
        Thread.sleep(2000);
        System.out.println("AssertTrue Test Passed\n");
    }

    @Test (description = "Demonstration of assertFalse in Selenium Java", priority = 4, enabled = true)
    public void Test_assert_false() throws IOException, InterruptedException
    {
        String test_url = "https://www.lambdatest.com";
        String window_title = "Most Powerful Cross Browser Testing Tool Online | LambdaTest";

        driver.get(test_url);

        Boolean bTitleCheck = driver.getTitle().equalsIgnoreCase(window_title);
        Assert.assertFalse(bTitleCheck);
        Thread.sleep(2000);
        System.out.println("AssertFalse Test Passed\n");
    }

    @Test (description = "Demonstration of assertNotNull in Selenium Java", priority = 5, enabled = true)
    public void Test_assert_notnull() throws IOException, InterruptedException
    {
        String test_url = "https://www.lambdatest.com";
        String window_title = "Most Powerful Cross Browser Testing Tool Online | LambdaTest";

        driver.get(test_url);

        String current_url = driver.getCurrentUrl();
        Assert.assertNotNull("Null Object", current_url);
        Thread.sleep(2000);
        System.out.println("AssertNotNull Test Passed");
    }

    @Test (description = "Demonstration of softAssert in Selenium Java", priority = 6, enabled = true)
    public void Test_soft_assert() throws IOException, InterruptedException
    {
        String test_url = "https://www.lambdatest.com";
        String test_url_title = "Most Powerful Cross Browser Testing Tool Online | LambdaTest";

        String test_community_url = "https://community.lambdatest.com/";
        String test_community_title = "LambdaTest Community - Connect, Ask &amp; Learn with Tech-Savvy Folks";

        driver.get(test_url);
        String current_url = driver.getCurrentUrl();

        /* Create an instance of Soft Assert */
        SoftAssert softAssert = new SoftAssert();

        /* This raises an assert but does not throw an exception since it is a Soft Assert */
        softAssert.assertEquals(current_url, test_community_url);
        softAssert.assertNotEquals(test_url_title, test_community_title);
        softAssert.assertNull((driver.getCurrentUrl()), "Null Object Found\n");
        softAssert.assertSame((driver.getCurrentUrl()), "https://www.lambdatest.com","Expected and Current URL are not same");
        softAssert.assertAll();
        Thread.sleep(2000);
        System.out.println("SoftAssert Test Passed\n");
    }

    @AfterClass
    public void tearDown()
    {
        if (driver != null)
        {
            driver.quit();
        }
    }
}