package json;

import exit_errors.ExitErrors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;

public class JsonRateProvider {

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
            System.err.println(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorMsg());
            System.exit(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorCode());
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
            System.err.println(ExitErrors.NOT_ENOUGH_VALUES.getErrorMsg());
            System.exit(ExitErrors.NOT_ENOUGH_VALUES.getErrorCode());
        }
        return 0;
    }

}
