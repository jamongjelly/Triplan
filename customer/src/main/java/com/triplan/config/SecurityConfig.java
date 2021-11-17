package com.triplan.config;

import com.triplan.security.JwtTokenCheckFilter;
import com.triplan.security.MemberDetailsService;
import com.triplan.security.OAuth2RequestCookieRepository;
import com.triplan.security.oauth2.OAuth2FailureHandler;
import com.triplan.security.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberDetailsService memberDetailsService;

    private final OAuth2RequestCookieRepository oAuth2RequestCookieRepository;
    private final DefaultOAuth2UserService oAuth2UserService;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final JwtTokenCheckFilter jwtTokenCheckFilter;

    @Value("${app.resources.commonUrl}")
    private final String RESOURCES_COMMON_URL;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable()
                .httpBasic().disable()
                .csrf().disable();

        http.authorizeRequests()
                .antMatchers("/**").permitAll();

        http.oauth2Login()
                .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(oAuth2RequestCookieRepository)
                    .and()
                .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                    .and()
                .failureHandler(oAuth2FailureHandler)
                    .userInfoEndpoint()
                    .userService(oAuth2UserService)
                    .and()
                .successHandler(oAuth2SuccessHandler);

        http.addFilterAt(jwtTokenCheckFilter, BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(RESOURCES_COMMON_URL + "/**")
                .antMatchers("/h2-console/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}