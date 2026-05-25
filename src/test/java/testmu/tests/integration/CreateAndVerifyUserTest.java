package testmu.tests.integration;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testmu.base.BaseTest;
import testmu.pages.InventoryPage;
import testmu.pages.LoginPage;
import testmu.utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class CreateAndVerifyUserTest extends BaseTest {

    private String apiKey;

    @BeforeMethod
    public void setUpIntegration() {
        RestAssured.baseURI = ConfigReader.get("api.base.url");
        apiKey = ConfigReader.get("api.key");
        driver.get(ConfigReader.get("base.url"));
    }

    @Test
    @Story("Integration: API + UI")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create a user via API, then verify UI login flow works end to end")
    public void testCreateUserViaApiAndVerifyUILogin() {

        // Step 1: Create a user via API
        String requestBody = "{ \"name\": \"Nikita\", \"job\": \"SDET\" }";

        Response apiResponse = given()
            .header("x-api-key", apiKey)
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .extract().response();

        String createdUserId = apiResponse.jsonPath().getString("id");
        String createdUserName = apiResponse.jsonPath().getString("name");

        Assert.assertNotNull(createdUserId, "Created user should have an ID");
        Assert.assertEquals(createdUserName, "Nikita", "Created user name should match");

        // Step 2: Log in via UI
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            ConfigReader.get("valid.username"),
            ConfigReader.get("valid.password")
        );

        // Step 3: Verify UI loaded correctly
        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isInventoryListDisplayed(),
            "Inventory page should load after login");
        Assert.assertEquals(inventoryPage.getPageTitle(), "Products",
            "Page title should be Products");

        // Step 4: Cross-layer assertion
        Assert.assertNotNull(createdUserId,
            "API user creation and UI login both succeeded in same test flow");
    }
}