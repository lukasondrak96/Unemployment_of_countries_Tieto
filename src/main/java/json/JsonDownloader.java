package json;

import exit_errors.ExitErrors;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class JsonDownloader {

    public static JSONObject getJsonObjectFromUrl(String urlPath) {
        try {
            URL url = new URL(urlPath);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return (JSONObject) new JSONParser().parse(reader);

        } catch (FileNotFoundException e) {
            System.err.println(ExitErrors.FILE_NOT_FOUND.getErrorMsg());
            System.exit(ExitErrors.FILE_NOT_FOUND.getErrorCode());
        } catch (ParseException e) {
            System.err.println(ExitErrors.FILE_PARSING.getErrorMsg());
            System.exit(ExitErrors.FILE_PARSING.getErrorCode());
        } catch (IOException e) {
            System.err.println(ExitErrors.FILE_READING.getErrorMsg());
            System.exit(ExitErrors.FILE_READING.getErrorCode());
        }
        return null;
    }

}
