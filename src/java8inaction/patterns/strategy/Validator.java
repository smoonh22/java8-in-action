package java8inaction.patterns.strategy;

public class Validator {
    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy validationStrategy) {
        this.strategy = validationStrategy;
    }

    public boolean validate(String s) {
        return strategy.execute(s);
    }

    public static void main(String[] args) {
        //traditional way
        Validator numericValidator = new Validator(new IsNumeric());
        boolean b1 = numericValidator.validate("aaaa");
        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        boolean b2 = lowerCaseValidator.validate("bbbb");

        //with lambda
        Validator numericValidatorWithLambda = new Validator(s -> s.matches("\\d+"));
        System.out.println(numericValidatorWithLambda.validate("aaaa"));
        Validator lowerCaseValidatorWithLambda = new Validator(s -> s.matches("[a-z]+"));
        System.out.println(lowerCaseValidatorWithLambda.validate("bbbb"));

    }
}
