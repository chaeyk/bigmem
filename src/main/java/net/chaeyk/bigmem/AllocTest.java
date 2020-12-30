package net.chaeyk.bigmem;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AllocTest extends Thread {

    private final int threads;
    private final int size;
    private final int count;

    @Slf4j
    public static class WorkerThread extends Thread {

        private final int size;
        private final ArrayList<Object> objects;

        public WorkerThread(ThreadGroup group, int size, int count) {
            super(group, "alloc_worker");
            this.size = size;
            this.objects = new ArrayList<>(count);
        }

        @Override
        public void run() {
            for (int i = 0; i < objects.size(); i++) {
                ByteBuffer buf = ByteBuffer.allocateDirect(size);
                objects.set(i, buf);
                for (int j = 0; j < size; j++) {
                    buf.put((byte) 1);
                }
                buf.flip();
            }
            for (int i = 0; i < objects.size(); i++) {
                ByteBuffer buf = (ByteBuffer) objects.get(i);
                for (int j = 0; j < size; j++) {
                    if (buf.get() != 1) {
                        throw new RuntimeException("Oh...");
                    }
                }
            }
        }
    }

    public AllocTest(int threads, int size, int count) {
        this.threads = threads;
        this.size = size;
        this.count = count;
    }

    @Override
    public void run() {
        for (int step = 0; step < 1000; step++) {
            ThreadGroup workerGroup = new ThreadGroup("alloc_test");
            List<Thread> workers = new ArrayList<>(threads);
            for (int i = 0; i < threads; i++) {
                workers.add(new WorkerThread(workerGroup, size, count));
            }
            workers.forEach(Thread::start);
            try {
                for (Thread worker : workers) {
                    worker.join();
                }
            } catch (InterruptedException e) {
                log.warn("test interrupted!");
                Thread.currentThread().interrupt();
            } finally {
                log.info("Test done - {}", step);
            }
        }
    }
}
