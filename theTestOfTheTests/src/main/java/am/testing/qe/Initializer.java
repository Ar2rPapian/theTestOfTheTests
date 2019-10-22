package am.testing.qe;

import am.testing.qe.util.Browser;

public class Initializer {

    private static Initializer initializer = new Initializer();

    private Initializer(){}

    public static Initializer get(){
        return initializer;
    }

    public Browser getCurrentBrowser() {
        String strBrowserName = System.getenv("browser").toUpperCase();
        return Browser.valueOf(strBrowserName);
    }
}
