package ca.mikhailov.samples;

public class SafeFunction {

  public static void main(String[] args) {

  }

  protected <T, E extends Exception> T excuteSafely(ThrowingSupplier<T, E> supplier) {
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
