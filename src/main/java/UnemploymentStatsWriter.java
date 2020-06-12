import json.JsonDownloader;
import org.json.simple.JSONObject;

public class UnemploymentStatsWriter {

    private static final String url = "https://json-stat.org/samples/oecd.json";

    public static void main(String[] args) {
        JSONObject a = new JsonDownloader().getJsonObjectFromUrl(url);
        System.out.println(a.get("value"));
    }
}
