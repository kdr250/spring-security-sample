package springsecuritysample.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import springsecuritysample.security.service.UserService;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthorizationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
        userService.signUp("taro", "password123");
    }

    @Test
    void USER権限を持っている人はUSER権限が必要なパスにリクエストできる() throws Exception {
        String json = "{\n" +
                "    \"username\": \"taro\",\n" +
                "    \"password\": \"password123\"\n" +
                "}";
        MvcResult loginResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
        )
                .andExpect(status().isNoContent())
                .andReturn();

        Cookie cookie = loginResult.getResponse().getCookie("SESSION");

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user")
                    .cookie(cookie)
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("USER画面です", result.getResponse().getContentAsString());
    }

    @Test
    void USER権限を持っている人はADMIN権限が必要なパスにリクエストできない() throws Exception {
        String json = "{\n" +
                "    \"username\": \"taro\",\n" +
                "    \"password\": \"password123\"\n" +
                "}";
        MvcResult loginResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isNoContent())
                .andReturn();

        Cookie cookie = loginResult.getResponse().getCookie("SESSION");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/admin")
                        .cookie(cookie)
        )
                .andExpect(status().isForbidden());
    }
}
