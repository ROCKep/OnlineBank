package ru.sbt.javaschool.group2.onlinebank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(@Qualifier("clientUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RequestMatcher csrfRequestMatcher = new RequestMatcher() {

            // Разрешенные HTTP методы
            private Pattern allowedMethods = Pattern.compile("^GET$");

            // URLы, на которых нужно отключить CSRF
            private AntPathRequestMatcher[] requestMatchers = new AntPathRequestMatcher[]{
                    new AntPathRequestMatcher("/api/**"),
            };

            @Override
            public boolean matches(HttpServletRequest request) {
                // Пропустить разрешенные методы
                if (allowedMethods.matcher(request.getMethod()).matches()) {
                    return false;
                }

                // Отключить CSRF на заданных URLах
                for (AntPathRequestMatcher rm : requestMatchers) {
                    if (rm.matches(request)) {
                        return false;
                    }
                }

                return true;
            }
        };

        http
                .csrf().requireCsrfProtectionMatcher(csrfRequestMatcher)
            .and()
                .authorizeRequests()
                .antMatchers("/register", "/login").anonymous()
                .antMatchers("/public/**", "/", "/api/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/client")
            .and()
                .logout();
    }
}