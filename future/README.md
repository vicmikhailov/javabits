# Async Operations (Spring @Async Demo)

This module demonstrates how to run asynchronous tasks in a Spring Boot application using `@EnableAsync`, a custom
`ThreadPoolTaskExecutor`, and a custom `AsyncUncaughtExceptionHandler`.

## Goal

Provide a concise, runnable example that shows how to:

- Enable and configure asynchronous execution in Spring.
- Offload work to a dedicated thread pool with a custom name prefix.
- Handle exceptions thrown from `void`-returning `@Async` methods via a custom uncaught exception handler.

## How the solution works

The demo consists of four main parts:

- Configuration (Enable + Executor + Exception handler)
    - `DemoAsyncBeanConfig`:
        - `@EnableAsync` turns on Spring’s async capability.
        - Declares a bean `executor1` using `ThreadPoolTaskExecutor` with:
            - corePoolSize = 7, maxPoolSize = 42, queueCapacity = 11
            - thread name prefix: `FutureExecutor-`
            - rejection policy: CallerRuns (applies back-pressure by running saturated tasks on the caller thread)
        - Provides a custom `AsyncUncaughtExceptionHandler` (`DemoAsyncUncaughtExceptionHandler`).

- Async task bean
    - `MyAsyncBean`:
        - Method `runTask()` is annotated with `@Async("executor1")` so it runs on the configured executor.
        - Logs the current thread name and simulates work with `Thread.sleep(2000)`.
        - Intentionally throws a `RuntimeException` for a specific thread name (`FutureExecutor-7`) to demonstrate
          exception handling.

- Uncaught exception handler
    - `DemoAsyncUncaughtExceptionHandler`:
        - Receives exceptions thrown by `void` async methods and logs the method that failed.

- Application entry point
    - `DemoEnableAsyc` (Spring Boot application):
        - Provides a `CommandLineRunner` that submits 10 async tasks (`asyncBean.runTask()`), waits for all of them with
          `CompletableFuture.allOf(...).join()`, and then closes the Spring context to trigger a graceful shutdown of
          the executor (configured with `setWaitForTasksToCompleteOnShutdown(true)`).

## Run the demo

From the repository root, either:

- Run with Spring Boot plugin:
    - `./mvnw -pl future -am spring-boot:run`

- Or build and run the jar:
    - `./mvnw -pl future -am package`
    - `java -jar future/target/future-*.jar`

Expected console output includes lines like:

- `Running task thread: FutureExecutor-<N>`
- `Finished task  thread: FutureExecutor-<N>`
- When the intentional exception is triggered, the exception handler logs the method name.

## Typical use cases

- Fire-and-forget background jobs (e.g., email sending, cache warming, audit logging).
- Offloading I/O-bound work (HTTP calls, file operations) from request threads.
- Parallelizing independent tasks to reduce end-to-end latency.

## Common pitfalls and drawbacks

- Proxying and method visibility
    - `@Async` relies on Spring AOP proxies. Calls must come from another Spring bean; self-invocation within the same
      bean will not go async. Methods should be `public`.

- Lost exceptions with `void` methods
    - Exceptions in `void` async methods do not propagate to the caller; they only reach
      `AsyncUncaughtExceptionHandler`. Use `CompletableFuture` when you need to observe errors.

- Transaction and request context boundaries
    - Async methods run on different threads. Transactional context (`@Transactional`) and request-scoped data do not
      automatically propagate.

- Thread pool sizing and blocking calls
    - Mis-sized pools can cause queue buildup or thread starvation. Avoid long blocking operations in pools intended for
      quick tasks.

- Shutdown behavior
    - Abrupt shutdown can interrupt tasks. Configure executors to wait for tasks to complete and shut down gracefully.

## Notes

- Class names contain a few typos (e.g., `DemoEnableAsyc`), and the example uses `Thread.sleep`/`System.exit` for
  simplicity. These are deliberate for demonstration and should be avoided in production code.
