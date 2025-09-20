package ca.mikhailov.samples;

import java.nio.file.Files;
import java.nio.file.Path;

public class SafeFunction {

    public static void main(String[] args) {
        var safe = new SafeFunction();
        String content = safe.executeSafely(() -> Files.readString(Path.of("test.txt")));
        System.out.println("File content: " + content);
    }

    protected <T, E extends Exception> T executeSafely(ThrowingSupplier<T, E> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T, E extends Exception> {

        T get() throws E;
    }
}
