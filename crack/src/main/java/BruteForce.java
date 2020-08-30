import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FilenameFilter;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

class BruteForce implements Runnable {

    private int start;
    private int end;
    private static boolean DONE = false;
    private String found;
    private final String psHint = "Twg48wT";
//  Change the fileName and destination path as per yours needs
    private final String fileName = "D:\\Meyvan\\sample\\assignment.zip";
    private final String destPath = "D:\\Meyvan\\sample\\assignment\\data_template";


    public BruteForce(int s, int e) throws NoSuchAlgorithmException {
        start = s;
        end = e;
    }

    public void generate(StringBuilder sb, int n) {
        if (DONE)
            return;
        if (n == sb.length()) {
            String pass = sb.toString();
// Matching the password
            System.out.println(pass);
            String password = psHint + pass;
            ignoringExc(() -> unCompressPasswordProtectedFiles(password));
            return;
        }

        for (int i = 0; i < Crack.ALPHABET.length && !DONE; i++) {
            char letter = Crack.ALPHABET[i];
            sb.setCharAt(n, letter);
            generate(sb, n + 1);
        }

    }

    ZipFile zipFile;
    {
        try {
            zipFile = new ZipFile(fileName);
        } catch (ZipException e) {
            System.out.println(e.getMessage());
        }
    }

    public String unCompressPasswordProtectedFiles(String password) throws ZipException {
        // Set the generated the password
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
//  If the password is match it will extract the files in the destination path
        zipFile.extractAll(destPath);
//  Check the file is extracted or not
        File f = new File(destPath);
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("data") && name.endsWith("csv");
            }
        });
//  If file exist end the program
        if (matchingFiles != null) {
            found = password;
            DONE = true;
            return password;
        }
        return "";
    }

    private String getFileName(String filePath) {
//  Get the folder name from the zipped file by removing .zip extension
        return filePath.substring(0, filePath.lastIndexOf("."));
    }

    //   Ignoring the exception while matching the password
    public static void ignoringExc(RunnableExc r) {
        try {
            r.run();
        } catch (Exception e) {
        }
    }

    @FunctionalInterface
    public interface RunnableExc {
        void run() throws Exception;
    }

    //  Running threads
    @Override
    public void run() {

        for (int length = start; length <= end && !DONE; length++) {
            StringBuilder sb = new StringBuilder();
            sb.setLength(length);
            generate(sb, 0);
        }
        if (DONE) {
            long duration = System.currentTimeMillis() - Crack.START_TIME;
            System.out.println("Password cracked in " + TimeUnit.MILLISECONDS.toSeconds(duration) + "." + TimeUnit.MILLISECONDS.toMillis(duration) + " sec. Password is = " + found + ". Check the directory " + destPath + " zip files are extracted successfully... : )");
        } else {
            System.out.println("Password not cracked for subset [" + start + ", " + end + "]");
        }
    }
}
