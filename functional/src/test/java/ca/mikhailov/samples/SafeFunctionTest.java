package ca.mikhailov.samples;

import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SafeFunctionTest {

    /**
     * Class under test: SafeFunction
     * Method under test: executeSafely
     * <p>
     * The executeSafely method is designed to safely execute a supplier that may throw a checked exception.
     * If an exception occurs, it wraps it in a RuntimeException and throws it.
     */

    @Test
    void testExecuteSafelyWithSuccessfulExecution() {

        SafeFunction safeFunction = new SafeFunction();
        String expected = "Success";

        String result = safeFunction.executeSafely(() -> expected);
        assertEquals(expected, result);
    }

    @Test
    void testExecuteSafelyThrowsRuntimeExceptionOnCheckedException() {

        SafeFunction safeFunction = new SafeFunction();
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                safeFunction.executeSafely(() -> {
                    throw new NoSuchFileException("File not found");
                })
        );
        assertInstanceOf(NoSuchFileException.class, exception.getCause());
        assertEquals("File not found", exception.getCause().getMessage());
    }

    @Test
    void testExecuteSafelyWithNullSupplierThrowsException() {

        SafeFunction safeFunction = new SafeFunction();
        assertThrows(RuntimeException.class, () -> safeFunction.executeSafely(null));
    }

    @Test
    void testExecuteSafelyPropagatesUncheckedException() {

        SafeFunction safeFunction = new SafeFunction();
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                safeFunction.executeSafely(() -> {
                    throw new IllegalArgumentException("Invalid argument");
                })
        );
        assertInstanceOf(IllegalArgumentException.class, exception.getCause());
        assertEquals("Invalid argument", exception.getCause().getMessage());
    }

    @Test
    void testExecuteSafelyReturnsNullWhenSupplierReturnsNull() {

        SafeFunction safeFunction = new SafeFunction();
        String result = safeFunction.executeSafely(() -> null);

        assertNull(result);
    }
}
