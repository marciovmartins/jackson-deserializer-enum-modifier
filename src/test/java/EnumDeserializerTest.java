import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumDeserializerTest {
  @Test
  void deserializeEnumToRecordClass() throws JsonProcessingException {
    // setup
    var objectMapper = new ObjectMapper();
    var json = """
            {
              "id": 1,
              "name": "theUser",
              "type": "REGULAR"
            }
            """;
    var expected = new UserAsRecord(1, "theUser", UserAsRecord.Type.REGULAR);
    // execution
    var result = objectMapper.readValue(json, UserAsRecord.class);
    // assertions
    assertThat(result).isEqualTo(expected);
  }
}
