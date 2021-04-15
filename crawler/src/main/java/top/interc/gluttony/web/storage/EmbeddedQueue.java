package top.interc.gluttony.web.storage;

public interface EmbeddedQueue<T> {

    public boolean put(T data);

    public T getFirst();

    public T getLast();

    public T poll();

    public T get(int index);

    public int size();
}
