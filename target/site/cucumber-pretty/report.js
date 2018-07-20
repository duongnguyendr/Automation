$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("demo.feature");
formatter.feature({
  "line": 1,
  "name": "Logging Test Feature",
  "description": "This feature to verify loggin with 4 roles on many servers",
  "id": "logging-test-feature",
  "keyword": "Feature"
});
formatter.before({
  "duration": 5043446400,
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
  "name": "I click some thing",
  "keyword": "Given "
});
formatter.match({
  "location": "DemoStepDefinition.iClickSomething()"
});
formatter.result({
  "duration": 143095100,
  "status": "passed"
});
formatter.write("Scenario Passed");
formatter.after({
  "duration": 793538000,
  "status": "passed"
});
});