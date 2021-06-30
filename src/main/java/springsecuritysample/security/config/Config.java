package springsecuritysample.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springsecuritysample.security.filter.UsernamePasswordJsonAuthenticationFilter;
import springsecuritysample.security.handler.ApiAuthenticationSuccessHandler;

@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(usernamePasswordJsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(customizer ->
                    customizer
                        .mvcMatchers("/api/login").permitAll()
                        .anyRequest().denyAll()
                )
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().withUser("taro").password("password123").roles("USER")
                .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    private UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationFilter() throws Exception {
        UsernamePasswordJsonAuthenticationFilter filter =
                new UsernamePasswordJsonAuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/api/login");
        filter.setAuthenticationSuccessHandler(new ApiAuthenticationSuccessHandler());
        return filter;
    }

}
