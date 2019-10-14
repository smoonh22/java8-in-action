package java8inaction;

import java.lang.reflect.Constructor;

public class Singleton {
    private static Singleton instance = new Singleton();

    private Singleton(String... strs) {
        System.out.println("call constructor");
    }

    public static Singleton getInstance() {
        return instance;
    }

    public void print() {
        System.out.println("instance hashCode > " + instance.hashCode());
    }

    public static void main(String[] args) {
        Singleton.instance.print();
        Singleton.instance.print();

        Singleton instance = Singleton.getInstance();
        Singleton instance2 = null;

        try {
            Constructor[] constructors = Singleton.class.getDeclaredConstructors();
            for (Constructor c : constructors) {
                c.setAccessible(true);
                instance2 = (Singleton) c.newInstance();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(EnumSingleton.test);


    }
}

enum EnumSingleton {
    INSTANCE;

    static String test;

    public static EnumSingleton getInstance() {
        test = "haha";
        return INSTANCE;
    }

    public void print() {
        System.out.println(" enum instance hashCode > " + INSTANCE.hashCode());
    }
}

