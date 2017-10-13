package com.code.dagger57;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Buffer {

    public static int BUFFER_SIZE = 1024 * 1024;
    private final ArrayBlockingQueue<String> blockingQueue;

    public Buffer(){
        this(BUFFER_SIZE);
    }

    public Buffer(int bufferSize){
        blockingQueue = new ArrayBlockingQueue<>(bufferSize);
    }

    public void add(String line){
        blockingQueue.offer(line);
    }

    public String get() throws InterruptedException {
        return blockingQueue.poll(10, TimeUnit.SECONDS);
    }

    public int size(){
        return blockingQueue.size();
    }


}
