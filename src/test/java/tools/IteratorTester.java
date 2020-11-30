package tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IteratorTester {
    @Test
    void testStartReadingSuccessfully(){
        Iterator iterator = new Iterator("file 1");
        try {
            Assertions.assertFalse(iterator.startReading());
        }
        catch (InterruptedException e){

        }
    }

    @Test
    void testStartReadingUnsuccessfully(){
        Iterator iterator = new Iterator("file 2");
        try {
            iterator.startReading();
            Assertions.assertFalse(iterator.startReading());
        }
        catch (InterruptedException e){

        }
    }

    @Test
    void testGetNextStringUnsuccessfully(){
        Iterator iterator = new Iterator("file 3");
        try {
            iterator.startReading();
            Assertions.assertEquals(iterator.getNextString(), iterator.FILE_READING_ERROR);
        }
        catch (InterruptedException e){

        }
    }
}
