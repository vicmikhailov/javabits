package ca.mikhailov.future.app;

import org.springframework.scheduling.annotation.Async;

public class MyAsyncBean {

  @Async("executor1")
  public void runTask() {
    String name = Thread.currentThread().getName();
    System.out.printf("Running task  thread: %s\n", name);

    if ("FutureExecutor-7".equals(name)) {
      throw new RuntimeException("Holy molly");
    }

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.printf("Finished task  thread: %s\n", name);
  }
}
