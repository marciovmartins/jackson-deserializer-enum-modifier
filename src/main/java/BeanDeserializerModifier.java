import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class BeanDeserializerModifier
    extends com.fasterxml.jackson.databind.deser.BeanDeserializerModifier {
  private final boolean caseInsensitive;

  public BeanDeserializerModifier(boolean caseInsensitive) {
    this.caseInsensitive = caseInsensitive;
  }

  @Override
  public JsonDeserializer<?> modifyEnumDeserializer(
      DeserializationConfig config,
      JavaType type,
      BeanDescription beanDesc,
      JsonDeserializer<?> deserializer) {
    if (deserializer instanceof com.fasterxml.jackson.databind.deser.std.EnumDeserializer deserializerAsEnumDeserializer) {
      return super.modifyEnumDeserializer(config, type, beanDesc, new EnumDeserializer(deserializerAsEnumDeserializer, caseInsensitive));
    }
    return super.modifyEnumDeserializer(config, type, beanDesc, deserializer);
  }

  private static class EnumDeserializer extends com.fasterxml.jackson.databind.deser.std.EnumDeserializer {
    protected EnumDeserializer(com.fasterxml.jackson.databind.deser.std.EnumDeserializer base, boolean caseInsensitive) {
      super(base, caseInsensitive);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      try {
        return super.deserialize(p, ctxt);
      } catch (com.fasterxml.jackson.databind.exc.InvalidFormatException ex) {
        throw new InvalidFormatException((JsonParser) ex.getProcessor(), ex.getLocalizedMessage(), ex.getValue(), ex.getTargetType());
      }
    }
  }
}
