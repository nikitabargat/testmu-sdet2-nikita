# AI Usage Log

This log documents every AI tool used during this assessment, 
the task it helped with, and what it produced.

| Task | Tool Used | What It Produced | My Decision |
|------|-----------|-----------------|-------------|
| Project setup guidance | Claude (Anthropic) | Step-by-step Maven project setup, folder structure recommendations | I chose the structure and understood each folder's purpose before creating it |
| pom.xml dependencies | Claude (Anthropic) | Dependency list with versions | I reviewed each dependency, understood its purpose, and fixed compiler version issue myself |
| BaseTest architecture | Claude (Anthropic) | WebDriver setup/teardown pattern | I understood the @BeforeMethod/@AfterMethod flow and why protected driver is used |
| Page Object classes | Claude (Anthropic) | LoginPage and InventoryPage with PageFactory | I understood the separation between actions (pages) and assertions (tests) |
| REST Assured API tests | Claude (Anthropic) | given/when/then test structure | I ran, debugged, and fixed the 401 auth issue myself by getting reqres.in API key |
| Integration test | Claude (Anthropic) | Combined API + UI test flow | I understood and can explain its limitations honestly |
| GitHub Actions pipeline | Claude (Anthropic) | CI workflow configuration | I reviewed and understood each step |

## Honest Assessment

I used Claude as a mentor and guide throughout this assessment rather than 
copying output blindly. Every file was typed by me. When errors occurred 
(nested git repo, Maven compiler version, reqres.in 401 errors), 
I debugged and resolved them. I can explain every design decision in this 
framework because I was walked through the reasoning, not just given the answer.