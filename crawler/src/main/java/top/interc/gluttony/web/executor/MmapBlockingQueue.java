package top.interc.gluttony.web.executor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mapdb.Serializer;
import top.interc.gluttony.web.controller.CrawlConfig;
import top.interc.gluttony.web.storage.EmbeddedQueue;
import top.interc.gluttony.web.storage.PreCrawlUrlQueue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 11:40
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class MmapBlockingQueue<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, java.io.Serializable {

    private EmbeddedQueue<E> queues;

    private CrawlConfig config;

    private final Condition notEmpty;

    private final ReentrantLock lock;

    public MmapBlockingQueue(CrawlConfig config, Serializer<E> serializer) {
        this.config = config;
        queues = new PreCrawlUrlQueue<>(config, serializer);
        this.lock = new ReentrantLock();
        this.notEmpty = this.lock.newCondition();
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return queues.size();
    }

    public boolean add(E e) {
        return super.add(e);
    }

    @Override
    public void put(@NotNull E e) throws InterruptedException {
        checkNotNull(e);
        lock.lock();
        try {
            enqueue(e);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean offer(E e, long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        checkNotNull(e);
        lock.lock();
        try {
            return enqueue(e);
        }finally {
            lock.unlock();
        }

    }

    @NotNull
    @Override
    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (this.queues.size() == 0)
                notEmpty.await();
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    @Nullable
    @Override
    public E poll(long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (this.queues.size() == 0) {
                if (nanos <= 0)
                    return null;
                nanos = notEmpty.awaitNanos(nanos);
            }
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int remainingCapacity() {
        return -1;
    }

    @Override
    public int drainTo(@NotNull Collection<? super E> c) {
        return 0;
    }

    @Override
    public int drainTo(@NotNull Collection<? super E> c, int maxElements) {
        return 0;
    }

    @Override
    public boolean offer(E e) {
        lock.lock();
        try {
            return enqueue(e);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public E poll() {
        return (this.queues.size() == 0) ? null : dequeue();
    }

    @Override
    public E peek() {
        return null;
    }

    private boolean enqueue(E x) {
        queues.put(x);
        notEmpty.signal();
        return true;
    }

    private E dequeue(){
        return this.queues.poll();
    }

    private static void checkNotNull(Object v) {
        if (v == null)
            throw new NullPointerException();
    }
}
