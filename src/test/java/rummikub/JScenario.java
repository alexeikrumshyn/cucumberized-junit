package rummikub;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class JScenario {
    public ArrayList<String> steps;
    public String stepDefs;
    public LinkedHashMap<String, ArrayList<Object>> parsedSteps;
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
        for (String step : parsedSteps.keySet()) {
            Method method = stepDefsClass.getDeclaredMethod(step, getParamTypes(step));
            method.setAccessible(true);
            method.invoke(obj, parsedSteps.get(step).toArray());
        }
    }

    private Class[] getParamTypes(String step) {
        ArrayList<String> pTypes = methods.get(step);
        Class[] paramTypes = new Class[pTypes.size()];
        for (int i = 0; i < pTypes.size(); ++i) {
            if (pTypes.get(i).equals("int"))
                paramTypes[i] = int.class;
            else if (pTypes.get(i).equals("string"))
                paramTypes[i] = String.class;
        }
        return paramTypes;
    }

    private void parseSteps() throws Exception {
        parsedSteps = new LinkedHashMap<>(); //<method_name, [parameters]>
        for (int i = 0; i < steps.size(); ++i) {
            String[] words = steps.get(i).split(" ");
            String firstWord = words[0].toLowerCase();
            if (!firstWord.equals("given") && !firstWord.equals("when") && !firstWord.equals("then"))
                throw new Exception("Scenario step must start with Given, When, or Then");

            String parsedStep = "";
            ArrayList<Object> params = new ArrayList<>();
            Boolean isParameter = false;
            String param = ""; //temp variable to hold string parameter (that can include spaces)

            for (int j = 1; j < words.length; ++j) {

                if (words[j].startsWith("\"")) {
                    param = words[j].substring(1);
                    isParameter = true;
                } else if (words[j].endsWith("\"")) {
                    param += " " + words[j].substring(0, words[j].length()-1);
                    params.add(param);
                    isParameter = false;
                    param = "";
                } else if (isParameter) {
                    param += " " + words[j];
                } else if (isNumeric(words[j])) {
                    params.add(Integer.parseInt(words[j]));
                } else {
                    parsedStep += words[j].toLowerCase();
                    parsedStep += "_";
                }
            }

            parsedSteps.put(trimTrailingUnderscore(parsedStep), params);
        }
    }

    private Boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); ++i) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    private String trimTrailingUnderscore(String str) {
        if (str.endsWith("_"))
            return str.substring(0, str.length()-1);
        return str;
    }

    /* Reads all methods in step defs class, loads method names and parameters with their index */
    private void parseStepDefs() throws Exception {
        methods = new Hashtable<>(); //<method_name, [parameters]>

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
                    params.add(words[i].substring(1, words[i].length()-1));
                } else {
                    parsedCond += words[i] + "_";
                }
            }

            methods.put(trimTrailingUnderscore(parsedCond), params);
        }
    }

    public static void main(String[] args) throws Exception {
        JScenario sc1 = new JScenario("src/test/java/rummikub/meld_testing.jfeature", "rummikub.JStepDefs");
        sc1.run();
    }
}
