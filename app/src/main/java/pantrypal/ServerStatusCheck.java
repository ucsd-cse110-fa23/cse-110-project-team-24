package pantrypal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class ServerStatusCheck {
    private static final String STATUS_CHECK_ENDPOINT = "http://localhost:8100/status";

    public static boolean isServerAvailable() {
        try {
            URI uri = new URI(STATUS_CHECK_ENDPOINT);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.connect();

            int responseCode = conn.getResponseCode();
            return responseCode == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}