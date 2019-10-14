package java8inaction;

public class SingletonLazy {
    private int a;
    private int b;
    private String c;
    private SingletonLazy() {
        this.a = 1;
        this.b = 2;
        this.c = "test";
    }

    private static class LazyHolder {
        private static final SingletonLazy INSTANCE= new SingletonLazy();
    }

    public static SingletonLazy getInstance() {
        return LazyHolder.INSTANCE;
    }
}
