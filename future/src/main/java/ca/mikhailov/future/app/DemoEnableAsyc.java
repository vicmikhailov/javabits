package ca.mikhailov.future.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@SpringBootApplication
public class DemoEnableAsyc {

    MyAsyncBean asyncBean;
    ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(DemoEnableAsyc.class, args);
    }

    @Autowired
    public void setAsyncBean(MyAsyncBean asyncBean) {
        this.asyncBean = asyncBean;
    }

    @Autowired
    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Bean
    public CommandLineRunner runner() {
        return x -> {
            System.out.println("Runner starts....");

            CompletableFuture<Void>[] futures = IntStream.range(0, 10)
                    .mapToObj(i -> asyncBean.runTask())
                    .toArray(CompletableFuture[]::new);

            CompletableFuture<Void> all = CompletableFuture.allOf(futures);

            try {
                all.join();
            } catch (Exception e) {
                System.out.println("One or more tasks completed exceptionally: " + e);
            }

            // Close the Spring context to trigger graceful shutdown of executors
            context.close();
        };
    }
}
