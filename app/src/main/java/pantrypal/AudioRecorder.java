package pantrypal;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;

import javax.sound.sampled.*;

class AudioRecorder extends VBox {
    private Button backAndTranscriptButton;
    private Button startButton;
    private Button stopButton;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private Label recordingLabel;
    private TextField ReminderLabel;
    // Set a default style for buttons and fields - background color, font size,
    // italics
    String defaultButtonStyle = "-fx-border-color: #000000; -fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px;";
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    AudioRecorder() {
        // Set properties for the flowpane
        this.setPrefSize(370, 120);
        this.setPadding(new Insets(5, 0, 5, 5));
        // this.setVgap(10);
        // this.setHgap(10);
        // this.setPrefWrapLength(170);

        // Add the buttons and text fields
        ReminderLabel = new TextField(
                "Please list the meal type (breakfast, lunch, or dinner) and ingredients in the following format: [meal type], [ingredient 1], [ingredient 2], . . .");
        ReminderLabel.setStyle(defaultButtonStyle);
        ReminderLabel.setPrefSize(800, 100);

        startButton = new Button("Start");
        startButton.setStyle(defaultButtonStyle);

        stopButton = new Button("Stop");
        stopButton.setStyle(defaultButtonStyle);

        backAndTranscriptButton = new Button("Back And Transciprt");
        backAndTranscriptButton.setStyle(defaultButtonStyle);

        recordingLabel = new Label("Recording...");
        recordingLabel.setStyle(defaultLabelStyle);

        this.getChildren().addAll(startButton, stopButton, recordingLabel, backAndTranscriptButton, ReminderLabel);

        // Get the audio format
        audioFormat = getAudioFormat();

        // Add the listeners to the buttons
        addListeners();
    }

    public Button getbackAndTranscriptButtonButton() {
        return this.backAndTranscriptButton;
    }

    public void addListeners() {
        // Start Button
        startButton.setOnAction(e -> {
            startRecording();
        });

        // Stop Button
        stopButton.setOnAction(e -> {
            stopRecording();
        });
    }

    private AudioFormat getAudioFormat() {
        // the number of samples of audio per second.
        // 44100 represents the typical sample rate for CD-quality audio.
        float sampleRate = 44100;

        // the number of bits in each sample of a sound that has been digitized.
        int sampleSizeInBits = 16;

        // the number of audio channels in this format (1 for mono, 2 for stereo).
        int channels = 2;

        // whether the data is signed or unsigned.
        boolean signed = true;

        // whether the audio data is stored in big-endian or little-endian order.
        boolean bigEndian = false;

        return new AudioFormat(
                sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }


    public void setBackAndTrancriptListener(Stage recorderWindow, CreateView view) {
        this.getbackAndTranscriptButtonButton().setOnAction(e -> {
            recorderWindow.close();
            Path filePath = Paths.get("recording.mp3");
            if (Files.exists(filePath)) {
                try {
                    byte[] fileData = GetFileData(new File("recording.mp3"));
                    String fileString = new String(fileData, java.nio.charset.StandardCharsets.ISO_8859_1);
                    //String fileDataString = new String(fileData, StandardCharsets.UTF_8);
                    String audioTranscription = PerformRequest.performTranscriptionRequest("transcript/", "GET", null, fileString);

                    String[] words = audioTranscription.split(" ");

                    String type = words[0];
                    String IngredientList = audioTranscription.substring(audioTranscription.indexOf(" "));
                    view.getTypeArea().setText(type);
                    view.getIngredientList().setText(IngredientList);

                } catch (Exception e1) {

                    e1.printStackTrace();
                }
            }
        });
    }


    private void startRecording() {
        Thread t = new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            // the format of the TargetDataLine
                            DataLine.Info dataLineInfo = new DataLine.Info(
                                    TargetDataLine.class,
                                    audioFormat);
                            // the TargetDataLine used to capture audio data from the microphone
                            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                            targetDataLine.open(audioFormat);
                            targetDataLine.start();
                            recordingLabel.setVisible(true);

                            // the AudioInputStream that will be used to write the audio data to a file
                            AudioInputStream audioInputStream = new AudioInputStream(
                                    targetDataLine);

                            // the file that will contain the audio data
                            File audioFile = new File("recording.mp3");
                            AudioSystem.write(
                                    audioInputStream,
                                    AudioFileFormat.Type.WAVE,
                                    audioFile);
                            recordingLabel.setVisible(false);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        t.start();
    }

    // return String to be written to output stream on server
    private byte[] GetFileData(File file) throws IOException {
        // String fileData = "";
        // try {
        // fileData += ("Content-Disposition: form-data; name=\"file\"; filename=\"" +
        //                 file.getName() +
        //                 "\"\r\n").getBytes();
        // fileData += ("Content-Type: audio/mpeg\r\n\r\n").getBytes();

        // FileInputStream fileInputStream = new FileInputStream(file);
        // byte[] buffer = new byte[1024];
        // int bytesRead;
        // while ((bytesRead = fileInputStream.read(buffer)) != -1) {
        //     fileData += bytesRead;
        // }
        // fileInputStream.close();
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     return null;
        // }
        // return fileData;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("Recording.mp3"));
        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0)
        {
            out.write(buff, 0, read);
        }
        out.flush();
        byte[] audioBytes = out.toByteArray();
        //String audioString = new String(audioBytes, java.nio.charset.StandardCharsets.ISO_8859_1);     
        return audioBytes;
    }

    private void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }
}
