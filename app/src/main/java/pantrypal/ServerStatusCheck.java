package pantrypal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class ServerStatusCheck {

    public static boolean isServerAvailable() throws UnsupportedEncodingException {
        try {
            String endpoint = "/status";
            URL url = new URL("http://localhost:8081/health");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            return responseCode == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
