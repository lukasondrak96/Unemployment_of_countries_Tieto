package json;

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
        JSONArray valuesArr = (JSONArray) inputJsonFile.get("value");
        Iterator i = valuesArr.iterator();

        while (i.hasNext()) {
            double next = (double) i.next();
            valuesList.add(next);
        }
    }

    public double provideNextValue() {
         return valuesList.get(currentValue++);
    }

}
