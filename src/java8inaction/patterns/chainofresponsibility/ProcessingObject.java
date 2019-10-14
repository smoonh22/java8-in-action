package java8inaction.patterns.chainofresponsibility;

public abstract class ProcessingObject<T> {
    protected ProcessingObject<T> successor;

    public void setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
    }

    public T handler(T input) {
        T r = handleWork(input);
        if (successor != null) {
            return successor.handler(r);
        }
        return r;
    }

    abstract protected T handleWork(T input);

}
