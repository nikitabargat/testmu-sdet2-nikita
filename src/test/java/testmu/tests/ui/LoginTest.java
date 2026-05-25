package testmu.tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testmu.base.BaseTest;
import testmu.pages.InventoryPage;
import testmu.pages.LoginPage;
import testmu.utils.ConfigReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;


public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpPages() {
        driver.get(ConfigReader.get("base.url"));
        loginPage = new LoginPage(driver);
    }

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a valid user can log in and reach the inventory page")
    public void testValidLogin() {
        loginPage.login(
            ConfigReader.get("valid.username"),
            ConfigReader.get("valid.password")
        );

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isInventoryListDisplayed(),
            "Inventory list should be visible after login");
        Assert.assertEquals(inventoryPage.getPageTitle(), "Products",
            "Page title should be 'Products' after login");
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that an invalid user sees an error message")
    public void testInvalidLogin() {
        loginPage.login(
            ConfigReader.get("invalid.username"),
            ConfigReader.get("invalid.password")
        );

        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "Error message should be displayed for invalid login");
        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Epic sadface"),
            "Error message text should contain 'Epic sadface'"
        );
    }

    @Test
    @Story("Login Validation")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that empty credentials show an error")
    public void testEmptyCredentials() {
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "Error message should be displayed for empty credentials");
        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Username is required"),
            "Error should mention username is required"
        );
    }

    @Test(dataProvider = "multipleUsers")
    @Story("Data Driven Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify login works for multiple valid user types")
    public void testLoginWithMultipleUsers(String username, String password) {
        loginPage.login(username, password);

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isInventoryListDisplayed(),
            "Inventory list should be visible for user: " + username);
    }

    @DataProvider(name = "multipleUsers")
    public Object[][] multipleUsersProvider() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(
            new File("src/test/resources/testdata/users.json")
        );

        return new Object[][] {
            {
                root.get("validUser").get("username").asText(),
                root.get("validUser").get("password").asText()
            },
            {
                root.get("visualUser").get("username").asText(),
                root.get("visualUser").get("password").asText()
            }
        };
    }
}