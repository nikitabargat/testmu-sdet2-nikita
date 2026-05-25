# Test Strategy — TestMu SDET2 Assessment

## 1. Scope and Approach

This framework covers three layers of testing:
- **UI Tests**: Login flows and dashboard interactions on saucedemo.com
- **API Tests**: CRUD operations, error handling, and response validation on reqres.in
- **Integration Tests**: A combined flow that exercises both the API and UI layers in a single test

The goal was not to maximise test count but to demonstrate a maintainable, 
extensible framework that another engineer could pick up without guidance.

---

## 2. Tool and Technology Choices

| Tool | Reason |
|------|--------|
| Selenium + Java | Most hands-on experience; strong community support; industry standard for enterprise QA |
| TestNG | Supports data providers, parallel execution config, and better reporting hooks than JUnit |
| REST Assured | Natural Java pairing for API testing; readable given/when/then syntax |
| Allure Reports | Clear visual reporting with severity levels, descriptions, and failure artifacts |
| Maven | Dependency management and build lifecycle control |
| WebDriverManager | Eliminates manual driver binary management; handles version compatibility automatically |
| PageFactory + @FindBy | Cleaner page object syntax; limitation is weaker explicit wait integration |

---

## 3. Test Targets

- **UI**: https://www.saucedemo.com — purpose-built test application, stable and freely accessible
- **API**: https://reqres.in — realistic REST API with full CRUD support, designed for testing

**Known limitation**: These are two separate applications. A true integration test 
would use the same application across both layers — e.g. create a record via API 
and verify it appears in the UI of the same system. The integration test here 
demonstrates the concept and framework capability rather than a real system integration.

---

## 4. Coverage Built

### UI Tests (LoginTest.java)
| Test | Scenario | Priority |
|------|----------|----------|
| testValidLogin | Valid credentials → inventory page loads | Critical |
| testInvalidLogin | Invalid credentials → error message shown | Normal |
| testEmptyCredentials | No input → username required error | Minor |
| testLoginWithMultipleUsers | Data-driven: 3 user types via @DataProvider | Normal |

### API Tests (UserApiTest.java)
| Test | Scenario | Priority |
|------|----------|----------|
| testGetUsersList | GET /users returns 200 with user data | Critical |
| testGetSingleUser | GET /users/2 returns correct user | Critical |
| testCreateUser | POST /users returns 201 with created data | Critical |
| testDeleteUser | DELETE /users/2 returns 204 | Normal |
| testGetInvalidUser | GET /users/999 returns 404 | Normal |
| testResponseTime | Response time under 3000ms | Minor |

### Integration Tests (CreateAndVerifyUserTest.java)
| Test | Scenario | Priority |
|------|----------|----------|
| testCreateUserViaApiAndVerifyUILogin | Create user via API + verify UI login flow | Critical |

---

## 5. What I Would Cover Next

- **Negative UI scenarios**: Form validation edge cases, SQL injection in login fields
- **Cross-browser testing**: Firefox and Edge runs via testng.xml browser parameter
- **Parallel test execution**: TestNG parallel="methods" configuration with thread-safe WebDriver
- **Custom explicit wait utility**: Replace PageFactory eager initialisation with a proper 
  WaitHelper class to reduce flakiness on slow networks
- **Schema validation**: JSON schema validation on API responses using rest-assured's 
  matchesJsonSchema()
- **More API coverage**: PUT/PATCH update user, authentication token flows
- **End-to-end on a single app**: Use an app like OrangeHRM or Parabank where both 
  UI and API belong to the same system

---

## 6. Top 3 Risks

### Risk 1: No Custom Wait Strategy
The framework relies on PageFactory element initialisation without explicit waits. 
On slow networks or CI environments, elements may not be ready when actions are attempted, 
causing intermittent failures. 
**Mitigation**: Build a WaitHelper utility using WebDriverWait and ExpectedConditions.

### Risk 2: External API Dependency
Both saucedemo and reqres.in are third-party services. reqres.in already changed 
its auth model during this assessment (introduced API key requirement), breaking all 
API tests. Any future change to these services will break the suite with no warning.
**Mitigation**: In a production setup, use mocks or a controlled test environment.

### Risk 3: No Parallel Execution
All tests run sequentially. As the suite grows, run time will become a bottleneck. 
The current WebDriver setup in BaseTest is not thread-safe — running parallel tests 
now would cause driver conflicts.
**Mitigation**: Refactor BaseTest to use ThreadLocal<WebDriver> before enabling parallel execution.

---

## 7. AI Usage
See ai-usage-log.md