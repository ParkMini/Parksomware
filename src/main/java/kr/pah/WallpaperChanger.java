package kr.pah;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Paths.*;

public class WallpaperChanger {

    public static void main() throws IOException {
        String tempImagePath = copyImageToTemp();
        setDesktopBackground(tempImagePath);
    }

    private static String copyImageToTemp() throws IOException {
        String tempDir = System.getenv("TEMP");
        Path tempImagePath = get(tempDir, "background.png");

        try (InputStream is = WallpaperChanger.class.getResourceAsStream("/background.png")) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: background.png");
            }
            Files.copy(is, tempImagePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return tempImagePath.toString();
    }

    public static void setDesktopBackground(String imagePath) throws IOException {
        String command = "powershell.exe Set-ItemProperty -path 'HKCU:\\Control Panel\\Desktop' -name WallPaper -value '" + imagePath + "'";


        Process powerShellProcess = Runtime.getRuntime().exec(command);
        powerShellProcess.getOutputStream().close();

        String line;
        BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
        }
        stdout.close();

        BufferedReader stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
        while ((line = stderr.readLine()) != null) {
            System.err.println(line);
        }
        stderr.close();

        try {
            powerShellProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Exit Status: " + powerShellProcess.exitValue());

        // 변경 사항 적용을 위해 시스템 명령 실행
        try {
            Runtime.getRuntime().exec("RUNDLL32.EXE user32.dll,UpdatePerUserSystemParameters");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
