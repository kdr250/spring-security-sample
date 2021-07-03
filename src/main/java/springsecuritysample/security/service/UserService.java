package springsecuritysample.security.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService(JdbcUserDetailsManager jdbcUserDetailsManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signUp(String username, String password) {
        UserDetails user = User
                .withUsername(username)
                .password(bCryptPasswordEncoder.encode(password))
                .authorities("ROLE_USER")
                .build();
        jdbcUserDetailsManager.createUser(user);
    }

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
