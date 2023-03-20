package com.example.hotelantique.config;

import com.example.hotelantique.model.enums.RoleEnum;
import com.example.hotelantique.repository.UserRepository;
import com.example.hotelantique.service.ApplicationUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //Defines which pages will be authorized
                .authorizeHttpRequests()
                //Static resources
        .requestMatchers("/static/assets/**", "/static/node_modules/**", "/static/scss/**").permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers("/", "/login", "/register").permitAll()
                    .requestMatchers("/pages/admin").hasRole(RoleEnum.ADMIN.name())
                .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                    .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/home", true)
                .failureForwardUrl("/login-error")
                     .and()
                     .logout()
                     .logoutUrl("/logout")
                     .logoutSuccessUrl("/")
                     .deleteCookies("JSESSIONID")
                     .clearAuthentication(true);



        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new ApplicationUserDetailsService(userRepository);
    }

}
