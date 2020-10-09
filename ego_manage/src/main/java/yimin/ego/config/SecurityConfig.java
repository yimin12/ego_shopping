package yimin.ego.config;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/9/30 21:20
 *   @Description :
 *
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login")
                .successForwardUrl("/loginSuccess")
                .loginPage("/");

        http.authorizeRequests()
                // 千万不要忘记放行静态资源
                .antMatchers("/","/css/**","/js/**").permitAll()
                .anyRequest().authenticated();

        http.csrf().disable();
        // 不要在响应头中添加X-Frame-Options: DENY
        http.headers().frameOptions().disable();
    }
}
