package com.test.unemploymentstats.json_processing;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Provides values of unemployment rates.
 */
public class JsonRateProvider {

    private final static String NO_VALUES_IN_FILE = "No areas in input file.";
    private JSONObject inputJsonFile;

    /**
     * Structure for loaded values from com.test.unemploymentstats.json
     */
    private LinkedList<Double> valuesList;

    /**
     * Current index in providing list of values
     */
    private int currentIndex;

    public JsonRateProvider(JSONObject inputJsonFile) {
        this.inputJsonFile = inputJsonFile;
        this.valuesList = new LinkedList<>();
        currentIndex = 0;
        fillValuesList();
    }

    /**
     * Provides value of list and moves to next.
     * @return value
     */
    public double provideNextValue() throws IndexOutOfBoundsException {
        try {
            return valuesList.get(currentIndex++);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void fillValuesList() throws NullPointerException {
        JSONArray valuesArr = null;
        try {
            valuesArr = (JSONArray) inputJsonFile.get("value");
        } catch (NullPointerException e) {
            throw new NullPointerException();
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

}
