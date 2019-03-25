import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Main {

    private static WebDriver driver;
    private static Connection con;
    private static String URL;
    private static String criteria;
    private static String jsonData;
    private static double lat;
    private static double lng;

    @Rule
    public TestName name = new TestName();

    private static ExtentReports report ;
    private static ExtentTest test ;

    //run extend report configurations
    @BeforeClass
    public static void runBefore() {

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(Constants.REPORTER_PATH);
        htmlReporter.setAppendExisting(true);
        report = new ExtentReports();
        report.attachReporter(htmlReporter);
        test = report.createTest(Constants.TEST_NAME, Constants.TEST_DESCRIPTION);
        report.setSystemInfo(Constants.INFO_KEY, Constants.INFO_VALUE);
        report.setSystemInfo(Constants.INFO_KEY2, Constants.INFO_VALUE2);
        test.log(Status.INFO, Constants.LOG_INFO);

    }

    //Connect to remove DB and taking data from it
    @Test
    public void test_01_runDBtest() throws ClassNotFoundException, SQLException {

        boolean runDB = false;
        try {
            Class.forName(Constants.DB_DRIVER);
            con = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            runDB = true;

        } catch (Exception e) {

            e.printStackTrace();
            test.log(Status.FAIL, Constants.DB_FAIL + e.getMessage());
            runDB = false;
        } finally {
            if (runDB) {
                test.log(Status.PASS, Constants.DB_SUCCESSFUL);

            }
        }

        boolean runQuery = false;
        try {
            String statementToExecute = "";
            Statement stmt = con.createStatement();
            statementToExecute = Constants.DB_QUERY;

            ResultSet rs = stmt.executeQuery(statementToExecute);
            while (rs.next()) {
                URL = rs.getString(Constants.DB_COLUMN_1);
                criteria = rs.getString(Constants.DB_COLUMN_2);
            }
            runQuery = true;

        } catch (Exception e) {

            e.printStackTrace();
            test.log(Status.FAIL, Constants.QUERY_FAIL + e.getMessage());
            runQuery = false;
        } finally {
            if (runQuery) {
                test.log(Status.PASS, Constants.QUERY_SUCCESSFUL);

            }
        }

    }

    //Run REST request and pasing coordinates
    @Test
    public void test_02_RESTrequest() throws IOException {

        boolean runREST = false;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(Constants.REST_URL + criteria + Constants.REST_KEY).build();
            Response response = client.newCall(request).execute();
            jsonData = response.body().string();
            runREST = true;

        } catch (Exception e) {

            e.printStackTrace();
            test.log(Status.FAIL, Constants.REST_FAIL + e.getMessage());
            runREST = false;
        } finally {
            if (runREST) {
                test.log(Status.PASS, Constants.REST_SUCCESSFUL);

            }
        }

        boolean parseJSON = false;
        try {
            JSONObject mainJsonObject = new JSONObject(jsonData);
            JSONArray resultsArray = mainJsonObject.getJSONArray(Constants.JSON_ARRAY);
            JSONObject location = resultsArray.getJSONObject(Constants.JSON_ARRAY_NUM).getJSONObject(Constants.JSON_OBJECT_1).getJSONObject(Constants.JSON_OBJECT_2);
            lat = location.getDouble(Constants.COORDINATE_X);
            lng = location.getDouble(Constants.COORDINATE_Y);

            parseJSON = true;

        } catch (Exception e) {

            e.printStackTrace();
            test.log(Status.FAIL, Constants.JSON_FAIL + e.getMessage());
            parseJSON = false;
        } finally {
            if (parseJSON) {
                test.log(Status.PASS, Constants.JSON_SUCCESSFUL);

            }
        }

    }

    //Run Selenium Web driver and type coordinates in Maps site
    @Test
    public void test_03_SeleniumTest() throws IOException {

        boolean driverEstablish = false;
        try {
            System.setProperty(Constants.PROPERTY, Constants.PROPERTY_2);
            driver = new ChromeDriver();
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driverEstablish = true;

        } catch (Exception e) {

            e.printStackTrace();
            fail(Constants.DRIVER_STATUS);
            test.log(Status.FATAL, Constants.DRIVER_FAIL + e.getMessage());
            driverEstablish = false;
        } finally {
            if (driverEstablish) {
                test.log(Status.PASS, Constants.DRIVER_SUCCESSFUL);

            }
        }

        boolean pageOpened = false;
        try {
            driver.get(URL);
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            pageOpened = true;

        } catch (Exception e) {

            e.printStackTrace();
            test.log(Status.FAIL, Constants.PAGE_FAIL + e.getMessage());
            pageOpened = false;
        } finally {
            if (pageOpened) {
                test.log(Status.PASS, Constants.PAGE_SUCCESSFUL);

            }
        }

        boolean InputAndSearch = false;
        try {
            driver.findElement(By.className(Constants.INPUT_FIELD)).sendKeys(lat + Constants.INPUT_KEYS + lng);
            driver.findElement(By.className(Constants.BUTTON_SEARCH)).click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(Constants.WAIT_CLASS)));
            InputAndSearch = true;

        } catch (Exception e) {

            e.printStackTrace();
            test.log(Status.FAIL, Constants.INPUT_SEARCH_FAIL + e.getMessage());
            InputAndSearch = false;
        } finally {
            if (InputAndSearch) {
                test.log(Status.PASS, Constants.INPUT_SEARCH_SUCCESSFUL);

            }
        }

        test.pass(Constants.SCREENSHOT_RESULT, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(Constants.IMAGE_PATH + name.getMethodName())).build());

    }

    //Closing of driver + screenshot configurations + run report
    @AfterClass
    public static void close(){
        test.log(Status.INFO, Constants.INFO_TEXT);
        driver.quit();
        report.flush();
    }

    private static String takeScreenShot(String ImagesPath) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath+Constants.IMAGE_FORMAT);
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath+Constants.IMAGE_FORMAT;
    }
}
