package java8inaction.asyncprograming;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class NoneBlockExample {
    static List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll")
            );

    //original code
    public static List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    // with parallel stream
    public static List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    // with completableFuture
    public static List<String> findPricesCompletableFuture(String product) {
        final Executor executor =
                Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread();
                //자바에서 일반 스레드가 실행중이면 자바 프로그램은 종료되지 않는다. 반면 데몬 스레드는 자바프로그램이 종료될 때 강제로 실행이 종료 될 수 있다.
                t.setDaemon(true);
                return t;
            }
        });
                List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " + shop.getPrice(product)
                        , executor))
                .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }



    /*
     병렬, 비동기 두 버전 모두 내부적으로 Runtime.getRuntime().availableProcessors()가 반환하는 스레드 수를 사용하면서 비슷한 결과가 된다.
     결과는 비슷하지만 CompletableFuture는 병렬 스트림 버전에 비해 작업에 이용할 수 있는 Executor를 지정할 수 있는 장점이 있다.
     */
    public static void main(String[] args) {
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = ((System.nanoTime() - start) - 1_000_000);
        System.out.println("Done in " + duration + " msecs");
    }
}
