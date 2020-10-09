package yimin.ego.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class CookieConfig {

    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer cookie = new DefaultCookieSerializer();
        cookie.setCookieName("TT_TOKEN");
        cookie.setUseHttpOnlyCookie(false);
        return cookie;
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver(){
        CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
        resolver.setCookieSerializer(cookieSerializer());
        return resolver;
    }
}
