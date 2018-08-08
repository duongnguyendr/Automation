$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("demo.feature");
formatter.feature({
  "line": 1,
  "name": "Logging Test Feature",
  "description": "This feature to verify loggin with 4 roles on many servers",
  "id": "logging-test-feature",
  "keyword": "Feature"
});
formatter.before({
  "duration": 5423397400,
  "status": "passed"
});
formatter.scenario({
  "line": 4,
  "name": "Login role",
  "description": "",
  "id": "logging-test-feature;login-role",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "I navigate to login page",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I take screenshoots",
  "keyword": "And "
});
formatter.step({
  "comments": [
    {
      "line": 7,
      "value": "#  And I click on cinema tab"
    },
    {
      "line": 8,
      "value": "#  And I take screenshoots"
    }
  ],
  "line": 9,
  "name": "I click on remove button",
  "keyword": "And "
});
formatter.match({
  "location": "GeneralStepsDefinition.navigateToLoginPage()"
});
formatter.result({
  "duration": 4952512300,
  "status": "passed"
});
formatter.match({
  "location": "GeneralStepsDefinition.takeScreenShoots()"
});
formatter.embedding("image/png", "embedded0.png");
formatter.result({
  "duration": 367329500,
  "status": "passed"
});
formatter.match({
  "location": "GeneralStepsDefinition.clickRemoveButton()"
});
formatter.result({
  "duration": 11046194500,
  "status": "passed"
});
formatter.write("Scenario Passed");
formatter.after({
  "duration": 2772159900,
  "status": "passed"
});
});