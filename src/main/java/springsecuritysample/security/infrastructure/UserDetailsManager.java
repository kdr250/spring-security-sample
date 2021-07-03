package springsecuritysample.security.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class UserDetailsManager {
    private final DataSource dataSource;

    UserDetailsManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        return new JdbcUserDetailsManager(dataSource);
    }
}
