package tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

public class IteratorTester {
    @Test
    void testConstruct(){
        Throwable thrown =Assertions.assertThrows(UncheckedIOException.class,
                ()->{new Iterator("1");});
    }
}
