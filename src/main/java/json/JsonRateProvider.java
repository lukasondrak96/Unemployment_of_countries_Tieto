package json;

import exit_errors.ExitErrors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;

import static exit_errors.ExitErrors.*;

public class JsonRateProvider {

    private final static String NO_VALUES_IN_FILE = "No areas in input file.";
    private JSONObject inputJsonFile;
    private LinkedList<Double> valuesList;
    private int currentValue;

    public JsonRateProvider(JSONObject inputJsonFile) {
        this.inputJsonFile = inputJsonFile;
        this.valuesList = new LinkedList<>();
        currentValue = 0;
        fillValuesList();
    }

    private void fillValuesList() {
        JSONArray valuesArr = null;
        try {
            valuesArr = (JSONArray) inputJsonFile.get("value");
        } catch (NullPointerException e) {
            ExitErrors.exitWithErrCode(MISSING_ATTRIBUTE_IN_FILE);
        }

        if(valuesArr.size() == 0) {
            System.out.println(NO_VALUES_IN_FILE);
            System.exit(0);
        }
        Iterator i = valuesArr.iterator();


        while (i.hasNext()) {
            double next = (double) i.next();
            valuesList.add(next);
        }
    }

    public double provideNextValue() throws NullPointerException {
        try {
            return valuesList.get(currentValue++);
        } catch (IndexOutOfBoundsException e) {
            ExitErrors.exitWithErrCode(NOT_ENOUGH_VALUES);
        }
        return 0;
    }

}
