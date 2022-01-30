import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnumDeserializerTest {
  @Test
  void deserializeToRecordClass() throws Throwable {
    // setup
    var objectMapper = getObjectMapper();
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

  @Test
  void deserializeToRecordWithInvalidEnumOptionShouldFail() throws Throwable {
    // setup
    var objectMapper = getObjectMapper();
    var json = """
            {
              "id": 1,
              "name": "theUser",
              "type": "INVALID"
            }
            """;
    // execution && assertions
    assertThatThrownBy(() -> objectMapper.readValue(json, UserAsRecord.class))
            .isInstanceOf(InvalidFormatException.class)
            .hasMessage("\"%s\" is not one of the values accepted: %s".formatted("INVALID", "[REGULAR, PREMIUM]"));
  }

  private ObjectMapper getObjectMapper() {
    var module = new SimpleModule();
    module.setDeserializerModifier(new BeanDeserializerModifier(false));
    return new ObjectMapper().registerModule(module);
  }
}
