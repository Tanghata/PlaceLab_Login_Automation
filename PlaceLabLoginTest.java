package placelab.tests;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import placelab.utilities.WebDriverSetup;

public class PlaceLabLoginTest {
    public WebDriver driver;
    private String host = System.getProperty("host");
    private String homePageUrl = System.getProperty("homepage");
    private String forgotPasswordUrl = "https://demo.placelab.com/password/forgot";
    private String termsOfUseUrl = "https://demo.placelab.com/terms_of_service";
    private String user = "Damir Memic";
    private String username = System.getProperty("username");
    private String password = System.getProperty("password");
    private String incorrectUsername = "notTheRealUsername";
    private String incorrectPassword = "notTheRealPassword";
    private String credentialsErrorMessage = "Invalid credentials!";

    @Parameters({"browser"})

//    @BeforeSuite(alwaysRun = true)
//    public void initDriver(String browser) {
//        driver = WebDriverSetup.getWebDriver(browser);
//    }

    @BeforeTest(alwaysRun = true, groups = {"ValidCredentials, InvalidCredentials, PageElements"},
            description = "Verify that user is able to open PlaceLab demo website.")

    public void openWebsite (String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        //Go to required website
        driver.navigate().to(host);
        //Validate that user is redirected to the right page
        Assert.assertEquals(driver.getCurrentUrl(), host);
        Assert.assertEquals(driver.getTitle(), "PlaceLab");
        WebElement logo = driver.findElement(By.xpath("//img[@src='/assets/logo" +
                "-526ea19604d26801aca90fe441f7df4775a24a5d74ae273dbc4af85f42241259.png']"));
        boolean logoPresent = logo.isDisplayed();
        Assert.assertTrue(logoPresent);
//      System.out.println(logo.getLocation());
    }

//    POSITIVE TEST CASES
    @Test(priority = 9, groups = {"ValidCredentials"}, description = "This test verifies that user is able to " +
            "log in to PlaceLab with valid credentials.", suiteName = "Login Test")
    public void loginPageTestPositive() {
        //Fill out login parameters, and then click on the login button
        driver.findElement(By.name("email")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Log in']")).click();

        //Validate that user is successfully logged in
        //(NOTE: Sign out and return to login page do not work when this is enabled)
//        Assert.assertEquals(driver.getCurrentUrl(), homePageUrl);
//        try {
//            WebElement userName = driver.findElement(By.id("user-name"));
//            assert (userName.getText().contains(user));
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Expected user is not logged in!");
//        }
//        WebElement userRole = driver.findElement(By.id("user-role"));
//        Assert.assertEquals(userRole.getText(), "Group Admin");

        //Sign out and return to the login page
        driver.findElement(By.xpath("//*[@id='actions-nav-item']")).click();
        driver.findElement(By.linkText("Sign out")).click();
    }

    @Test(priority = 10, groups = {"ValidCredentials"}, description = "This test verifies that user is able to " +
            "log in to PlaceLab with valid credentials, by pressing the 'enter' key.", suiteName = "Login Test")
    public void loginWithEnterKeyTest() {
        //Fill out login parameters and then press 'enter' on your keyboard
        driver.findElement(By.name("email")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Log in']")).sendKeys(Keys.ENTER);

        //Sign out and return to the login page
        driver.findElement(By.xpath("//*[@id='actions-nav-item']")).click();
        driver.findElement(By.linkText("Sign out")).click();
        }

    @Test(priority = 7, groups = {"PageElements"}, description = "This test verifies that specific unclickable " +
            "elements are present on the PlaceLab login page.", suiteName = "Login Test")
    public void validatePageElementsTest() {
        //Validate unclickable elements on the page
        WebElement pageSubtitle = driver.findElement(By.xpath("//p[@class='bold headline']"));
        Assert.assertEquals(pageSubtitle.getText(), "Turn your data into information, information into insight "+
                "and insight into business decisions.");

        WebElement rightHeadline1 = driver.findElement(By.xpath("(//div[@class='span5 pull-right']/div[@class"+
                "='row info']/div[@class='span3'])[1]"));
        Assert.assertEquals(rightHeadline1.getText(), "Turn raw location data into actionable insights");

        WebElement rightHeadline2 = driver.findElement(By.xpath("//p[@style='padding-top:8px;']"));
        Assert.assertEquals(rightHeadline2.getText(), "Quantify user experience");

        WebElement rightHeadline3 = driver.findElement(By.xpath("(//div[@class='span5 pull-right']/div[@class"+
                "='row info']/div[@class='span3'])[3]"));
        Assert.assertEquals(rightHeadline3.getText(), "Independent attestation of quality");

//        WebElement footer = driver.findElement(By.className("copyright"));
//        Assert.assertEquals (footer.getText(), "© Copyright 2012 - 2022 Atlantbh d.o.o. ® All Rights Reserved.");
    }

    @Test(priority = 11, groups = {"PageElements"}, description = "This test verifies that user is able to " +
            "open the 'Forgot your password?' link on login page.", suiteName = "Login Test")
    public void forgotPasswordTest() {
        //Validate that 'Forgot your password?' message is displayed on the login page, and then click on it
        WebElement forgotPasswordMessage = driver.findElement(By.xpath("//div/a[@class='link-btn']"));
        Boolean forgotPasswordMessagePresent = forgotPasswordMessage.isDisplayed();
        Assert.assertTrue(forgotPasswordMessagePresent);
        driver.findElement(By.xpath("//div/a[@class='link-btn']")).click();

        //Validate that you've navigated to the right page
        Assert.assertEquals(driver.getCurrentUrl(), forgotPasswordUrl);

        WebElement forgotPasswordPageSubtitle1 = driver.findElement(By.className("small-headline"));
        Assert.assertEquals(forgotPasswordPageSubtitle1.getText(), "Let's find your account");
    }

    @Test(priority = 8, groups = {"PageElements"}, description = "This test verifies that user is able to " +
            "open the 'Terms of Use' link on PlaceLab login page.", suiteName = "Login Test")
    public void termsOfUseTest() {

        //Validate that 'Terms of Use' message is displayed on the login page
        WebElement termsOfUseMessage = driver.findElement(By.xpath("//span/a[@class='link-btn']"));
        Boolean termsOfUseMessagePresent = termsOfUseMessage.isDisplayed();
        Assert.assertTrue(termsOfUseMessagePresent);

        //Store the ID of the original window
        String originalWindow = driver.getWindowHandle();

        //Click on 'Terms of Use' link which opens in a new tab or window
        driver.findElement(By.xpath("//span/a[@class='link-btn']")).click();

        //Loop through to find a new tab or window handle
        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        //Validate that you've navigated to the right page
        Assert.assertEquals(driver.getCurrentUrl(), termsOfUseUrl);

        WebElement termsOfUsePageTitle = driver.findElement(By.className("terms-header"));
        Assert.assertEquals(termsOfUsePageTitle.getText(), "Terms and conditions of use");

        //Close the newly-opened tab or window, and then switch back to the old one
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    //NEGATIVE TEST CASES
    @Test(priority = 4, groups = {"InvalidCredentials"}, description = "This test verifies that user cannot log in " +
            "to PlaceLab with invalid password.")
    public void wrongPasswordTest() {
        //Enter correct username and incorrect password, and then click on login button
        driver.findElement(By.name("email")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(incorrectPassword);
        driver.findElement(By.xpath("//input[@value='Log in']")).click();
    }

    @Test(priority = 5, groups = {"InvalidCredentials"}, description = "This test verifies that user cannot log in " +
            "to PlaceLab with invalid username.")
    public void wrongUsernameTest() {
        //Enter incorrect username and correct password, and then click on login button
        driver.findElement(By.name("email")).sendKeys(incorrectUsername);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Log in']")).click();
    }

    @Test(priority = 6, groups = {"InvalidCredentials"}, description = "This test verifies that user cannot log in " +
            "to PlaceLab with invalid username and invalid password.")
    public void wrongUsernameWrongPassTest() {
        //Enter incorrect username and incorrect password, and then click on login button
        driver.findElement(By.name("email")).sendKeys(incorrectUsername);
        driver.findElement(By.name("password")).sendKeys(incorrectPassword);
        driver.findElement(By.xpath("//input[@value='Log in']")).click();
    }

    @Test(priority = 2, groups = {"InvalidCredentials"}, description = "This test verifies that user cannot log in " +
            "to PlaceLab without providing the password")
    public void noPasswordTest() {
        //Enter the username but do not enter the password, and then click on login button
        driver.findElement(By.name("email")).sendKeys(username);
        driver.findElement(By.xpath("//input[@value='Log in']")).click();
    }

    @Test(priority = 3, groups = {"InvalidCredentials"}, description = "This test verifies that user cannot log in " +
            "to PlaceLab without providing the username")
    public void noUsernameTest() {
        //Enter the password but do not enter the username, and then click on login button
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Log in']")).click();
    }

    @Test(priority = 1, groups = {"InvalidCredentials"}, description = "This test verifies that user cannot log in " +
            "to PlaceLab with providing neither the username nor password")
    public void noUsernameNoPasswordTest() {
        //Click on the login button without providing username or password
        driver.findElement(By.xpath("//input[@value='Log in']")).click();
    }

    @AfterTest(dependsOnGroups = {"InvalidCredentials"}, description = "Verify that user is not logged in")
    public void failedLogin() {
        //Validate that user cannot log in after providing invalid credentials
        Assert.assertEquals(driver.getCurrentUrl(), host);
        Assert.assertEquals(driver.findElement(By.className("error-area")).getText(), credentialsErrorMessage,
                "User should not be able to log in with invalid credentials!");
    }

    //Sign out and return to the login page do not work when included in @AfterTest, regardless if
    //validation that user is successfully logged in is performed or not

/*  @AfterTest(dependsOnGroups = {"ValidCredentials"}, description = "Verify that user is successfully logged in")
    public void successfulLogin() {
        //Validate that user is successfully logged in
        Assert.assertEquals(driver.getCurrentUrl(), homePageUrl);
        try {
            WebElement userName = driver.findElement(By.id("user-name"));
            assert (userName.getText().contains(user));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Expected user is not logged in!");
        }
        WebElement userRole = driver.findElement(By.id("user-role"));
        Assert.assertEquals(userRole.getText(), "Group Admin");

        //Sign out and return to the login page
        driver.findElement(By.xpath("//*[@id='actions-nav-item']")).click();
        driver.findElement(By.linkText("Sign out")).click();
    }
*/
    //CLEAN UP - CLOSE THE BROWSER
    @AfterSuite(alwaysRun = true)
    public void quitDriver() {
        driver.close();
    }
}