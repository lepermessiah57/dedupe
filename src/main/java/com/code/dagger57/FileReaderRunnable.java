package com.code.dagger57;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReaderRunnable implements Runnable {

    Buffer buffer;
    File file;

    public FileReaderRunnable(Buffer buffer) {
        this.buffer = buffer;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        int count = 0;
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(file.toURI()))) {
            while(reader.ready()){
                String[] pieces = reader.readLine().split(" ");
                for(String s : pieces) {
                    buffer.add(s);
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Words written from file: " + count);
    }
}
