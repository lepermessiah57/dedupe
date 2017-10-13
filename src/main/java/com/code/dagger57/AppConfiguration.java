package com.code.dagger57;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableAutoConfiguration
public class AppConfiguration {

    @Bean
    public MongoDao mongoDao(){
        return new MongoDao();
    }

    @Bean(name="readBuffer")
    @Primary
    public Buffer readBuffer(){
        return new Buffer();
    }

    @Bean(name="writeBuffer")
    public Buffer writeBuffer(){
        return new Buffer();
    }

    @Bean
    public OutputFileWriter outputFileWriter(@Qualifier(value="writeBuffer") Buffer buffer){
        return new OutputFileWriter(buffer);
    }

    @Bean
    @Scope("prototype")
    public DuplicationRemover mongoLoader(Buffer buffer,@Qualifier(value="writeBuffer") Buffer outputBuffer,  MongoDao dao){
        return new DuplicationRemover(buffer, outputBuffer, dao);
    }

    @Bean
    public FileReaderRunnable fileReaderRunnable(Buffer buffer){
        return new FileReaderRunnable(buffer);
    }

    @Bean
    @Scope("prototype")
    public KeyStrategy keyStrategy(){
        return new KeyStrategy();
    }
}
