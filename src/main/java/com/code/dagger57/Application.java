package com.code.dagger57;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application implements CommandLineRunner{

    public static int MAX_THREADS = 3;

    @Autowired
    AnnotationConfigApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        String file;
        if(args.length > 0) {
            file = args[0];
        }else{
            file = "/home/dagger57/Downloads/dictionary.txt";
        }
        SpringApplication.run(Application.class, file);

    }

    private void run(String file) throws Exception {

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS);
        FileReaderRunnable reader = applicationContext.getBean(FileReaderRunnable.class);
        reader.setFile(new File(file));
        threadPoolExecutor.execute(reader);
        for(int i = 0; i < MAX_THREADS-2 ; i++){
            threadPoolExecutor.execute(applicationContext.getBean(DuplicationRemover.class));
        }
        Thread.sleep(1000);
        OutputFileWriter outputFileWriter = applicationContext.getBean(OutputFileWriter.class);
        outputFileWriter.setOutput(new File(file + ".output"));
        threadPoolExecutor.execute(outputFileWriter);
        threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
        threadPoolExecutor.shutdown();
    }

    @Override
    public void run(String... args) throws Exception {
        run(args[0]);
        applicationContext.registerShutdownHook();
    }
}
