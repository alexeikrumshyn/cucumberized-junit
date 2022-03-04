package rummikub;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class JScenario {
    public ArrayList<String> steps;
    public String stepDefs;
    public LinkedHashMap<String, ArrayList<Object>> parsedSteps;
    public Hashtable<String, ArrayList<String>> methods;

    public JScenario(String fn, String sd) {
        steps = openFile(fn);
        stepDefs = sd;
    }

    /**
     * Opens, reads and saves step definitions from a file into the steps class variable
     * @param filename filepath of scenario
     * @return an ArrayList of steps
     */
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

    /**
     * Main logic of class - calls parsing functions and invokes step methods
     * @throws Exception
     */
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

    /**
     * Looks up parameter types of a given step in the methods Hashtable
     * @param step for which the parameter types will be retrieved
     * @return an array of Class objects corresponding to the step's parameter types
     */
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

    /**
     * Parses the scenario's steps into a LinkedHashMap, which contains the method names as keys, and ArrayLists of parameters as values.
     * @throws Exception
     */
    private void parseSteps() throws Exception {
        parsedSteps = new LinkedHashMap<>(); //<method_name, [parameters]>
        for (int i = 0; i < steps.size(); ++i) {
            String[] words = steps.get(i).split(" ");
            String firstWord = words[0].toLowerCase();
            if (!firstWord.equals("given") && !firstWord.equals("when") && !firstWord.equals("then") && !firstWord.equals("and"))
                throw new Exception("Scenario step must start with Given, When, Then, or And");

            String parsedStep = "";
            ArrayList<Object> params = new ArrayList<>();
            Boolean isParameter = false;
            String param = ""; //temp variable to hold string parameter (that can include spaces)

            for (int j = 1; j < words.length; ++j) {

                if (words[j].startsWith("\"") && words[j].endsWith("\"")) {
                    params.add(words[j].substring(1, words[j].length()-1));
                } else if (words[j].startsWith("\"")) {
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

    /**
     * Checks if a string contains only numeric values
     * @param str input
     * @return boolean
     */
    private Boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); ++i) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * If a string ends with an underscore, remove it
     * @param str input
     * @return string without a trailing underscore
     */
    private String trimTrailingUnderscore(String str) {
        if (str.endsWith("_"))
            return str.substring(0, str.length()-1);
        return str;
    }

    /** Returns the condition of the step that is non-empty
     *
     * @param step JStep annotation to analyze
     * @return
     */
    private String getStepString(JStep step) throws InvocationTargetException, IllegalAccessException {
        String str = "";
        for (Method m : step.annotationType().getDeclaredMethods()) {
            String cond = (String) m.invoke(step, null);
            if (!cond.equals("")) str = cond;
        }
        return str.toLowerCase();
    }

    /**
     * Reads all methods in step defs class, loads method names and their parameter types into a Hashtable
     * @throws Exception
     */
    private void parseStepDefs() throws Exception {
        methods = new Hashtable<>(); //<method_name, [parameters]>

        //loop through all declared methods in step defs class
        for (Method m : JStepDefs.class.getMethods()) {
            JStep intfc = m.getAnnotation(JStep.class);
            if (intfc == null)
                continue;

            String cond = getStepString(intfc);
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
