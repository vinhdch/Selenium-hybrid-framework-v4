package PageUIs;

public class HomePageUI {

    public static final String DYNAMIC_HEADER_LINK = "xpath=//a[@class='ico-%s']";

    public static final String JQUERY_DYNAMIC_PAGE_LINK = "xpath=//li[@class='qgrd-pagination-page']/a[text()='%s']";

    public static final String JQUERY_DYNAMIC_HEADER_LINK = "xpath=//div[@class='qgrd-header-text' and text()='%s']/parent::div/following-sibling::input";

    public static final String JQUERY_DYNAMIC_ROW = "xpath=//td[@data-key='%s' and text()='%s']";

    public static final String JQUERY_DYNAMIC_COLUMN_HEADER = "xpath=//div[text()='%s']/ancestor::th/preceding-sibling::th";

    public static final String JQUERY_DYNAMIC_ALL_VALUE_BY_COLUMN_INDEX = "xpath=//td[%s]";

    public static final String JQUERY_FILE_UPLOADED_BY_FILE_NAME ="xpath=//p[@class='name' and text()='%s']";

    public static final String JQUERY_UPLOAD_BUTTON ="css=td button.start";

    public static final String JQUERY_FILE_UPLOADED_SUCCESS ="xpath=//p[@class='name']/a[text()='%s']";
}
