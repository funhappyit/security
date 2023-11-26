package com.example.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
public class SecurityConfig{

	//해당 메서드의 리턴되는 오브젝트를 ioc로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


//		http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
//			authorizationManagerRequestMatcherRegistry
//					.requestMatchers("/user/**").hasAnyRole("USER","MANAGER","ADMIN")
//					.requestMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
//					.requestMatchers("/admin/**").hasAnyRole("ADMIN").anyRequest().permitAll();
//		});
		http.csrf(cs->cs.disable());
		http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
			authorizationManagerRequestMatcherRegistry
					.requestMatchers("/user/**").authenticated()
					.requestMatchers("/manager").hasAnyRole("ADMIN", "MANAGER") // manager쪽으로 들어올려면 어드민 or 매니저
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().permitAll();
		});
		http.formLogin(f->f.loginPage("/loginForm").loginProcessingUrl("/login").defaultSuccessUrl("/"));



//				.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.formLogin(f->f.loginPage("/loginForm").loginProcessingUrl("/login"));
//		http.csrf().disable();
//		http
//				.authorizeHttpRequests()
//				.requestMatchers("/user/**").authenticated() // user로 들어올려면 인증 필요
//				.requestMatchers("/manager/**").hasAnyRole("ADMIN or MANAGER") // manager쪽으로 들어올려면 어드민 or 매니저
//				.requestMatchers("/admin/**").hasRole("ADMIN") // admin은 권한 있는 사람만 들어와 /hasrole은 단일 권한
//				.anyRequest().permitAll()
//				.and()
//				.formLogin() //기본 로그인 페이지가 노출되지 않고 바로 접근이 가능
//				.loginPage("/loginForm")
//				.loginProcessingUrl("/login")
//				.defaultSuccessUrl("/");

		return http.build();
	}

}
