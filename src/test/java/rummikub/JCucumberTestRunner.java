package rummikub;


public class JCucumberTestRunner {
    public static void main(String args[]) throws Exception {
        new JScenario("src/test/java/rummikub/meld_testing.jfeature", "rummikub.JStepDefs");
    }
}
