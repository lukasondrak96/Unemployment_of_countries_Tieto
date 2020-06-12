package data;

import java.time.Year;

public class UnemploymentRate {

    private Year year;
    private float rate;

    public Year getYear() {
        return year;
    }

    public float getRate() {
        return rate;
    }

    public UnemploymentRate(Year year, float rate) {
        this.year = year;
        this.rate = rate;
    }
}
