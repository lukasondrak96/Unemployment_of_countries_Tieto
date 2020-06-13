package com.test.unemploymentstats.json_processing;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JsonFileDownloaderTest {

    private String testPathOK1;
    private String testPathOK2;
    private String testDownloadOverHttpsFilePath;
    private String wrongTestPath;
    private String wrongStructureFilePath;

    @Before
    public void init() {
        testPathOK1 = "file:src/test/testFiles/test_tieto_data.json";
        testPathOK2 = "file:src/test/testFiles/json_file.json";
        testDownloadOverHttpsFilePath = "https://json-stat.org/samples/oecd.json";
        wrongTestPath = "file:src/tesd/tesdfiles/tesd.json";
        wrongStructureFilePath = "file:src/test/testFiles/test_not_json_structure.json";
    }

    @Test
    public void correctPathsTest() throws IOException, ParseException {
        JSONObject jsonOKObj1 = JsonFileDownloader.getJsonObjectFromUrl(testPathOK1);
        JSONObject jsonOKObj2 = JsonFileDownloader.getJsonObjectFromUrl(testPathOK2);
    }

    @Test
    public void correctDownloadFileTest() throws IOException, ParseException {
        //would end up with error without internet connection
        JSONObject jsonOKObj1 = JsonFileDownloader.getJsonObjectFromUrl(testDownloadOverHttpsFilePath);
    }

    @Test(expected = FileNotFoundException.class)
    public void failWrongPathTest() throws IOException, ParseException {
        JSONObject jsonWrongObj = JsonFileDownloader.getJsonObjectFromUrl(wrongTestPath);
    }

    @Test(expected = ParseException.class)
    public void failWrongStructureTest() throws IOException, ParseException {
        JSONObject jsonWrongObj = JsonFileDownloader.getJsonObjectFromUrl(wrongStructureFilePath);
    }
}
