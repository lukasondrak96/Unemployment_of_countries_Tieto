package com.test.unemploymentstats.json_processing;

import com.test.unemploymentstats.data.Area;
import com.test.unemploymentstats.data.UnemploymentRate;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Parser of oecd file with com.test.unemploymentstats.json-stat format. Creates inner structure of areas and their rates in years.
 */
public class JsonOECDParser {

    /**
     * Input com.test.unemploymentstats.json file with com.test.unemploymentstats.data
     */
    private JSONObject jsonFile;

    /**
     * List of areas with rates
     */
    private List<Area> areaList;
    private final static String NO_AREAS_IN_FILE = "No areas in input file.";

    public JsonOECDParser(JSONObject jsonFile) throws NullPointerException, IndexOutOfBoundsException {
        this.jsonFile = jsonFile;
        createListEntries();
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    private void createListEntries() throws NullPointerException, IndexOutOfBoundsException {
        areaList = new ArrayList<>();
        JSONObject dimensionEntry = null;
        try {
            dimensionEntry = (JSONObject) jsonFile.get("dimension");
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }

        createAreas(dimensionEntry);
        areaList.sort(Comparator.comparingLong(Area::getOrderIndex));
        fillAreaUnemploymentRates(dimensionEntry);

        sortUnemploymentListsByValue();
    }

    private void sortUnemploymentListsByValue() {
        for (Area area : areaList) {
            area.getUnemploymentRateList().sort(Comparator.comparingDouble(UnemploymentRate::getRate));
        }
    }

    private void createAreas(JSONObject dimensionEntry) throws NullPointerException {
        JSONObject areaCategoryEntry = null;

        try {
            JSONObject areaEntry = (JSONObject) dimensionEntry.get("area");
            areaCategoryEntry = (JSONObject) areaEntry.get("category");
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
        retrieveAreasFromCategory(areaCategoryEntry);
    }

    private void retrieveAreasFromCategory(JSONObject areaCategoryEntry) throws NullPointerException {
        JSONObject areaLabelEntry = null;
        try {
            areaLabelEntry = (JSONObject) areaCategoryEntry.get("label");
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }

        final JSONObject finalAreaLabelEntry = areaLabelEntry;
        if (finalAreaLabelEntry.size() == 0) {
            System.out.println(NO_AREAS_IN_FILE);
            System.exit(0);
        }

        areaLabelEntry.keySet().forEach(label -> {
            String name = (String) finalAreaLabelEntry.get(label);
            long index = findIndexOfLabel((String) label, areaCategoryEntry);
            areaList.add(new Area(name, (String) label, index));
        });
    }

    private void fillAreaUnemploymentRates(JSONObject dimensionEntry) throws NullPointerException, IndexOutOfBoundsException {
        JSONObject indexEntry = null;
        try {
            JSONObject yearEntry = (JSONObject) dimensionEntry.get("year");
            JSONObject yearCategoryEntry = (JSONObject) yearEntry.get("category");
            indexEntry = (JSONObject) yearCategoryEntry.get("index");
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }

        JsonRateProvider rateProvider = new JsonRateProvider(jsonFile);

        for (Area area : areaList) {
            Set indexSet = indexEntry.keySet();
            new TreeSet(indexSet).forEach(year -> {
                area.getUnemploymentRateList().add(
                        new UnemploymentRate(Integer.parseInt((String) year), rateProvider.provideNextValue())
                );
            });
        }
    }

    private long findIndexOfLabel(String label, JSONObject areaCategoryEntry) throws NullPointerException {
        JSONObject indexEntry = null;
        long value = 0;
        try {
            indexEntry = (JSONObject) areaCategoryEntry.get("index");
            value = (long) indexEntry.get(label);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }

        return value;
    }

}
