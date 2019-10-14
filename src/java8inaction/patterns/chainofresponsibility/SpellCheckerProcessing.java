package java8inaction.patterns.chainofresponsibility;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class SpellCheckerProcessing extends ProcessingObject<String> {
    @Override
    protected String handleWork(String input) {
        return input.replace("labda", "lambda");
    }


    public static void main(String[] args) {

        //traditional way
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();

        p1.setSuccessor(p2);

        System.out.println(p1.handler("Aren't labdas really sexy?!!"));


        // with lambda
        UnaryOperator<String> headerProcessing = text -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing = text -> text.replace("labda", "lambda");

        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);

        String result = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result);


    }
}
