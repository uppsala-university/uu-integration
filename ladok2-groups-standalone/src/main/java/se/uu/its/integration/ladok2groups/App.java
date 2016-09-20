package se.uu.its.integration.ladok2groups;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import se.uu.its.integration.ladok2groups.conf.DbConf;

@Import(value = { DbConf.class })
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class App {
    
    public App() {
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

}
