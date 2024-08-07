package ca.mikhailov.future.app;

import static java.lang.System.exit;

import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoEnableAsyc {

  MyAsyncBean asyncBean;

  public static void main(String[] args) {
    SpringApplication.run(DemoEnableAsyc.class, args);
  }

  @Autowired
  public void setAsyncBean(MyAsyncBean asyncBean) {
    this.asyncBean = asyncBean;
  }

  @Bean
  public CommandLineRunner runner() {
    return x -> {
      System.out.println("Runner starts....");

      IntStream.range(0, 10).forEach(i -> asyncBean.runTask());

      Thread.sleep(20000);
      exit(0);
    };
  }
}
