package springsecuritysample.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void ユーザーを新規登録できる() {
        userService.signUp("taro", "password123");

        UserDetails user = jdbcUserDetailsManager.loadUserByUsername("taro");

        assertEquals("taro", user.getUsername());
    }
}
