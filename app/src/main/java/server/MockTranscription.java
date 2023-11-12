package server;

public class MockTranscription implements Transcription {
    String text;
    public MockTranscription(String response) {
        this.text = response;
    }
    @Override
    public String transcript(byte[] fileData) {
        return this.text;
    }
}
