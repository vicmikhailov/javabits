package ca.mikhailov.future.app;

import java.util.concurrent.Executor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class DemoAsyncBeanConfig implements AsyncConfigurer {

  /**
   * This bean has the asyc methods
   * @return MyAsyncBean
   */
  @Bean
  public MyAsyncBean asyncBean() {
    return new MyAsyncBean();
  }

  /**
   * Custorm Executor
   * @return Executor
   */
  @Bean("executor1")
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(7);
    executor.setMaxPoolSize(42);
    executor.setQueueCapacity(11);
    executor.setThreadNamePrefix("FutureExecutor-");
    executor.initialize();
    return executor;
  }

  /**
   * Custom exception handler
   * @return AsyncUncaughtExceptionHandler
   */
  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return new DemoAsyncUncaughtExceptionHandler();
  }

}
