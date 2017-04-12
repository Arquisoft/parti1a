package asw;


import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
	public static final Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] args) throws IOException {
    	SpringApplication.run(Application.class, args);
    }

}