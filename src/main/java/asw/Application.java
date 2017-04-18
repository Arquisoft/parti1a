package asw;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import asw.dbManagement.repository.InitParticipantRepository;
import asw.dbManagement.repository.ParticipantRepository;

@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
public class Application {
	public static final Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] args) throws IOException {
    	SpringApplication.run(Application.class, args);
    }

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    @Bean
	public CommandLineRunner initParticipantRepository(ParticipantRepository repository) throws ParseException {
    	return (args) -> { InitParticipantRepository.initDB(repository); };
    }
    
}