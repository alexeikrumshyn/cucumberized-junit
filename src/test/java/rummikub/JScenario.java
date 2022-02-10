package rummikub;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class JScenario {
    public ArrayList<String> steps;
    public String stepDefs;
    public Hashtable<String, ArrayList<String>> methods;

    public JScenario(String fn, String sd) {
        steps = openFile(fn);
        stepDefs = sd;
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
        Class stepDefsClass = Class.forName(this.stepDefs);
        Object obj = stepDefsClass.getDeclaredConstructor().newInstance();

        parseStepDefs();
        parseSteps();
        for (String step : steps) {
            Method method = stepDefsClass.getDeclaredMethod(step, null);
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

    /* Reads all methods in step defs class, loads method names and parameters with their index */
    private void parseStepDefs() throws Exception {
        methods = new Hashtable<>(); //<method_name, [parameters@idx]>

        //loop through all declared methods in step defs class
        for (Method m : JStepDefs.class.getMethods()) {
            JGiven intfc = m.getAnnotation(JGiven.class);
            if (intfc == null)
                continue;

            String cond = intfc.cond().toLowerCase();
            String[] words = cond.split(" ");

            String parsedCond = "";
            ArrayList<String> params = new ArrayList<>();

            for (int i = 0; i < words.length; ++i) {
                if (words[i].startsWith("{") && words[i].endsWith("}")) {
                    params.add(words[i].substring(1, words[i].length()-1) + "@"+i);
                } else {
                    parsedCond += words[i] + "_";
                }
            }
            //trim trailing underscore if necessary
            if (parsedCond.endsWith("_"))
                parsedCond = parsedCond.substring(0, parsedCond.length()-1);

            methods.put(parsedCond, params);
        }
    }

    public static void main(String[] args) throws Exception {
        JScenario sc1 = new JScenario("src/test/java/rummikub/meld_testing.jfeature", "rummikub.JStepDefs");
        sc1.run();
    }
}
