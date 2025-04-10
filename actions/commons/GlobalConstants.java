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

    public static final String  UPLOAD_FILE_TYPE = "xpath=//input[@type='file']";

    public static final String  JIRA_URL = "https://vinh01647533406.atlassian.net/";
    public static final String  JIRA_USERNAME = "vinh01647533406@gmail.com";
    public static final String  JIRA_API_TOKEN = "ATATT3xFfGF0lfnOZNQUIt8CQFTo5_ygTi2BaZyUy8fQyvJ07C6_jf5Ad71LogJ_yMIgYfLHydmvDiFV5PU3d1nlFq70zCQO4_rFAwRMiiBNQvIzeV9Hh1h" +
            "rsEH8nlqAyzCuuzUrPQLChyuV_5zgvKdLJOGNEHoo1_cEp-iTh6nhTp1nZccu_tk=02494283";
    public static final String  JIRA_PROJECT_KEY = "NOPVINHDCH";
}
