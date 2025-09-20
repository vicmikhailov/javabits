package ca.mikhailov.future.app;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class DemoAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(
            Throwable throwable,
            Method method,
            Object... objects) {
        String methodName = method.getName();
        System.out.printf("exception handeled: \n Method: %s\n", methodName);
    }
}
