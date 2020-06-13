package com.test.unemploymentstats;

import com.test.unemploymentstats.data.Area;
import com.test.unemploymentstats.data.UnemploymentRate;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UnemploymentStatsWriterTest {

    private String testPath1;
    private String testPath2;
    private String notEnoughValuesFilePath;
    private String noYearAttributeFilePath;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void init() {
        testPath1 = "file:src/test/testFiles/test_tieto_data.json";
        testPath2 = "file:src/test/testFiles/test2.json";
        notEnoughValuesFilePath = "file:src/test/testFiles/test2_not_enough_values.json";
        noYearAttributeFilePath = "file:src/test/testFiles/test2_without_year.json";
        setUpStream();
    }

    private void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void correctInnerStructureLoaded() throws IOException, ParseException {
        List<UnemploymentRate> expectedFirstRate = new ArrayList<>();
        expectedFirstRate.add(new UnemploymentRate(2011, 4.2));
        expectedFirstRate.add(new UnemploymentRate(2010, 5.3));
        expectedFirstRate.add(new UnemploymentRate(2012, 7.2));
        expectedFirstRate.add(new UnemploymentRate(2013, 8.4));
        expectedFirstRate.add(new UnemploymentRate(2014, 9.1));
        Area expectedFirstArea = new Area("Australia", "AU", 0);
        List<UnemploymentRate> expectedLastRate = new ArrayList<>();
        expectedLastRate.add(new UnemploymentRate(2014, 4.2));
        expectedLastRate.add(new UnemploymentRate(2013, 8.2));
        expectedLastRate.add(new UnemploymentRate(2012, 10.1));
        expectedLastRate.add(new UnemploymentRate(2010, 12.1));
        expectedLastRate.add(new UnemploymentRate(2011, 13.1));
        Area expectedLastArea = new Area("Chile", "CL", 4);
        expectedFirstArea.setUnemploymentRateList(expectedFirstRate);
        expectedLastArea.setUnemploymentRateList(expectedLastRate);

        UnemploymentStatsWriter writer1 = new UnemploymentStatsWriter(testPath2);

        assertEquals(writer1.getAreaList().get(0), expectedFirstArea);
        assertEquals(writer1.getAreaList().get(writer1.getAreaList().size()-1), expectedLastArea);
    }

    @Test
    public void okPathsTest() throws IOException, ParseException {
        UnemploymentStatsWriter writer1 = new UnemploymentStatsWriter(testPath1);
        writer1.writeExtremeOfUnemploymentRate(5, UnemploymentStatsWriter.ExtremesOfUnemployment.LOWEST);
        writer1.writeExtremeOfUnemploymentRate(5, UnemploymentStatsWriter.ExtremesOfUnemployment.HIGHEST);
    }

    @Test()
    public void okPathMyDataTest() throws IOException, ParseException {
        UnemploymentStatsWriter writer1 = new UnemploymentStatsWriter(testPath2);
        writer1.writeExtremeOfUnemploymentRate(2, UnemploymentStatsWriter.ExtremesOfUnemployment.HIGHEST);
        writer1.writeExtremeOfUnemploymentRate(4, UnemploymentStatsWriter.ExtremesOfUnemployment.LOWEST);
    }

    @Test
    public void okCorrectOutput() throws IOException, ParseException {
        String expectedOut = "Lowest unemployment rate:\n" +
                "1. Iceland (IS) - 2.301867378%  (2007)\n" +
                "2. Norway (NO) - 2.498729296%  (2007)\n" +
                "3. Mexico (MX) - 2.998805894%  (2003)\n\n" +
                "Highest unemployment rate:\n" +
                "1. Greece (GR) - 27.2364419%  (2014)\n" +
                "2. Spain (ES) - 26.89014696%  (2013)\n" +
                "3. Poland (PL) - 19.61702787%  (2003)\n" +
                "4. Slovak Republic (SK) - 18.22108629%  (2004)\n\n";

        UnemploymentStatsWriter writer1 = new UnemploymentStatsWriter(testPath1);
        writer1.writeExtremeOfUnemploymentRate(3, UnemploymentStatsWriter.ExtremesOfUnemployment.LOWEST);
        writer1.writeExtremeOfUnemploymentRate(4, UnemploymentStatsWriter.ExtremesOfUnemployment.HIGHEST);
        assertEquals(expectedOut, outContent.toString().replace("\r", ""));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void failNotEnoughValuesInFile() throws IOException, ParseException {
        UnemploymentStatsWriter writer1 = new UnemploymentStatsWriter(notEnoughValuesFilePath);
        writer1.writeExtremeOfUnemploymentRate(3, UnemploymentStatsWriter.ExtremesOfUnemployment.HIGHEST);
    }

    @Test(expected = NullPointerException.class)
    public void failJsonFileWithoutYear() throws IOException, ParseException {
        UnemploymentStatsWriter writer1 = new UnemploymentStatsWriter(noYearAttributeFilePath);
        writer1.writeExtremeOfUnemploymentRate(3, UnemploymentStatsWriter.ExtremesOfUnemployment.HIGHEST);
    }
}
