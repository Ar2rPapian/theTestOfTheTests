package am.testing.qe;

import am.testing.qe.util.Browser;

import static am.testing.qe.config.Constants.DEFAULT_BROWSER;

public class Initializer {

    private static Initializer initializer = new Initializer();

    private Initializer(){}

    public static Initializer get(){
        return initializer;
    }

    public Browser getCurrentBrowser() {
        String browserName = System.getenv("browser");
        if(browserName == null){
            return DEFAULT_BROWSER;
        }
        String strBrowserName = browserName.toUpperCase();
        return Browser.valueOf(strBrowserName);
    }
}
