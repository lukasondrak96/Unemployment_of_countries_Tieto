package com.test.unemploymentstats;

import com.test.unemploymentstats.data.Area;

import com.test.unemploymentstats.data.UnemploymentRate;
import com.test.unemploymentstats.exit_errors.ExitErrors;
import com.test.unemploymentstats.json_processing.JsonFileDownloader;
import com.test.unemploymentstats.json_processing.JsonOECDParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.test.unemploymentstats.exit_errors.ExitErrors.*;

/**
 * Main class - writer of stats of unemployemnt rate in areas.
 */
public class UnemploymentStatsWriter {

    private static final String URL = "https://json-stat.org/samples/oecd.json";
    private static final int COUNT_OF_AREAS_TO_WRITE = 3;

    /**
     * List of areas with rates
     */
    private List<Area> areaList;

    public UnemploymentStatsWriter() throws IOException, ParseException, NullPointerException, IndexOutOfBoundsException {
        JSONObject jsonObject = JsonFileDownloader.getJsonObjectFromUrl(URL);
        this.areaList = new JsonOECDParser(jsonObject).getAreaList();
    }

    private void writeExtremeOfUnemploymentRate(int countOfAreas, ExtremesOfUnemployment extreme) {
        if (extreme == ExtremesOfUnemployment.HIGHEST) {
            writeLowestUnemploymentRates(countOfAreas);
        } else {
            writeHighestUnemploymentRates(countOfAreas);
        }
    }

    private void writeHighestUnemploymentRates(int count) {
        List<Area> maxSortedAreas = areaList;
        Collections.sort(maxSortedAreas, (a1, a2) -> {
            int firstLength = a1.getUnemploymentRateList().size();
            int secondLength = a2.getUnemploymentRateList().size();
            double highestRateOfFirst = a1.getUnemploymentRateList().get(firstLength - 1).getRate();
            double lowestRateOfSecond = a2.getUnemploymentRateList().get(secondLength - 1).getRate();
            if (highestRateOfFirst - lowestRateOfSecond > 0) {
                return 1;
            } else {
                return -1;
            }
        });

        List<Area> areasToWrite = createArrayToWrite(count);
        writeFormattedUnemploymentRates(areasToWrite, ExtremesOfUnemployment.HIGHEST);
    }

    private void writeLowestUnemploymentRates(int count) {
        List<Area> minSortedAreas = areaList;
        Collections.sort(minSortedAreas, (a1, a2) -> {
            double lowestRateOfFirst = a1.getUnemploymentRateList().get(0).getRate();
            double lowestRateOfSecond = a2.getUnemploymentRateList().get(0).getRate();
            if (lowestRateOfFirst - lowestRateOfSecond > 0) {
                return -1;
            } else {
                return 1;
            }
        });

        List<Area> areasToWrite = createArrayToWrite(count);
        writeFormattedUnemploymentRates(areasToWrite, ExtremesOfUnemployment.LOWEST);
    }

    private List<Area> createArrayToWrite(int count) {
        List<Area> areasToWrite = new ArrayList<>();
        for (int i = areaList.size() - 1; i > 0; i--) {
            count--;
            areasToWrite.add(areaList.get(i));
            if (count == 0)
                break;
        }
        return areasToWrite;
    }

    private void writeFormattedUnemploymentRates(List<Area> areasToWrite, ExtremesOfUnemployment extreme) {
        if (extreme == ExtremesOfUnemployment.HIGHEST) {
            System.out.println("Highest unemployment rate:");
        } else {
            System.out.println("Lowest unemployment rate:");
        }
        int order = 1;
        StringBuilder sb = new StringBuilder();
        for (Area a : areasToWrite) {
            UnemploymentRate unemplRate;
            if (extreme == ExtremesOfUnemployment.HIGHEST) {
                unemplRate = a.getUnemploymentRateList().get(a.getUnemploymentRateList().size()-1);
            } else {
                unemplRate = a.getUnemploymentRateList().get(0);
            }

            sb.append(order + ". "
                    + a.getName() +
                    " (" + a.getAreaCode() + ") - " + unemplRate.getRate() + "%  " +
                    "(" + unemplRate.getYear() + ")\n");
            order++;
        }
        System.out.println(sb);
    }

    private enum ExtremesOfUnemployment {
        HIGHEST,
        LOWEST
    }

    public static void main(String[] args) {
        UnemploymentStatsWriter writer = null;
        try {
            writer = new UnemploymentStatsWriter();
        } catch (IndexOutOfBoundsException e) {
            ExitErrors.exitWithErrCode(NOT_ENOUGH_VALUES);
        } catch (NullPointerException e) {
            ExitErrors.exitWithErrCode(MISSING_ATTRIBUTE_IN_FILE);
        } catch (FileNotFoundException e) {
            ExitErrors.exitWithErrCode(FILE_NOT_FOUND);
        } catch (ParseException e) {
            ExitErrors.exitWithErrCode(FILE_PARSING);
        } catch (IOException e) {
            ExitErrors.exitWithErrCode(FILE_READING);
        }

        writer.writeExtremeOfUnemploymentRate(COUNT_OF_AREAS_TO_WRITE, ExtremesOfUnemployment.HIGHEST);
        writer.writeExtremeOfUnemploymentRate(COUNT_OF_AREAS_TO_WRITE, ExtremesOfUnemployment.LOWEST);
    }
}
