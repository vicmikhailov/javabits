package ca.mikhailov.future.app;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
@EnableConfigurationProperties(FutureExecutorProperties.class)
public class DemoAsyncBeanConfig implements AsyncConfigurer {

    private final FutureExecutorProperties props;

    public DemoAsyncBeanConfig(FutureExecutorProperties props) {
        this.props = props;
    }

    /**
     * This bean has the asyc methods
     *
     * @return MyAsyncBean
     */
    @Bean
    public MyAsyncBean asyncBean() {
        return new MyAsyncBean();
    }

    /**
     * Custom Executor configured via properties
     *
     * Back-pressure and rejection policy:
     * - Use a bounded queue (configured via properties)
     * - When the pool and queue are saturated, run tasks on the calling thread (CallerRunsPolicy)
     *   to apply back-pressure and avoid silent task drops.
     *
     * @return Executor
     */
    @Bean("executor1")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(props.getCorePoolSize());
        executor.setMaxPoolSize(props.getMaxPoolSize());
        executor.setQueueCapacity(props.getQueueCapacity());
        executor.setThreadNamePrefix(props.getThreadNamePrefix());
        // Back-pressure on saturation
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // Ensure graceful shutdown: wait for tasks to complete and bound the wait time
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }

    /**
     * Custom exception handler
     *
     * @return AsyncUncaughtExceptionHandler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new DemoAsyncUncaughtExceptionHandler();
    }
}
