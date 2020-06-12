package json;

import data.Area;
import data.UnemploymentRate;
import org.json.simple.JSONObject;

import java.util.*;

public class JsonOECDParser {

    private JSONObject jsonFile;
    private List<Area> areaList;

    public JsonOECDParser(JSONObject jsonFile) {
        this.jsonFile = jsonFile;
        createListEntries();
        for (Area area : areaList) {
            System.out.println(area);
        }
    }

    public void writeLowestUnemploymentRate(int count) {

    }

    public void writeHighestUnemploymentRate(int count) {

    }

    private void createListEntries() {
        areaList = new ArrayList<>();
        JSONObject dimensionEntry = (JSONObject) jsonFile.get("dimension");

        createAreas(dimensionEntry);
        areaList.sort(Comparator.comparingLong(Area::getOrderIndex));
        fillAreaUnemploymentRates(dimensionEntry);

        sortUnemploymentListsByValue();

    }

    private void sortUnemploymentListsByValue() {
        for (Area area : areaList) {
            area.getUnemploymentRateList().sort(Comparator.comparingDouble(UnemploymentRate::getRate));
            System.out.println();
        }
    }

    private void createAreas(JSONObject dimensionEntry) {
        JSONObject areaEntry = (JSONObject) dimensionEntry.get("area");
        JSONObject areaCategoryEntry = (JSONObject) areaEntry.get("category");
        retrieveAreasFromCategory(areaCategoryEntry);
    }

    private void retrieveAreasFromCategory(JSONObject areaCategoryEntry) {
        JSONObject areaLabelEntry = (JSONObject) areaCategoryEntry.get("label");
        areaLabelEntry.keySet().forEach(label -> {
            String name = (String) areaLabelEntry.get(label);
            long index = findIndexOfLabel((String) label, areaCategoryEntry);
            areaList.add(new Area(name, (String) label, index));
        });
    }

    private void fillAreaUnemploymentRates(JSONObject dimensionEntry) {
        JSONObject yearEntry = (JSONObject) dimensionEntry.get("year");
        JSONObject yearCategoryEntry = (JSONObject) yearEntry.get("category");
        JSONObject indexEntry = (JSONObject) yearCategoryEntry.get("index");

        JsonRateProvider rateProvider = new JsonRateProvider(jsonFile);

        for (Area area : areaList) {
            Set indexSet = indexEntry.keySet();
            new TreeSet(indexSet).forEach(year -> {
                area.getUnemploymentRateList().add(
                        new UnemploymentRate( Integer.parseInt((String) year), rateProvider.provideNextValue() )
                );
            });
        }
    }

    private long findIndexOfLabel(String label, JSONObject areaCategoryEntry) {
        JSONObject indexEntry = (JSONObject) areaCategoryEntry.get("index");
        return (long) indexEntry.get(label);
    }

}
