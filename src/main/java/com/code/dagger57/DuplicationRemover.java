package com.code.dagger57;

import org.springframework.beans.factory.annotation.Autowired;

public class DuplicationRemover implements Runnable {
    private final Buffer readBuffer;
    private final Buffer outputBuffer;
    private final DatabaseDao dao;

    @Autowired
    KeyStrategy keyStrategy;

    public DuplicationRemover(Buffer buffer, Buffer outputBuffer, DatabaseDao dao){
        this.readBuffer = buffer;
        this.outputBuffer = outputBuffer;
        this.dao = dao;
    }

    @Override
    public void run() {
        int timeoutCount = 0;
        while(true){
            try {
                String line = readBuffer.get();
                if(line == null){
                    break;
                }
                if (dao.insert(keyStrategy.generateKey(line), line)) {
                    outputBuffer.add(line);
                }
            }catch (InterruptedException e){
                break;
            }
        }
    }
}
