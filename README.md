# Cucumberized JUnit
A fourth year honours project for COMP 4905 at Carleton University. The purpose of this project is to provide guidance for industry developers that want to apply BDD (behaviour-driven development) principles using JUnit. The current (but not widely used) BDD framework is Cucumber, from which inspiration will be taken, hence the name "Cucumberized" JUnit.


## Getting Started
---

This repo tracks work on "Cucumberized" JUnit. If you wish to use this framework on another program, you can copy these two files to the other program directory.
```
src/test/java/rummikub/JScenario.java
src/test/java/rummikub/JStep.java
```

Some more helpful files are found in this repo to get started with the tool. For an example class containing step definition methods annotated with *JStep*:
```
src/test/java/rummikub/JStepDefs.java
```
For an example of how to run features:
```
src/test/java/rummikub/JCucumberTestRunner.java
```
For examples of feature files written for this tool, see any file in ```src/test/java/rummikub``` with the **.jfeature* extension. These files combine to form a complete test suite for the Rummikub game.

## More Content
---
This repo also contains a Rummikub test suite in Cucumber. This was intentionally done to do a side-by-side comparison with "Cucumberized" JUnit. The files for this Cucumber test suite are found here:
```
src/test/java/rummikub/StepDefMeldTesting.java
src/test/java/rummikub/*.feature
```

There is also a test suite using JUnit. This can be found at ```src/test/java/rummikub/UnitTests.java```.

There is a file that runs a local server for the test suites. This is found at ```src/test/java/rummikub/TestServer.java```.

The implementation of the Rummikub game can be found under ```src/main```. 

---
Authored by Alexei Krumshyn under the supervision of Professor Jean-Pierre Corriveau.