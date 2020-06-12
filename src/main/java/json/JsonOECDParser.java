package json;

import data.Area;
import data.UnemploymentRate;
import exit_errors.ExitErrors;
import org.json.simple.JSONObject;

import java.util.*;

public class JsonOECDParser {

    private JSONObject jsonFile;
    private List<Area> areaList;

    public JsonOECDParser(JSONObject jsonFile) {
        this.jsonFile = jsonFile;
        createListEntries();

    }

    public List<Area> getAreaList() {
        return areaList;
    }

    private void createListEntries() {
        areaList = new ArrayList<>();
        JSONObject dimensionEntry = null;
        try {
            dimensionEntry = (JSONObject) jsonFile.get("dimension");
        } catch (NullPointerException e) {
            System.err.println(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorMsg());
            System.exit(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorCode());
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

    private void createAreas(JSONObject dimensionEntry) {
        JSONObject areaCategoryEntry = null;

        try {
            JSONObject areaEntry = (JSONObject) dimensionEntry.get("area");
            areaCategoryEntry = (JSONObject) areaEntry.get("category");
        } catch (NullPointerException e) {
            System.err.println(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorMsg());
            System.exit(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorCode());
        }
        retrieveAreasFromCategory(areaCategoryEntry);
    }

    private void retrieveAreasFromCategory(JSONObject areaCategoryEntry) {
        JSONObject areaLabelEntry = null;
        try {
            areaLabelEntry = (JSONObject) areaCategoryEntry.get("label");
        } catch (NullPointerException e) {
            System.err.println(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorMsg());
            System.exit(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorCode());
        }

        final JSONObject finalAreaLabelEntry = areaLabelEntry;
        areaLabelEntry.keySet().forEach(label -> {
            String name = (String) finalAreaLabelEntry.get(label);
            long index = findIndexOfLabel((String) label, areaCategoryEntry);
            areaList.add(new Area(name, (String) label, index));
        });
    }

    private void fillAreaUnemploymentRates(JSONObject dimensionEntry) {
        JSONObject indexEntry = null;
        try {
            JSONObject yearEntry = (JSONObject) dimensionEntry.get("year");
            JSONObject yearCategoryEntry = (JSONObject) yearEntry.get("category");
            indexEntry = (JSONObject) yearCategoryEntry.get("index");
        } catch (NullPointerException e) {
            System.err.println(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorMsg());
            System.exit(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorCode());
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

    private long findIndexOfLabel(String label, JSONObject areaCategoryEntry) {
        JSONObject indexEntry = null;
        try {
          indexEntry = (JSONObject) areaCategoryEntry.get("index");
        } catch (NullPointerException e) {
            System.err.println(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorMsg());
            System.exit(ExitErrors.MISSING_ATTRIBUTE_IN_FILE.getErrorCode());
        }

        return (long) indexEntry.get(label);
    }

}
