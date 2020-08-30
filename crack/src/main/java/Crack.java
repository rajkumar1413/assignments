/**
 * Dextor created on 8/30/2020 inside the package - PACKAGE_NAME
 **/


import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crack {

    public static char[] ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    public static final int PASSWORD_MAX_LENGTH = 3;
    public static long START_TIME;

    public static void main(String[] args) throws NoSuchAlgorithmException {
//   Set the availability of the processors cores
        int cores = Runtime.getRuntime().availableProcessors();
        int lengthSet = PASSWORD_MAX_LENGTH / cores;
        int end = 0;

        START_TIME = System.currentTimeMillis();
//  ExecutorService is execute the thread
        ExecutorService executorService = Executors.newFixedThreadPool(cores);
        while (end < PASSWORD_MAX_LENGTH) {
            int start = end + 1;
            end = start + lengthSet;
            if (end > PASSWORD_MAX_LENGTH)
                end = PASSWORD_MAX_LENGTH;
            executorService.submit(new BruteForce(start, end));
        }
        executorService.shutdown();
    }
}