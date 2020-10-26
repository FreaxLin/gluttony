package top.interc.crawler.storage;

public interface EmbeddedQueue<T> {

    public boolean put(T data);

    public T getFirst();

    public T getLast();

    public T get(int index);

    public int size();
}
