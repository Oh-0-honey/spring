package com.spring.test.config.auth;

import com.spring.test.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@RequiredArgsConstructor
@EnableWebSecurity//spring security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().headers().frameOptions().disable()//h2-console 화면을 사용하기 위해 disable해야 하는 것들
                .and()
                    .authorizeRequests()//URL별 권한 관리 설정하는 옵션의 시작점
                    .antMatchers("/","/css/**","/image/**","/js/**","/h2-console/**","/profile").permitAll()//지정된 URL에 전체 열람 권한 부여
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())//USER 권한만 가능하도록
                    .anyRequest().authenticated()//그 외 URL에 대해서는 모두 인증된 사용자들(로그인)에게만 가능하게
                .and()
                    .logout()
                        .logoutSuccessUrl("/")//로그아웃 기능 설정, 로그아웃 성공시 메인으로 이동
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);//oAuth2 로그인 기능 설정, 성공 이후 사용자 정보 불러오고(endpoint), userService의 후속 조치 실행


    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher(){
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

}
