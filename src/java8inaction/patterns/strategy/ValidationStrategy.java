package java8inaction.patterns.strategy;

public interface ValidationStrategy {
    boolean execute(String s);
}
