public class Constants {

    //Before Class
    public static final String REPORTER_PATH = "/home/misha/Downloads/Maps_report.html";
    public static final String TEST_NAME = "Google Maps Test";
    public static final String TEST_DESCRIPTION = "Testing of Google Maps using MySQL, REST API and Selenium WebDriver";
    public static final String INFO_KEY = "Distribution type";
    public static final String INFO_VALUE = "Private";
    public static final String INFO_KEY2 = "Tester";
    public static final String INFO_VALUE2 = "Angelina";
    public static final String LOG_INFO = "@BeforeClass";

    public static final String IMAGE_PATH = "/home/misha/Pictures/Reports/";

    //RunDBTest
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://sql7.freemysqlhosting.net:3306/?user=sql7284700";
    public static final String DB_USERNAME = "sql7284700";
    public static final String DB_PASSWORD = "W63zkMqa9F";
    public static final String DB_QUERY = "SELECT * FROM sql7284700.MapsData;";
    public static final String DB_COLUMN_1 = "URL";
    public static final String DB_COLUMN_2 = "criteria";

    public static final String DB_FAIL = "DB connection was failed";
    public static final String DB_SUCCESSFUL = "DB was successfully connected";

    public static final String QUERY_FAIL = "Query request was failed";
    public static final String QUERY_SUCCESSFUL = "Query request was completed";

    //RESTrequest
    public static final String REST_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
    public static final String REST_KEY = "&key=AIzaSyCu2vjNMuQDCQSB67-2zsJRXkvQYgFSW64";
    public static final String JSON_ARRAY = "results";
    public static final int JSON_ARRAY_NUM = 4;
    public static final String JSON_OBJECT_1 = "geometry";
    public static final String JSON_OBJECT_2 = "location";
    public static final String COORDINATE_X = "lat";
    public static final String COORDINATE_Y = "lng";

    public static final String REST_FAIL = "REST API connection was failed";
    public static final String REST_SUCCESSFUL = "REST API was successfully connected";

    public static final String JSON_FAIL = "JSON parsing was failed";
    public static final String JSON_SUCCESSFUL = "JSON was parsed connected";

    //SeleniumTest
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String PROPERTY_2 = "/home/misha/Downloads/chromedriver";
    public static final String INPUT_FIELD = "tactile-searchbox-input";
    public static final String INPUT_KEYS = " ";
    public static final String BUTTON_SEARCH = "searchbox-searchbutton-container";
    public static final String WAIT_CLASS = "section-action-button-icon-container";

    public static final String DRIVER_STATUS = "Cant connect chromeDriver";
    public static final String DRIVER_FAIL = "Driver Connection Failed! ";
    public static final String DRIVER_SUCCESSFUL = "Driver established successfully";

    public static final String PAGE_FAIL = "Google Maps page was not found ";
    public static final String PAGE_SUCCESSFUL = "Google Maps was opened successfully";

    public static final String INPUT_SEARCH_FAIL = "Input and Search was failed";
    public static final String INPUT_SEARCH_SUCCESSFUL = "Input and Search was done successfully";

    public static final String SCREENSHOT_RESULT = "Result: found page in Google Maps under provided coordinates";

    //AfterClass
    public static final String INFO_TEXT = "@AfterClass";
    public static final String IMAGE_FORMAT = ".png";

}
