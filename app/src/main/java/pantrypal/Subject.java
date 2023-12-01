package pantrypal;

public interface Subject {
    void notifyAll(String method, int pos, Recipe r);
    void registerObserver(Observer o);
}
