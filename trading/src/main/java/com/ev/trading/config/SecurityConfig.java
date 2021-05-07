package com.ev.trading.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService detailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/").permitAll();
//                .antMatchers("/toQuestions").hasAnyRole("visitor", "admin")
//                .antMatchers("/savePost").hasAnyRole("visitor", "admin");

//                .antMatchers("/toInfo").hasAnyRole("student","admin")
//                .antMatchers("/studentList").hasRole("admin")
//                .antMatchers("/addCourse/**","/updateCourse/**","/deleteCourse/**").hasRole("admin");

       http.formLogin()
               .loginPage("/toLogin");
//               .loginProcessingUrl("/userLogin");


        //注销
        http.csrf().disable();  //关闭csrf功能，解决注销失败
        http.logout().logoutSuccessUrl("/");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService);
    }




    @Bean  //注入PasswordEncode
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
