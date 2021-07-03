package springsecuritysample.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import springsecuritysample.security.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService.signUp("taro", "password123");
    }

    @Test
    void 認証しないとForbiddenを返す() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/")
        ).andExpect(status().isForbidden());
    }

    @Test
    void 認証することができる() throws Exception {
        String json = "{\n" +
                "    \"username\": \"taro\",\n" +
                "    \"password\": \"password123\"\n" +
                "}";
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNoContent());
    }

    @Test
    void 誤ったusernameとpasswordだと認証失敗する() throws Exception {
        String json = "{\n" +
                "    \"username\": \"taro1\",\n" +
                "    \"password\": \"password123\"\n" +
                "}";
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isUnauthorized());
    }
}
