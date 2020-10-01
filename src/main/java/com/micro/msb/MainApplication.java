package com.micro.msb;

import com.micro.msb.queue.Balancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import java.util.concurrent.Executors;

/**
 *
 * @author T420
 */
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    @Autowired
    private Balancer balancer;
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        
        Executors.newFixedThreadPool(1).execute(balancer);
    }
}
