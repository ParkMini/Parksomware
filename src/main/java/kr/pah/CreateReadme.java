package kr.pah;

import java.awt.Desktop;
import java.io.*;
import java.nio.file.*;

public class CreateReadme {
    public static void main() {
        try {
            Path desktopPath = Paths.get(System.getenv("USERPROFILE"), "Desktop");
            Path readmePath = extractFileToDesktop(desktopPath, "/README.html");
            Path backgroundPath = extractFileToDesktop(desktopPath, "/background.png");
            openInBrowser(readmePath);
            addToStartup(readmePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path extractFileToDesktop(Path desktopPath, String resourceFileName) throws IOException {
        Path filePath = desktopPath.resolve(resourceFileName.substring(1)); // Remove the leading "/"
        try (InputStream is = CreateReadme.class.getResourceAsStream(resourceFileName)) {
            if (is == null) {
                throw new FileNotFoundException(resourceFileName + " not found in Jar file.");
            }
            Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return filePath;
    }

    private static void openInBrowser(Path filePath) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(filePath.toUri());
        } else {
            throw new UnsupportedOperationException("Desktop is not supported.");
        }
    }

    private static void addToStartup(Path filePath) throws IOException {
        String startupFolderPath = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
        Path shortcutPath = Paths.get(startupFolderPath, "OpenReadme.lnk");

        String script = "powershell \"$s = (New-Object -COM WScript.Shell).CreateShortcut('" + shortcutPath.toString() + "'); "
                + "$s.TargetPath = '" + filePath.toString() + "'; $s.Save()\"";

        Runtime.getRuntime().exec(script);
    }
}
