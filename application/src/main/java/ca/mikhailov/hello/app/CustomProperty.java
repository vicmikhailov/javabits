package ca.mikhailov.hello.app;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;

@Configuration
public class CustomProperty {

  // write a bean that will set a custom property value
  // that will be resloved and bound in application.yml property
  // named service.message
  @Autowired
  private Environment environment;

  @Bean
  public MapPropertySource customPropertySource() {
    Map<String, Object> source = new HashMap<>();
    source.put("service.message", "Custom message from custom property source");
    return new MapPropertySource("customPropertySource", source);
  }
}
