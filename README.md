# API-Cucumber

## **Overview:**
My framework is BDD(Behavior-Driven Development) cucumber-junit framework and my main language is java with selenium. 
In that framework I gave two different layer one of them is business layer for non-techinical people and implementation layer 
for people with technical knowledge.
This API framework is developed using REST Assured and Cucumber BDD. 
REST Assured is a Java library that provides a domain-specific language (DSL)for writing powerful, maintainable tests for RESTful APIs. 
Cucumber is an open source library, which supports behavior driven development.
To be more precise, Cucumber can be defined as a testing framework, driven by plain Gherkin language. It serves as documentation, automated tests,
and a development aid – all in one.
### **Some of the key features of this framework:**
-I use POJOs (Plain Old Java Objects) to carry data is indeed an efficient approach, especially when dealing with large datasets.
- I used the "runners" package to store our CukesRunner and FailedTestRunner classes. These configure where our step definitions 
and feature files are located, and allow us to run specific tests.
- We used the "step_definitions" package to store the implementation of feature file steps. Here, I want to highlight the Hooks class, 
which contains @Before and @After methods that run before and after each scenario.
- We used utilities package in order to store reusable methods inside the utility classes such as Driver,
  BrowserUtilities,ConfigurationReader.
- Also We created features folder in order to put our feature files with scenarios that are written in gherkin language.
- And last We created configure.properties in order to put credentials in it
## This framework generates an Extent report after test execution. To view:
Runtest in CukeRunner class. 
Go to target > cucumber folder
Find "overview-features.html"
Right-click and select "Open in">>select browser.
The report provides a comprehensive overview of test results in an easy-to-read format.
