package com.test.unemploymentstats.json_processing;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Can download file from url and parse it to JSONObject. Exits program on error.
 */
public class JsonFileDownloader {

    /**
     * Downloads file and parse it to JSONObject.
     *
     * @param urlPath url path to json file
     * @return created JSONObject
     */
    public static JSONObject getJsonObjectFromUrl(String urlPath) throws IOException, ParseException {
        URL url = new URL(urlPath);
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return (JSONObject) new JSONParser().parse(reader);
    }

}
