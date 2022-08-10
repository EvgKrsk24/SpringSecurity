package ru.java.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.java.springsecurity.model.Permission;
import ru.java.springsecurity.model.Role;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //включение аннотации @PreAuthorize в DeveloperRestControllerV1
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// защита от csrf атаки
                .authorizeRequests() // авторизация
                .antMatchers("/").permitAll() // к стартовой странице все имеют доступ
                // заменено аннотацией @PreAuthorize
               /* .antMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.DEVELOPERS_READ.getPermission()) // выдаем права на чтение метооду GET
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission()) // выдаем права на запись методу POST
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission()) // выдаем права на запись методу DELETE */
                .anyRequest()
                .authenticated() // каждый запрос должен быть аутентифицирован
                .and()
                .httpBasic(); // с помощью декодера base 64

    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() { //используется для извлечения данных, связанных с пользователем.
        return new InMemoryUserDetailsManager(
                User.builder() // строим юзера админа
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .authorities(Role.ADMIN.getAuthorities())// права из енам Role
                        .build(),
                User.builder() // строим юзера user
                        .username("user")
                        .password(passwordEncoder().encode("user"))
                        .authorities(Role.USER.getAuthorities()) // права из енам Role
                        .build()
        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {// инкодер для кодировки пароля в UserDetailsService
        return new BCryptPasswordEncoder(12);
    }
}
