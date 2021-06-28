package springsecuritysample.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springsecuritysample.security.UsernamePasswordJsonAuthenticationFilter;

@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(usernamePasswordJsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationFilter() {
        UsernamePasswordJsonAuthenticationFilter filter =
                new UsernamePasswordJsonAuthenticationFilter();
        filter.setFilterProcessesUrl("/api/login");
        return filter;
    }

}
