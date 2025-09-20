package ca.mikhailov.future.app;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public class MyAsyncBean {

    @Async("executor1")
    public CompletableFuture<Void> runTask() {
        String name = Thread.currentThread().getName();
        System.out.printf("Running task thread: %s\n", name);

        if ("FutureExecutor-7".equals(name)) {
            throw new RuntimeException("Holy molly! Exception in thread: " + name);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Restore interrupt status and complete exceptionally
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        }

        System.out.printf("Finished task  thread: %s\n", name);
        return CompletableFuture.completedFuture(null);
    }
}
