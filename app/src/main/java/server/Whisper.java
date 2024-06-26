package server;

import java.io.*;
import java.net.*;
import org.json.*;


class Whisper implements Transcription{
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";
    // private static final String FILE_PATH = "C:/Users/litia/Downloads/Recording
    // (2) (online-audio-converter.com).mp3";

    // Helper method to write a parameter to the output stream in multipart form
    // data format
    private static void writeParameterToOutputStream(
        OutputStream outputStream,
        String parameterName,
        String parameterValue,
        String boundary
        ) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(
        (
            "Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n"
        ).getBytes()
        );
        outputStream.write((parameterValue + "\r\n").getBytes());
    }

    

    private static String handleSuccessResponse(HttpURLConnection connection)
            throws IOException, JSONException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject responseJson = new JSONObject(response.toString());
        String generatedText = responseJson.getString("text");
        return generatedText;
        // Print the transcription result
        // System.out.println("Whisper Result: " + generatedText);
    }

    // Helper method to handle an error response
    private static void handleErrorResponse(HttpURLConnection connection)
            throws IOException, JSONException {
        BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(connection.getErrorStream()));
        String errorLine;
        StringBuilder errorResponse = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
        }
        errorReader.close();
        String errorResult = errorResponse.toString();
        System.out.println("Error Result: " + errorResult);
    }


    // Helper method to write a file to the output stream in multipart form data
    // format
    private static void writeFileToOutputStream(
        OutputStream outputStream,
        File file,
        String boundary
        ) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
        (
        "Content-Disposition: form-data; name=\"file\"; filename=\"" +
        file.getName() +
        "\"\r\n"
        ).getBytes()
            );
        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());   
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }
    public static File DatatoFile(byte[] filedata) throws FileNotFoundException, IOException{
        File outputfile = new File("RecordingServer.mp3");
        try (FileOutputStream outputStream = new FileOutputStream(outputfile)) {
            outputStream.write(filedata);
        }
        return outputfile;
    }


    public String transcript(byte[] fileData)  {
        try {
            //File file = new File("recording.mp3");
            // Set up HTTP connection
            URL url = new URI(API_ENDPOINT).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Response code becomes -1
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            // Set up request headers
            String boundary = "Boundary-" + System.currentTimeMillis();
            connection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
            // Set up output stream to write request body
            OutputStream outputStream = connection.getOutputStream();
            // Write model parameter to request body
            writeParameterToOutputStream(outputStream, "model", MODEL, boundary);

            File datatoFile = DatatoFile(fileData);
            // Write file parameter to request body
           // outputStream.write(("--" + boundary + "\r\n").getBytes());
            //outputStream.write(fileData);
            writeFileToOutputStream(outputStream, datatoFile, boundary);
            // Write closing boundary to request body
            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            // Flush and close output stream
            outputStream.flush();
            // String transcription = outputStream.toString();
            outputStream.close();
            // Get response code
            int responseCode = connection.getResponseCode(); // response code is 400, HTTP_OK is 200
            
            // Check response code and handle response accordingly
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String ans = handleSuccessResponse(connection);
                return ans;
            } 
            else {
                handleErrorResponse(connection);
                connection.disconnect();
                return "Error while transcripting because of Wrong Response Code";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Exception while transcripting";
        }
            
    }

}
