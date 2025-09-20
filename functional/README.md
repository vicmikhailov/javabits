# Checked exceptions in Streams — minimal helper

Problem: Stream lambdas target non-throwing interfaces, so checked exceptions force noisy try/catch in pipelines.

Solution: SafeFunction.executeSafely wraps checked exceptions once, keeping lambdas as single expressions.

- Location: `ca.mikhailov.samples.EnumPower`
- API: `<T, E extends Exception> T executeSafely(ThrowingSupplier<T, E> supplier)`

Usage

```java
SafeFunction safe = new SafeFunction();

List<String> contents = files.stream()
    .map(p -> safe.executeSafely(() -> Files.readString(p)))
    .toList();

List<String> lines = files.stream()
    .flatMap(p -> safe.executeSafely(() -> Files.readAllLines(p)).stream())
    .toList();

// Also fine outside streams:
String content = safe.executeSafely(() -> Files.readString(Path.of("test.txt")));
```

Notes

- Wraps any checked exception in `RuntimeException` (inspect `getCause()` if needed).
- Swap the wrapper to a specific unchecked type (e.g., `UncheckedIOException`) if that fits your boundary.
- Stateless and thread-safe; reuse one instance.
