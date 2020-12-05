package tools;

import java.io.*;
import java.util.Spliterator;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class Iterator implements java.util.Iterator<String>, Closeable {
    /**
     * Instance of file reader stream.
     */
    private final BufferedReader reader;

    /**
     * New file line.
     */
    protected String currString;

    /**
     * Iterator constructor.
     * Use Closeable.close() to release resources after work or exception handling.
     * @param pathToFile path ot file you need read.
     * @throws UncheckedIOException throes if it's impossible to read this file.
     */
    public Iterator(String pathToFile) throws UncheckedIOException {
        try{
            reader = new BufferedReader(new FileReader(pathToFile));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public boolean hasNext() {
        try {
            currString = reader.readLine();
            if (currString == null) {
                reader.close();
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String next() {
        return currString;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
