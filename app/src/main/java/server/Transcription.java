package server;

public interface Transcription {
    public String transcript(byte[] fileData);
}

class MockTranscription implements Transcription {
    String text;
    MockTranscription(String response) {
        this.text = response;
    }
    @Override
    public String transcript(byte[] fileData) {
        return this.text;
    }
}
