package ca.mikhailov.future.app;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "future.executor")
public class FutureExecutorProperties {

    /**
     * Core pool size for the async executor.
     */
    private int corePoolSize = 7;

    /**
     * Max pool size for the async executor.
     */
    private int maxPoolSize = 42;

    /**
     * Queue capacity for the async executor.
     */
    private int queueCapacity = 11;

    /**
     * Thread name prefix for the async executor.
     */
    private String threadNamePrefix = "FutureExecutor-";

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }
}
