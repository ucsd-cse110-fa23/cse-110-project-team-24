package pantrypal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URI;
import java.net.URLEncoder;
import java.net.URLDecoder;

public class PerformRequest {
    public static String performRequest(String endpoint, String method, String data, String query) {
        try {
            String urlString = "http://localhost:8100/" + endpoint;
            if (query != null) {
                /**
                Source: https://alvinalexander.com/blog/post/java/how-encode-java-string-send-web-server-safe-url/#:~:text=Answer%3A%20As%20the%20question%20implies%2C%20you%20can%27t%20just,in%20the%20following%20sample%20Java%20source%20code%20example%3A
                Title: How to encode a Java String to send to a web server URL
                Date Accessed: 11/11/2023
                Use: Used to find info on how to encode a String to a format acceptable in a url (using the encode(String) method)
             */
                urlString += "?=" + URLEncoder.encode(query, "US-ASCII");
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
            return URLDecoder.decode(response, "US-ASCII");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    // does not use US-ASCII encoding
   public static String performTranscriptionRequest(String endpoint, String method, String data, String query) throws UnsupportedEncodingException {
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

