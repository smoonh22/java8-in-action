package java8inaction.patterns.observer;

public interface Subject {
    void registerObserver(Observer observer);
    void notifyObserver(String tweet);
}
