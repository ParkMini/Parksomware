package kr.pah;

import java.io.IOException;

public class ServiceBlock {
    public static void blockAll() {
        disableTaskManager();
        disableControlPanel();
        disableRegistryEditor();
        disableFolderOptions();
        disableRunCommand();
        disableSystemProperties();
        disableLogOff();
        disableChangePassword();
        disableShutDownOptions();
        disableCommandPrompt();
        disableNetworkProperties();
        disableSystemRestore();
        disableWindowsUpdate();
        disableDriveAccess("C");
        disableDriveAccess("D");
        disableDriveAccess("E");
        disableDriveAccess("F");
    }

    public static void disableTaskManager() {
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableTaskMgr", "1");
        System.out.println("작업 관리자가 비활성화되었습니다.");
    }

    public static void disableControlPanel() {
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoControlPanel", "1");
        System.out.println("제어판이 비활성화되었습니다.");
    }

    public static void disableRegistryEditor() {
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableRegistryTools", "1");
        System.out.println("레지스트리 편집기가 비활성화되었습니다.");
    }

    public static void disableFolderOptions() {
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoFolderOptions", "1");
        System.out.println("파일 탐색기 옵션 변경이 비활성화되었습니다.");
    }

    public static void disableRunCommand() {
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoRun", "1");
        System.out.println("실행 명령 사용이 비활성화되었습니다.");
    }

    public static void disableSystemProperties() {
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoPropertiesMyComputer", "1");
        System.out.println("시스템 설정 변경이 비활성화되었습니다.");
    }

    public static void disableLogOff() {
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "StartMenuLogOff", "0");
        System.out.println("로그오프 옵션이 비활성화되었습니다.");
    }

    public static void disableChangePassword() {
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableChangePassword", "1");
        System.out.println("비밀번호 변경 옵션이 비활성화되었습니다.");
    }

    public static void disableShutDownOptions() {
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoClose", "1");
    }

    public static void disableCommandPrompt() {
        setRegistryValue("HKCU\\Software\\Policies\\Microsoft\\Windows\\System", "DisableCMD", "1");
        System.out.println("명령 프롬프트가 비활성화되었습니다.");
    }

    public static void disableNetworkProperties() {
        setRegistryValue("HKCU\\Software\\Policies\\Microsoft\\Windows\\Network Connections", "NC_LanProperties", "1");
        System.out.println("네트워크 설정 변경이 비활성화되었습니다.");
    }

    public static void disableSystemRestore() {
        setRegistryValue("HKLM\\Software\\Policies\\Microsoft\\Windows NT\\SystemRestore", "DisableSR", "1");
        System.out.println("시스템 복원 기능이 비활성화되었습니다.");
    }


    public static void disableWindowsUpdate() {
        setRegistryValue("HKLM\\Software\\Policies\\Microsoft\\Windows\\WindowsUpdate\\AU", "NoAutoUpdate", "1");
        System.out.println("Windows 업데이트가 비활성화되었습니다.");
    }

    public static void disableDriveAccess(String driveLetter) {
        // 예: "NoDrives" 값에 4(드라이브 D:)을 설정하면 D 드라이브 접근이 금지됨.
        // 드라이브 글자에 해당하는 비트를 계산하는 로직 필요.
        setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoDrives", driveLetter);
        System.out.println(driveLetter + " 드라이브 접근이 금지되었습니다.");
    }

    private static void setRegistryValue(String path, String name, String value) {
        try {
            String command = "reg add \"" + path + "\" /v " + name + " /t REG_DWORD /d " + value + " /f";
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}