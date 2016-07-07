package com.tradeshift.thirdparty.samples.springboot;

import com.tradeshift.thirdparty.samples.springboot.controllers.TokenController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.tradeshift.thirdparty.samples.springboot")
public class DemoApplication {

    static Logger LOGGER = LoggerFactory.getLogger(TokenController.class);

    public static String clientID = null;
    public static String clientSecret = null;

    public static void main(String[] args) {
        if (args.length > 0 && args[0] != null) {
            clientID = args[0];
        } else {
            LOGGER.error("Client ID argument must not be empty", DemoApplication.class);
            throw new NullPointerException();
        }

        if (args.length > 0 && args[1] != null) {
            clientSecret = args[1];
        } else {
            LOGGER.error("Client Secret argument must not be empty", DemoApplication.class);
            throw new NullPointerException();
        }


        SpringApplication.run(DemoApplication.class, args);
    }
}
