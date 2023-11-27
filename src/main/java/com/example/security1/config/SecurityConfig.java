package com.example.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true) //secure 어노테이션 활성화, preAuthorize 어노테이션 활성화
public class SecurityConfig{

	//해당 메서드의 리턴되는 오브젝트를 ioc로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf(cs->cs.disable());
		http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
			authorizationManagerRequestMatcherRegistry
					.requestMatchers("/user/**").authenticated()
					.requestMatchers("/manager").hasAnyRole("ADMIN", "MANAGER") // manager쪽으로 들어올려면 어드민 or 매니저
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().permitAll();
		});
		http.formLogin(f->f.loginPage("/loginForm").loginProcessingUrl("/login").defaultSuccessUrl("/"));
		http.oauth2Login(f->f.loginPage("/loginForm"));//구글 로그인이 완료됀 후처리가 필요함

		return http.build();
	}

}
