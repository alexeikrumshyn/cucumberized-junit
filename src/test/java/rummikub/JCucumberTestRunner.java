package rummikub;


public class JCucumberTestRunner {
    public static void main(String args[]) throws Exception {
        long start = System.currentTimeMillis();

        new JScenario("src/test/java/rummikub/declaring_winner.jfeature", "rummikub.JStepDefs");
        new JScenario("src/test/java/rummikub/initial_points.jfeature", "rummikub.JStepDefs");
        new JScenario("src/test/java/rummikub/joker_noreplacement.jfeature", "rummikub.JStepDefs");
        new JScenario("src/test/java/rummikub/joker_replacement_invalid.jfeature", "rummikub.JStepDefs");
        new JScenario("src/test/java/rummikub/joker_replacement_valid.jfeature", "rummikub.JStepDefs");
        new JScenario("src/test/java/rummikub/meld_testing.jfeature", "rummikub.JStepDefs");
        new JScenario("src/test/java/rummikub/table_reuse_advanced.jfeature", "rummikub.JStepDefs");
        new JScenario("src/test/java/rummikub/table_reuse_simple.jfeature", "rummikub.JStepDefs");
        new JScenario("src/test/java/rummikub/user_input_validation.jfeature", "rummikub.JStepDefs");

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }
}
