package kr.pah;

import java.io.IOException;

public class SystemReboot {

    public static void reboot() throws IOException {
        try {
            Runtime.getRuntime().exec("shutdown -r -t 0");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main() {
        try {
            System.out.println("시스템을 재부팅합니다...");
            reboot();
        } catch (IOException e) {
            System.err.println("재부팅 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
