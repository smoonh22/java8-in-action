package java8inaction.asyncprograming;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class Shop {
    String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //sync method
    public double getPrice(String product) {
        return calculatePrice(product);

    }
    //async method
    public Future<Double> getAsyncPrice(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                // if an error occurs during the process, error has to be included in future object.
                // otherwise, the invoking thread doesn't know if invoked thread has an error, just keep waiting until the task is done.
                futurePrice.completeExceptionally(e);
            }

        }).start();
        return futurePrice;
    }

    public Future<Double> getSimpleAsyncPrice(String product) {
        // this is a simple way of getAsyncPrice.   getSyncPrice == getSimpleAsyncPrice
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        delay();
        return ThreadLocalRandom.current().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    // replacement of database operation
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getAsyncPrice("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("invocation returned after " + invocationTime + " msecs");

        //doSomethingElse();

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");

    }
}
