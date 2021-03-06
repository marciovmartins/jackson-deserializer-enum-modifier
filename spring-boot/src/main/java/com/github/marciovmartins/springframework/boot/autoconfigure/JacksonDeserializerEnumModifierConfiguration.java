package com.github.marciovmartins.springframework.boot.autoconfigure;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.marciovmartins.jackson.databind.deser.BeanDeserializerModifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonDeserializerEnumModifierConfiguration {
  @Bean
  Module beanSerializerModifierBean() {
    return new SimpleModule().setDeserializerModifier(new BeanDeserializerModifier());
  }
}
