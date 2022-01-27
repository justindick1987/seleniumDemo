## Info
The tests are written in Java and use Maven, TestNG, and Selenium. All tests run on a Chrome browser.

The tests are located in the `src/test/java/com/justin/conway/tests/AmazonSearchTest.java` class.

The page objects used in the tests are located in `src/test/java/com/justin/conway/pages` directory.

## To Run Automated Tests

### Run from command line
The tests can be run from the command line using Maven. This will run the tests against the prod environment by default.
```
mvn clean test
```

### Running tests against different environments
The tests can be run against the "prod", "staging", "qa", or "dev" environments. Prod is the default environment.
The specified environment will use the corresponding *.properties file to get the corresponding URL to use for the test.

****Running the tests against staging, qa, or dev environments are currently only for example purposes. The tests will intentionally fail due to invalid URLs.***


#### Run against prod environment:
```
mvn clean test -DTestEnvironment=prod
```

#### Run against staging environment:
```
mvn clean test -DTestEnvironment=staging
```

#### Run against qa environment:
```
mvn clean test -DTestEnvironment=qa
```

#### Run against dev environment:
```
mvn clean test -DTestEnvironment=dev
```

### Running tests using different groups
There can also be run by groups. Each test can have different groups assigned to it. 
This enables the desired group to be specified at runtime, and only the tests with a matching group tag will be run. 
Tests that do not have the matching group will be excluded from the test run. 

#### Running tests in the 'prod' group
```
mvn clean test -Dgroups=prod
```
Tests can also be run with a combination of group and environment
```
mvn clean test -Dgroups=prod -DTestEnvironment=qa
```
This would run all the tests tagged with the 'prod' group against the 'qa' environment


### Test Results

A report of the results can be found in `target/surefire-reports/index.html`

To view the report open the `index.html` file in a browser

After the tests have been run, the results are also included in the console logs:
``` 
Results :

Failed tests:
AmazonSearchTest.failingSearchTest:148 » NoSuchElement no such element: Unable...

Tests run: 2, Failures: 1, Errors: 0, Skipped: 0
```