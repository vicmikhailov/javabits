package ca.mikhailov.hello.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import ca.mikhailov.hello.service.MyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private MyService myService;

  @BeforeEach
  void setUp() {
    given(myService.message()).willReturn("Hello, World!");
  }

  @Test
  void homeEndpointReturnsMessage() {
    ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo("Hello, World!");
  }

  @Test
  void homeEndpointHandlesNullMessage() {
    given(myService.message()).willReturn(null);
    ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNull();
  }
}
