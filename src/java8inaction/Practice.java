package java8inaction;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;

public class Practice {


    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 700),
                new Transaction(mario, 2012, 710),
                new Transaction(alan, 2012, 950)
        );
        System.out.println("1 번");
        transactions.stream().filter(t -> t.year == 2011).sorted(comparing(x -> x.price)).forEach(System.out::println);
        System.out.println("2 번");
        transactions.stream().map(t -> t.trader.city).distinct().forEach(System.out::println);
        System.out.println("3 번");
        transactions.stream().filter(t -> t.trader.city.equals("Cambridge"))
                .map(p -> p.trader.name).distinct().sorted().forEach(System.out::println);
        System.out.println("4 번");
        transactions.stream().map(t -> t.trader.name).distinct().sorted().forEach(System.out::println);
        System.out.println("5 번");
        //5
        System.out.println(transactions.stream().anyMatch(t -> t.trader.city.equals("Milan")));
        System.out.println("6 번");
        //6
        System.out.println(transactions.stream().filter(t -> t.trader.city.equals("Cambridge")).map(p -> p.price).reduce(Integer::sum));
        System.out.println("7 번");
        System.out.println(transactions.stream().map(t -> t.price).max(Integer::compare));
        System.out.println("8 번");
        System.out.println(transactions.stream().map(t -> t.price).min(Integer::compare));


    }
}

class Trader {
    String name;
    String city;

    Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String toString() {
        return "[ name = " + name + ", city = " + city + "]";
    }
}

class Transaction {
    int year;
    int price;
    Trader trader;

    Transaction(Trader trader, int year, int price) {
        this.trader = trader;
        this.year = year;
        this.price = price;
    }

    @Override
    public String toString() {
        return "year = " + year + ", price = " + price + ", trader = " + trader;
    }
}
