package pantrypal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;

import java.net.URI;
import java.net.URLEncoder;
import java.net.URLDecoder;

public class PerformRequest implements Subject{
    List<Observer> observers;

    PerformRequest() {
        this.observers = new ArrayList<Observer>();
    }
    public void notifyAll(String method, int pos, Recipe r) {
        for (Observer o: observers) {
            o.update(method, pos, r);
        }
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }
     /*
     * Perform request to server to:
    *   get all recipes
    *   add one recipe
    *   remove one recipe
    *   update one recipe
    */
    public void performRequest(String endpoint, String method, String data, String query) {
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
                out.write(URLEncoder.encode(data, "US-ASCII"));
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            if (response == null) {
                response = "";
            }
            in.close();

            String result = URLDecoder.decode(response, "US-ASCII");

            // notify Observers depending on method
            if (method.equals("GET")) {
                addAllRecipes(result);
                return;
            } else if(method.equals("POST")) {
                int nonDigit = getFirstNonDigitPosition(result);
                int pos = Integer.parseInt(result.substring(0, nonDigit));
                String recipeString = result.substring(nonDigit, result.length());
                Recipe recipe = Recipe.of(recipeString);
                this.notifyAll("POST", pos, recipe);
                return;
            } else if (method.equals("DELETE")) {
                int nonDigit = getFirstNonDigitPosition(result);
                int pos = Integer.parseInt(result.substring(0, nonDigit));
                String recipeString = result.substring(nonDigit, result.length());
                Recipe recipe = Recipe.of(recipeString);
                this.notifyAll("DELETE", pos, recipe);
                return;
            } else if (method.equals("PUT")) {
                int nonDigit = getFirstNonDigitPosition(result);
                int pos = Integer.parseInt(result.substring(0, nonDigit));
                String recipeString = result.substring(nonDigit, result.length());
                Recipe recipe = Recipe.of(recipeString);
                this.notifyAll("PUT", pos, recipe);
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int getFirstNonDigitPosition(String s) {
        int i;
        for (i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            boolean isDigit = curr >= 48 && curr <= 57;
            if (!isDigit)
                break;
        }
        return i;
    }

    private void addAllRecipes(String result) {
        if (result == null) {
            result = "";
        }
        
        this.notifyAll("REMOVEALL", 0, null);
        String delimeter = "RECIPE_SEPARATOR";
        String[] recipeArr = result.split(delimeter);
        
        for (int i = 0; i < recipeArr.length; i++) {
            String components = recipeArr[i];
            if (components == null || components.equals("")) {
                continue;
            }
            this.notifyAll("GET", i, Recipe.of(components));
        }
    }

    public static String performDefaultRequest(String endpoint, String method, String data, String query) {
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
                out.write(URLEncoder.encode(data, "US-ASCII"));
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            if (response == null) {
                response = "";
            }
            in.close();
            return URLDecoder.decode(response, "US-ASCII");
           //return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public static String performImageRequest(String endpoint, String method, String data, String query) {
        try {
            String urlString = "http://localhost:8100/" + endpoint;
            if (query != null) {
                /**
                Source: https://alvinalexander.com/blog/post/java/how-encode-java-string-send-web-server-safe-url/#:~:text=Answer%3A%20As%20the%20question%20implies%2C%20you%20can%27t%20just,in%20the%20following%20sample%20Java%20source%20code%20example%3A
                Title: How to encode a Java String to send to a web server URL
                Date Accessed: 11/11/2023
                Use: Used to find info on how to encode a String to a format acceptable in a url (using the encode(String) method)
             */
                if(method.equals("PUT")){
                    urlString += "?=" + URLEncoder.encode(query, java.nio.charset.StandardCharsets.ISO_8859_1);
                }
                else {urlString += "?=" + URLEncoder.encode(query, "US-ASCII");}
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (data != null) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(URLEncoder.encode(data, "US-ASCII"));
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            if (response == null) {
                response = "";
            }
            in.close();
            return URLDecoder.decode(response, java.nio.charset.StandardCharsets.ISO_8859_1);
           //return response;
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

