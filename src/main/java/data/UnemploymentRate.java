package data;

public class UnemploymentRate {

    private int year;
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
}
