package ca.mikhailov.future.app;

import java.lang.reflect.Method;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class DemoAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

  @Override
  public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
    String methodName = method.getName();
    System.out.printf("exception handeled: \n Method: %s\n", methodName);
  }
}
