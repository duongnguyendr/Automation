$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("demo.feature");
formatter.feature({
  "line": 1,
  "name": "Logging Test Feature",
  "description": "This feature to verify loggin with 4 roles on many servers",
  "id": "logging-test-feature",
  "keyword": "Feature"
});
formatter.before({
  "duration": 15233650800,
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
  "line": 7,
  "name": "I click on cinema tab",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "I take screenshoots",
  "keyword": "And "
});
formatter.match({
  "location": "GeneralStepsDefinition.navigateToLoginPage()"
});
formatter.result({
  "duration": 14847506300,
  "status": "passed"
});
formatter.match({
  "location": "GeneralStepsDefinition.takeScreenShoots()"
});
formatter.embedding("image/png", "embedded0.png");
formatter.result({
  "duration": 559960500,
  "status": "passed"
});
formatter.match({
  "location": "GeneralStepsDefinition.iClickCinemaTab()"
});
formatter.result({
  "duration": 232099900,
  "status": "passed"
});
formatter.match({
  "location": "GeneralStepsDefinition.takeScreenShoots()"
});
formatter.embedding("image/png", "embedded1.png");
formatter.result({
  "duration": 374505400,
  "status": "passed"
});
formatter.write("Scenario Passed");
formatter.after({
  "duration": 2785808300,
  "status": "passed"
});
});