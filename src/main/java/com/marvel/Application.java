package com.marvel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.Charset;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.boot.CommandLineRunner;

import lombok.extern.slf4j.Slf4j;



@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner{
	@Autowired
	private Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		String application = env.getProperty("spring.application.name");
		String environment = env.getProperty("spring.profiles.active");
		String version = env.getProperty("info.version");
		String app = String.format("%s-%s in %s", application, version, environment);
//		log.info("Started Application...  {}, Charset: {} in Timezone: {} [{}] {}", app, Charset.defaultCharset(), TimeZone.getDefault().getDisplayName(), ZoneId.systemDefault(),  OffsetDateTime.now(ZoneId.systemDefault()));
//		log.info("Endpoint configuration: ");
	}

}
