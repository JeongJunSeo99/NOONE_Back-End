/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.techconnection.noone.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
/*
@Slf4j
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf().disable().cors().disable()
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(
						HttpMethod.GET,
						"**"
				).permitAll()
				.requestMatchers(
						HttpMethod.POST,
						"**"
				).permitAll()
				//.requestMatchers("/cms", "/cms/**").hasRole("ADMIN")
				.anyRequest().authenticated()

			);

		return http.build();
	}
}
*/


