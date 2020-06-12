import json.JsonDownloader;
import json.JsonOECDParser;
import org.json.simple.JSONObject;

public class UnemploymentStatsWriter {

    private static final String url = "https://json-stat.org/samples/oecd.json";

    public static void main(String[] args) {
        JSONObject jsonObject = JsonDownloader.getJsonObjectFromUrl(url);
        JsonOECDParser parser = new JsonOECDParser(jsonObject);
        parser.writeLowestUnemploymentRate(3);
        parser.writeHighestUnemploymentRate(3);
    }
}
