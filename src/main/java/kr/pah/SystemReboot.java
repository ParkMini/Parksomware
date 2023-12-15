package kr.pah;

import java.io.IOException;

public class SystemReboot {

    public static void reboot() {
        try {
            Runtime.getRuntime().exec("shutdown -r -t 0 -f");
        } catch (IOException ignored) {
        }
    }

    public static void main() {
        reboot();
    }
}