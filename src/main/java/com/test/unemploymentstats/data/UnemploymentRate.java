package com.test.unemploymentstats.data;

/**
 * Class representing rate of unemployment in area.
 */
public class UnemploymentRate {

    /**
     * Year of rate entry
     */
    private int year;

    /**
     * Unemployment rate in percentages
     */
    private double rate;

    public int getYear() {
        return year;
    }

    public double getRate() {
        return rate;
    }

    public UnemploymentRate(int year, double rate) {
        this.year = year;
        this.rate = rate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        UnemploymentRate other = (UnemploymentRate) obj;
        if (other.getRate() == this.rate && other.getYear() == this.year)
            return true;

        return false;
    }
}
