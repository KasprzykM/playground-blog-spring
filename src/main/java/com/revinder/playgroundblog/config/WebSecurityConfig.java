package com.revinder.playgroundblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginSuccessHandler loginSuccessHandler;

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(LoginSuccessHandler loginSuccessHandler, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/**")
                    .authenticated()
                .and()
                    .httpBasic()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                    .formLogin()
                    .loginPage("/api/login")
                    .successHandler(loginSuccessHandler)
                    .failureHandler(new SimpleUrlAuthenticationFailureHandler());

        // POST + PUT Wont work with csrf enabled from postman alone.
        http.csrf().disable();
//        http.csrf().ignoringAntMatchers("/api/login")
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }


    @Bean
    public UserDetailsService userDetailsService()
    {
        String pwd = "pwd";
        String username = "user";
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        String encodedPwd = passwordEncoder().encode(pwd);

        manager.createUser(User.withUsername(username).password(encodedPwd).roles("USER").build());
        return manager;
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
