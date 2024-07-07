package com.lec.spring.config;

import com.lec.spring.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // OAuth2 Client
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;


    // ↓ SecurityFilterChain 을 Bean 으로 등록해서 사용
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/customer/BookingList/**").authenticated()
                        .requestMatchers("/mypage/provider/**").hasAnyRole("PROVIDER")
                        .requestMatchers("mypage/manager/MemberManagement").hasAnyRole("MASTER")
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                                .loginPage("/user/Login")
                                .loginProcessingUrl("/user/Login")
                                .defaultSuccessUrl("/")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .successHandler(new CustomLoginSuccessHandler("/Home"))
                                .failureHandler(new CustomLoginFailureHandler())
                )


                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(false)
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())

                )


                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )

//                 OAuth2 로그인
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .loginPage("/user/Login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(principalOauth2UserService)
                        )
                )


                .build();

    } // end filterChain()

    // OAuth 로그인
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
