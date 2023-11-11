package com.luv2code.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests(configurer ->
                configurer
                        .antMatchers("/api/books/secure/**",
                                "/api/reviews/secure/**",
                                "/api/messages/secure/**",
                                "/api/admin/secure/**")
                        .authenticated()).oauth2ResourceServer().jwt();

        http.cors();

        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }
/*client에서 octa로부터 id와 path 받고
spring server에서 미리 octa id와 octa path를 설정해두고
만약 로그인을 하게 되면 JWT(해싱된것)가 spring server로 전달 되고
이 JWT의 PAYLOAD에 존재하게 되는 여러가지 정보(name, email 등)와 더불어
octa id와 octa path가 전달 되고,
이걸 받은 spring sever는 어떤 동작을 실행시키는 것이구나
* */
}
