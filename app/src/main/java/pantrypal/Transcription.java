package pantrypal;

public interface Transcription {
    public String transcript();
}

class MockTranscription implements Transcription {
    String text;
    MockTranscription(String response) {
        this.text = response;
    }
    @Override
    public String transcript() {
        return this.text;
    }
}
