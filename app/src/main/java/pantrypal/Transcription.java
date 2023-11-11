package pantrypal;

import java.io.File;

public interface Transcription {
    public String transcript(File f);
}

class MockTranscription implements Transcription {
    String text;
    MockTranscription(String response) {
        this.text = response;
    }
    @Override
    public String transcript(File f) {
        return this.text;
    }
}
