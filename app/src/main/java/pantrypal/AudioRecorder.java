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
        addStartAndStopListeners();
    }

    public Button getbackAndTranscriptButtonButton() {
        return this.backAndTranscriptButton;
    }

    public void addStartAndStopListeners() {
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
            // String s = "i want a lunch, and i have carrots, eggs, and rices";
            // String type = GetTypes(s);
            // String IngredientList = GetIngredientList(s);
            // this.getTypeArea().setText(type);
            // this.getIngredientList().setText(IngredientList);
            Path p = Paths.get("recording.mp3");
            if (Files.exists(p)) {
                Whisper transcriptor = new Whisper();
                try {
                    String s = transcriptor.transcript(new File("recording.mp3")).toLowerCase(); // TODO: Server request
                    String[] words = s.split(" ");

                    String type = words[0];
                    String IngredientList = s.substring(s.indexOf(" "));
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

    private void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }
}
