package rummikub;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class JScenario {
    public ArrayList<String> steps;

    public JScenario(ArrayList<String> s) {
        steps = s;
    }

    public void run() throws Exception {
        Class stepDefs = Class.forName("rummikub.JStepDefs");
        Object obj = stepDefs.getDeclaredConstructor().newInstance();

        parseSteps();
        for (String step : steps) {
            Method method = stepDefs.getDeclaredMethod(step, null);
            method.setAccessible(true);
            method.invoke(obj, null);
        }
    }

    public void parseSteps() throws Exception {
        for (int i = 0; i < steps.size(); ++i) {
            String[] words = steps.get(i).toLowerCase().split(" ");
            if (!words[0].equals("given") && !words[0].equals("when") && !words[0].equals("then"))
                throw new Exception("Scenario step must start with Given, When, or Then");

            String parsedStep = "";
            for (int j = 1; j < words.length; ++j) {
                parsedStep += words[j];
                if (j != words.length - 1)
                    parsedStep += "_";
            }
            steps.set(i, parsedStep);
        }
    }

    public static void main(String[] args) throws Exception {
        ArrayList<String> steps = new ArrayList<>(){{
            add("Given test server is started");
        }};

        JScenario sc1 = new JScenario(steps);
        sc1.run();
    }
}
