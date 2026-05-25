package testmu.tests.api;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testmu.utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class UserApiTest {

    private String apiKey;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigReader.get("api.base.url");
        apiKey = ConfigReader.get("api.key");
    }

    @Test
    @Story("Get Users")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify GET list of users returns 200 and correct data")
    public void testGetUsersList() {
        Response response = given()
            .header("x-api-key", apiKey)
            .queryParam("page", 2)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .extract().response();

        int totalUsers = response.jsonPath().getInt("total");
        Assert.assertTrue(totalUsers > 0, "Total users should be greater than 0");

        String firstUserEmail = response.jsonPath().getString("data[0].email");
        Assert.assertNotNull(firstUserEmail, "First user email should not be null");
    }

    @Test
    @Story("Get Single User")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify GET single user returns 200 with correct user data")
    public void testGetSingleUser() {
        Response response = given()
            .header("x-api-key", apiKey)
        .when()
            .get("/users/2")
        .then()
            .statusCode(200)
            .extract().response();

        int userId = response.jsonPath().getInt("data.id");
        String userEmail = response.jsonPath().getString("data.email");

        Assert.assertEquals(userId, 2, "User ID should be 2");
        Assert.assertNotNull(userEmail, "User email should not be null");
    }

    @Test
    @Story("Create User")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify POST create user returns 201 with created user data")
    public void testCreateUser() {
        String requestBody = "{ \"name\": \"Nikita\", \"job\": \"QA Engineer\" }";

        Response response = given()
            .header("x-api-key", apiKey)
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .extract().response();

        String name = response.jsonPath().getString("name");
        String id = response.jsonPath().getString("id");

        Assert.assertEquals(name, "Nikita", "Created user name should match");
        Assert.assertNotNull(id, "Created user should have an ID");
    }

    @Test
    @Story("Delete User")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify DELETE user returns 204 no content")
    public void testDeleteUser() {
        given()
            .header("x-api-key", apiKey)
        .when()
            .delete("/users/2")
        .then()
            .statusCode(204);
    }

    @Test
    @Story("User Not Found")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify GET non-existent user returns 404")
    public void testGetInvalidUser() {
        Response response = given()
            .header("x-api-key", apiKey)
        .when()
            .get("/users/999")
        .then()
            .statusCode(404)
            .extract().response();

        Assert.assertNotNull(response.getBody(), "Response body should exist");
    }

    @Test
    @Story("Response Time")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify API response time is under 3 seconds")
    public void testResponseTime() {
        Response response = given()
            .header("x-api-key", apiKey)
        .when()
            .get("/users?page=1")
        .then()
            .statusCode(200)
            .extract().response();

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 3000,
            "Response time should be under 3000ms but was: " + responseTime + "ms");
    }
}