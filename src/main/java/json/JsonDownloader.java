package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class JsonDownloader {

    JSONParser jsonParser = new JSONParser();


    public JSONObject getJsonObjectFromUrl(String urlPath) {
        try {
            URL url = new URL(urlPath);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return (JSONObject) jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
            System.err.println("This file cannot be downloaded. Please check if you typed in correct url.");
        } catch (ParseException e) {
            System.err.println("Error while parsing file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
