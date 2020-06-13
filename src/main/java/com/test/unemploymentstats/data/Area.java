package com.test.unemploymentstats.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing area, usually countries but not necessary (EU).
 */
public class Area {

    private String name;

    /**
     * Short-cut of area such as CZ for Czech republic
     */
    private String areaCode;

    /**
     * Index of area in com.test.unemploymentstats.json file, it chooses what values will be used for this area
     */
    private long orderIndex;

    /**
     * List representing unemployment rates of area in years
     */
    private List<UnemploymentRate> unemploymentRateList;

    public Area(String name, String areaCode, long orderIndex) {
        this.name = name;
        this.areaCode = areaCode;
        this.orderIndex = orderIndex;
        this.unemploymentRateList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public long getOrderIndex() {
        return orderIndex;
    }

    public List<UnemploymentRate> getUnemploymentRateList() {
        return unemploymentRateList;
    }

    public void setUnemploymentRateList(List<UnemploymentRate> unemploymentRateList) {
        this.unemploymentRateList = unemploymentRateList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Area: " +
                "\nname: " + name +
                "\nlabel: " + areaCode +
                "\nlist of rates: ");
        for (UnemploymentRate rate : unemploymentRateList) {
            sb.append("\n\t" + rate.getYear() + " -> " + rate.getRate());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Area other = (Area) obj;

        if (other.getName().equals(this.name) &&
                (other.getAreaCode().equals(this.areaCode) &&
                other.getOrderIndex() == this.orderIndex &&
                other.getUnemploymentRateList().equals(this.getUnemploymentRateList())
        ))
            return true;

        return false;
    }
}
