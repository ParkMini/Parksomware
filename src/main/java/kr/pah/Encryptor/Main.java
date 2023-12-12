package kr.pah.Encryptor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String verify = sc.nextLine();
        if (verify.equals("run")) {
            System.out.println("Ok");
            Encrypt.runEncryption();
            System.out.println("Done");
        } else {
            System.out.println("NotRun");
        }
    }
}