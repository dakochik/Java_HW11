package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Iterator {
    /**
     * Path to file we need to read.
     */
    private final String pathToFile;

    /**
     * Additional thread which will read a file.
     */
    private ReadingThread readingThread;

    /**
     * Helps to wait for readingThread initialization.
     */
    private CountDownLatch threadLaunchingHelper;

    /**
     * New line of the file that we're reading.
     */
    volatile private StringBuilder currentString;

    /**
     * Lines which is returned if it's impossible to keep reading the file.
     */
    public final String FILE_READING_ERROR = "ERROR: some problems during file reading";

    public Iterator(String pathToFile) {
        this.pathToFile = pathToFile;
        currentString = new StringBuilder();
        readingThread = new ReadingThread();
        threadLaunchingHelper = new CountDownLatch(1);
    }

    /**
     * Method launches additional thread and begins file reading.
     *
     * @return was thread successfully started.
     * @throws InterruptedException if main current thread (not additional) should be interrupted.
     */
    public boolean startReading() throws InterruptedException {
        synchronized (readingThread) {
            if (readingThread.isAlive()  || readingThread.isInterrupted()) {
                return false;
            }
            readingThread.start();
        }

        threadLaunchingHelper.await();

        synchronized (readingThread) {
            return readingThread.isAlive();
        }
    }

    /**
     * Method gets new line from opened document and lets additional thread keep running.
     *
     * @return new line from opened document.
     */
    public String getNextString() {
        synchronized (readingThread) {
            readingThread.notify();
        }

        synchronized (readingThread) {
            return currentString.toString();
        }
    }

    /**
     * Return additional thread state.
     *
     * @return if additional thread is alive.
     */
    public boolean isAlive() {
        return readingThread.isAlive();
    }

    /**
     * This method creates new instances of additional thread and CountDownLatch objects.
     * It's called after previous additional thread finished.
     */
    private void updateThread(){
        readingThread = new ReadingThread();
        threadLaunchingHelper = new CountDownLatch(1);
    }

    class ReadingThread extends Thread {

        /**
         * It's reading lines from the file one by one. After each new line method save it into the currentString
         * and called .wait() for additional thread.
         */
        public void run() {
            synchronized (readingThread) {
                try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
                    String newStr = "";
                    do {
                        currentString.delete(0, currentString.length());
                        newStr = reader.readLine();
                        if (newStr != null) {
                            currentString.append(newStr);
                        } else {
                            return;
                        }

                        threadLaunchingHelper.countDown();

                        try {
                            readingThread.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    while (true);
                } catch (IOException e) {
                    currentString.delete(0, currentString.length());
                    currentString.append(FILE_READING_ERROR);
                    threadLaunchingHelper.countDown();
                }
                updateThread();
            }
        }

    }
}
