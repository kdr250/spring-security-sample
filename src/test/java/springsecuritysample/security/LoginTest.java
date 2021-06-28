package springsecuritysample.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 認証しないとUnAuthorizedを返す() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/")
        ).andExpect(status().isUnauthorized());
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
}
