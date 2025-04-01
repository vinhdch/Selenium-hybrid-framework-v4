package commons;

import java.io.File;

public class GlobalConstants {
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final String UPLOAD_FILE = PROJECT_PATH + File.separator + "uploadFiles" + File.separator;
    public static final String DOWNLOAD_FILE = PROJECT_PATH + File.separator + "downloadFiles";
    public static final String BROWSER_LOG = PROJECT_PATH + File.separator + "browserLogs" + File.separator;
    public static final String DRAG_DROP_HTML5 = PROJECT_PATH + File.separator + "dragDropHTML5";

    public static final long SHORT_TIMEOUT = 15;
    public static final long LONG_TIMEOUT = 30;
    public static final long THREAD_TIMEOUT = 2000;
    public static final long RETRY_TEST_FAIL = 3;

    public static final String UPLOAD_FILE_TYPE = "xpath=//input[@type='file']";
}
