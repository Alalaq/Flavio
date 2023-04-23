package ru.kpfu.itis.khabibullin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringBootRestApiProjectApplication {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

//	@Bean
//	public CommandLineRunner run(DataSource dataSource) {
//		return args -> {
//			Resource resource = new ClassPathResource("/sql/data.sql");
//			ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiProjectApplication.class, args);
	}

}
