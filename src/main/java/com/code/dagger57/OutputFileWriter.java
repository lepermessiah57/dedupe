package com.code.dagger57;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OutputFileWriter implements Runnable{
    private final Buffer buffer;

    private File output;

    public OutputFileWriter(Buffer buffer) {
        this.buffer = buffer;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    @Override
    public void run() {
        try(BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(output.toURI()))) {
            int count = 0;
            while(true) {
                try {
                    String line = buffer.get();
                    if(line == null) break;
                    fileWriter.write(line);
                    fileWriter.newLine();
                    count++;
                }catch(InterruptedException e){
                    break;
                }
            }

            System.out.println("*** " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
