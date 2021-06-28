package springsecuritysample.json;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonMapperTest {
    @Test
    void JSON文字列をオブジェクトに変換できる() {
        String json = "{\n" +
                "    \"username\": \"taro\",\n" +
                "    \"password\": \"password123\"\n" +
                "}";
        JSONObject jsonObject = new JSONObject(json);
        assertEquals("akihiro", jsonObject.get("username"));
    }
}
