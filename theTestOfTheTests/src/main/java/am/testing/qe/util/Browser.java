package am.testing.qe.util;

import am.testing.qe.exceptions.ImpossibleContinueTestingException;

import java.util.HashMap;
import java.util.Map;

public enum Browser {
    SAFARI  (),
    CHROME  (),
    FIREFOX ();

    private Map<OS, String> path = new HashMap<>();

    private void init(){
        switch (this){
            case FIREFOX:
                path.put(OS.WINDOWS, "");
                path.put(OS.MAC, "/Applications/Firefox.app/Contents/MacOS/firefox");
                path.put(OS.LINUX, "/usr/bin/firefox");
                break;
            case SAFARI:
                path.put(OS.WINDOWS, "");
                path.put(OS.MAC, "");
                path.put(OS.LINUX, "");
                break;
            case CHROME:
                path.put(OS.WINDOWS, "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
                path.put(OS.MAC, "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
                path.put(OS.LINUX, "/usr/bin/google-chrome");
                break;
        }

    }

    public String getPath() {
        init();
        String browserPath = path.get(OS.current());
        if (browserPath.isEmpty())
            throw new ImpossibleContinueTestingException(String.format("%s browser's path is not defined", this.name()));
        return browserPath;
    }
}
