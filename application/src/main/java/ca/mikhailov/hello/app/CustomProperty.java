package ca.mikhailov.hello.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CustomProperty {

    private Environment environment;

    @Bean
    public MapPropertySource customPropertySource() {
        Map<String, Object> source = new HashMap<>();
        source.put("service.message", "Custom message from custom property source");
        return new MapPropertySource("customPropertySource", source);
    }
}
