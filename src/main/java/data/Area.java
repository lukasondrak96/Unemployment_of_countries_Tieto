package data;

import java.util.ArrayList;
import java.util.List;

public class Area {

    private String name;
    private String label;
    private List<UnemploymentRate> unemploymentRateList;

    public Area(String name) {
        this.name = name;
        this.unemploymentRateList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public List<UnemploymentRate> getUnemploymentRateList() {
        return unemploymentRateList;
    }

    public void setUnemploymentRateList(List<UnemploymentRate> unemploymentRateList) {
        this.unemploymentRateList = unemploymentRateList;
    }
}
