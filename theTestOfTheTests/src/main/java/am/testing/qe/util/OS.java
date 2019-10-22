package am.testing.qe.util;

public enum OS {
    MAC,
    LINUX,
    WINDOWS;

    public static OS current(){
        return valueOf(System.getProperty("os.name")
                .toUpperCase().split(" ")[0]);
    }
}