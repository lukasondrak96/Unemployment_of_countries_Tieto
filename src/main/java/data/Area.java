package data;

import java.util.ArrayList;
import java.util.List;

public class Area {

    private String name;
    private String label;
    private long orderIndex;
    private List<UnemploymentRate> unemploymentRateList;

    public Area(String name, String label, long orderIndex) {
        this.name = name;
        this.label = label;
        this.orderIndex = orderIndex;
        this.unemploymentRateList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public long getOrderIndex() {
        return orderIndex;
    }

    public List<UnemploymentRate> getUnemploymentRateList() {
        return unemploymentRateList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Area: " +
                "\nname=" + name +
                "\nlabel=" + label +
                "\nlist of rates=");
        for (UnemploymentRate rate : unemploymentRateList) {
            sb.append("\n\t" + rate.getYear() + " -> " + rate.getRate());
        }
        return sb.toString();
    }
}
