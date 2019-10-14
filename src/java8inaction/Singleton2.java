package java8inaction;

public enum Singleton2 {
    INSTANCE;

    private static Node node;

    public Node getNode() {
        return node;
    }

    static class Node {
        int a;
        String b;

        private Node() {
            this.a = 1;
            this.b = "str";
        }

        public int getA() {
            return a;
        }

        public String getB() {
            return b;
        }
    }

    public static Singleton2 getInstance() {
        node = new Node();
        return INSTANCE;
    }
}
