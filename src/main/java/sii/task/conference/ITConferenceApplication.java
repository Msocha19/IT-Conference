package sii.task.conference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ITConferenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ITConferenceApplication.class, args);
	}
}
