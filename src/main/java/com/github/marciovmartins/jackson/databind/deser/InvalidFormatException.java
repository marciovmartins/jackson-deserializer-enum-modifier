package com.github.marciovmartins.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;

import java.util.regex.Pattern;

public class InvalidFormatException
    extends com.fasterxml.jackson.databind.exc.InvalidFormatException {
  public InvalidFormatException(JsonParser p, String msg, Object value, Class<?> targetType) {
    super(p, msg, value, targetType);
  }

  @Override
  public String getMessage() {
    var pattern = Pattern.compile("Enum class: \\[(.*?)]");
    var matcher = pattern.matcher(super.getMessage());
    if (matcher.find()) {
      var enumValues = matcher.group(1);
      return "\"%s\" is not one of the values accepted: [%s]".formatted(getValue(), enumValues);
    }
    var className = this.getClass().getSuperclass().getCanonicalName();
    throw new IllegalStateException(
        "%s message changed! This library must be updated.".formatted(className));
  }
}
