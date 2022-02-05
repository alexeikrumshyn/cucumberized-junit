package rummikub;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

public class JScenario {
    public ArrayList<String> steps;

    public JScenario(String fn) {
        steps = openFile(fn);
    }

    private ArrayList<String> openFile(String filename) {
        ArrayList<String> steps = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                steps.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return steps;
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

    private void parseSteps() throws Exception {
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
        JScenario sc1 = new JScenario("src/test/java/rummikub/meld_testing.jfeature");
        sc1.run();
    }
}
