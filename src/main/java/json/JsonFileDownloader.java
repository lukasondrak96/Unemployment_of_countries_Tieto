package json;

import exit_errors.ExitErrors;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import static exit_errors.ExitErrors.*;

public class JsonFileDownloader {

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
