package kr.pah;

import java.nio.file.*;

public class WallpaperChanger {

    private static final String PARKSOMWARE_DIR = System.getenv("APPDATA") + "\\Parksomware";
    private static final String BACKGROUND_IMAGE = "background.png";

    public static void main() throws Exception {
        Path parksomwarePath = Paths.get(PARKSOMWARE_DIR);
        if (!Files.exists(parksomwarePath)) {
            Files.createDirectories(parksomwarePath);
        }

        Path backgroundPath = parksomwarePath.resolve(BACKGROUND_IMAGE);
        setDesktopBackground(backgroundPath.toString());
    }

    public static void setDesktopBackground(String imagePath) throws Exception {
        String command = "powershell.exe Set-ItemProperty -path 'HKCU:\\Control Panel\\Desktop' -name WallPaper -value '" + imagePath + "'";

        Process powerShellProcess = Runtime.getRuntime().exec(command);
        powerShellProcess.getOutputStream().close();

        // Handle process output and errors
        // ...

        try {
            powerShellProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Exit Status: " + powerShellProcess.exitValue());

        // Apply changes
        Runtime.getRuntime().exec("RUNDLL32.EXE user32.dll,UpdatePerUserSystemParameters");
    }
}
