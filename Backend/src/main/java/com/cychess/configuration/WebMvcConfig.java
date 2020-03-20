package com.cychess.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configures the encoder used for password security
 * @author bbanothu
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * Constructs and returns a new Password encoder
	 * @return bCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

}