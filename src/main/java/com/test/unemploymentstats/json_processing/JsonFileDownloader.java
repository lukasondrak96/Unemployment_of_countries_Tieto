package com.test.unemploymentstats.json_processing;

import com.test.unemploymentstats.exit_errors.ExitErrors;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import static com.test.unemploymentstats.exit_errors.ExitErrors.*;

/**
 * Can download file from url and parse it to JSONObject. Exits program on error.
 */
public class JsonFileDownloader {

    /**
     * Downloads file and parse it to JSONObject.
     * @param urlPath url path to com.test.unemploymentstats.json file
     * @return created JSONObject
     */
    public static JSONObject getJsonObjectFromUrl(String urlPath) {
        try {
            URL url = new URL(urlPath);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return (JSONObject) new JSONParser().parse(reader);

        } catch (FileNotFoundException e) {
            ExitErrors.exitWithErrCode(FILE_NOT_FOUND);
        } catch (ParseException e) {
            ExitErrors.exitWithErrCode(FILE_PARSING);
        } catch (IOException e) {
            ExitErrors.exitWithErrCode(FILE_READING);
        }
        return null;
    }

}
