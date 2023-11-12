package pantrypal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URI;

public class PerformRequest {  

     public static String performRequest(String endpoint, String method, String data, String query) throws UnsupportedEncodingException {
        // if (endpoint.equals("transcript/")) return "Breakfast Tomatoes";
        query = URLEncoder.encode(query, java.nio.charset.StandardCharsets.ISO_8859_1);
        try {
            String urlString = "http://localhost:8100/" + endpoint;
            if (query != null) {
                urlString += "?=" + query;
            }
            
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (data != null) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(data);
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }
}

