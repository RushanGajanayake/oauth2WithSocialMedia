package com.oauth2sociallogin.demo.security;

import com.oauth2sociallogin.demo.ClientResources;
import com.oauth2sociallogin.demo.Utill.DateUtill;
import com.oauth2sociallogin.demo.model.User;
import com.oauth2sociallogin.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import java.security.Principal;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by rusha on 7/10/2018.
 */
@Configuration
@EnableOAuth2Client
@RestController
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    UserRepository userRepository;

//    @RequestMapping("/user")
//    public Principal user(Principal principal) {
//        System.out.println(principal);
////        User user = userRepository.findUserByUserId();
//        return principal;
//    }

    @RequestMapping("/user")
    public Authentication user(OAuth2Authentication authentication) {
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        User user = userRepository.findUserByUserId((String) properties.get("id"));
        if(user == null){
            user = new User();
            user.setName((String) properties.get("name"));
            user.setEmail((String) properties.get("email"));
            user.setBirthday((String) properties.get("birthday"));
            user.setUserId((String) properties.get("id"));

            user.setCreatedBy(1);
            user.setCreatedDate(DateUtill.getCurrentDate());
            user.setModifiedBy(1);
            user.setModifiedDate(DateUtill.getCurrentDate());

            User resultUser = userRepository.save(user);
        }
        return authentication;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**","/webjars/**", "/error**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
        tokenServices.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(tokenServices);
        return facebookFilter;
    }

    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties facebookResource() {
        return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("facebook")
    public ClientResources facebookRes() {
        return new ClientResources();
    }
}
