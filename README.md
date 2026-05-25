# TestMu SDET2 Assessment — Nikita Bargat

## Overview

A test automation framework built for the TestMu AI SDET-2 quality engineering challenge.
Covers UI, API, and integration testing with CI/CD pipeline integration and Allure reporting.

---

## Tech Stack

- **Language**: Java 21
- **UI Automation**: Selenium WebDriver 4.x
- **API Testing**: REST Assured
- **Test Runner**: TestNG
- **Reporting**: Allure Reports
- **Build Tool**: Maven
- **CI/CD**: GitHub Actions

---

## Project Structure

## Project Structure

```
testmu-sdet2-nikita/
├── src/test/java/testmu/
│   ├── base/               # BaseTest — WebDriver setup and teardown
│   ├── pages/              # Page Object classes (LoginPage, InventoryPage)
│   ├── tests/
│   │   ├── ui/             # UI test classes
│   │   ├── api/            # API test classes
│   │   └── integration/    # Integration test classes
│   └── utils/              # ConfigReader and shared utilities
├── src/test/resources/
│   ├── config/             # config.properties — environment settings
│   └── testdata/           # users.json — externalised test data
├── .github/workflows/      # GitHub Actions CI pipeline
├── testng.xml              # Test suite configuration
├── test-strategy.md        # Test approach and risk analysis
├── ai-usage-log.md         # AI tool usage log
└── pom.xml
```

---

## Setup

### Prerequisites
- Java 11+
- Maven 3.6+
- Chrome browser installed

### Clone and Install
```bash
git clone https://github.com/nikitabargat/testmu-sdet2-nikita.git
cd testmu-sdet2-nikita
mvn clean install -DskipTests
```

### Configure API Key
Add your reqres.in API key to `src/test/resources/config/config.properties`:
```properties
api.key=your_api_key_here
```

---

## Running Tests

### Run full suite
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=UserApiTest
mvn test -Dtest=CreateAndVerifyUserTest
```

### Generate Allure Report
```bash
mvn allure:report
mvn allure:serve
```

---

## Design Decisions

### Page Object Model
Each page of the application has a dedicated class in `pages/`. Locators and 
actions live in the page class. Assertions live in the test class. 
This means a UI change requires updating one file, not every test.

### Config-Driven Environment
All URLs, credentials, and browser settings live in `config.properties`. 
Switching environments means changing one file.

### Data-Driven Tests
Login tests use TestNG `@DataProvider` to run the same test across multiple 
user types without duplicating test code.

### WebDriverManager
Eliminates manual chromedriver management. The correct driver version is 
downloaded automatically at runtime.

---

## What I Would Build Next

- ThreadLocal WebDriver setup for parallel execution
- Custom WaitHelper utility to replace PageFactory limitations
- Cross-browser testing via testng.xml parameters
- JSON schema validation on API responses
- More negative test scenarios and edge cases

---

## Reports

Sample Allure report is available in the `/reports` folder.
CI pipeline publishes the report as a GitHub Actions artifact on every run.