package ru.DenWorker.PP_3_1_SpringBoot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final AuthProviderImpl authProvider;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    public WebSecurityConfig(AuthProviderImpl authProvider, CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.authProvider = authProvider;
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests
                ((authorize) -> authorize
                        .requestMatchers("/auth/**", "/static/**",
                                "/logout").permitAll()
                        .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN"));


        http.formLogin(form -> form
                        .loginPage("/auth/login")
                        .failureUrl("/auth/error")
                        .successHandler(customLoginSuccessHandler))
                .httpBasic(withDefaults())
                .authenticationProvider(authProvider);

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login"));

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}