package rummikub;


import java.util.ArrayList;
import java.util.Arrays;

public class JCucumberTestRunner {

    static ArrayList<String> scenarioFiles = new ArrayList<>(Arrays.asList(
            "src/test/java/rummikub/declaring_winner.jfeature",
            "src/test/java/rummikub/initial_points.jfeature",
            "src/test/java/rummikub/joker_noreplacement.jfeature",
            "src/test/java/rummikub/joker_replacement_invalid.jfeature",
            "src/test/java/rummikub/joker_replacement_valid.jfeature",
            "src/test/java/rummikub/meld_testing.jfeature",
            "src/test/java/rummikub/table_reuse_advanced.jfeature",
            "src/test/java/rummikub/table_reuse_simple.jfeature",
            "src/test/java/rummikub/user_input_validation.jfeature"
    ));
    static String stepDefFile = "rummikub.JStepDefs";

    public static void sequentialExecution() throws Exception {
        for (String scenario : scenarioFiles) {
            new JScenario(scenario, stepDefFile);
        }
    }

    public static void parallelExecution() throws Exception {

        for (String scenario : scenarioFiles) {
            Thread t = new Thread(() -> {
                try {
                    new JScenario(scenario, stepDefFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }

    public static void main(String args[]) throws Exception {
        long start = System.currentTimeMillis();
        sequentialExecution();
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Sequential Execution Time: "+timeElapsed);

        /*
        This is a template for how to run scenarios in parallel. This Rummikub
        game does not handle parallelization well - there are probably issues
        with resource contention. An InvocationTargetException is thrown for
        some scenarios, meaning an underlying method (i.e., a function in the
        Rummikub main code) threw an exception. Parallel execution should only
        be used for code that is designed to withstand resource contention
        issues.
         */
//        start = System.currentTimeMillis();
//        parallelExecution();
//        finish = System.currentTimeMillis();
//        timeElapsed = finish - start;
//        System.out.println("Parallel Execution Time: "+timeElapsed);
    }
}
