import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class BasePage {
    public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentTest childTest;
    public static File CONF;

    public static AndroidDriver driver;

        @BeforeSuite
        public void baseMethod() throws IOException {
            report_Deployment();
            launchMethod();
        }

        public void launchMethod() throws IOException {
            DesiredCapabilities cap = new DesiredCapabilities();

            cap.setCapability(MobileCapabilityType.DEVICE_NAME, "prashanth-device");
            cap.setCapability("appPackage", "com.adaptavant.setmore");
            cap.setCapability("appActivity", "com.adaptavant.setmore.ui.LaunchActivity");
            cap.setCapability("app" , System.getProperty("user.dir")+"/src/apps/app-setmoreproduction-release.apk");
            driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), cap);

            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        }

    public static void report_Deployment() throws IOException {

        System.out.println(System.getProperty("user.dir"));
        extent = new ExtentReports();
        System.out.println("2");
        CONF = new File("src/test/report/extentconfig.xml");
        System.out.println("3");
        ExtentSparkReporter spark = new ExtentSparkReporter("src/test/report/DeploymentReport.html");
        System.out.println("4");
        spark.loadXMLConfig(CONF);
        System.out.println("5");
        extent.attachReporter(spark);
    }


    @BeforeMethod
    public void beforeTest(Method method) throws Exception {
        String testcaseName = method.getName();
        try {
            test = extent.createTest(testcaseName);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }


    @AfterMethod
    public synchronized void afterMethod(ITestResult result) throws Exception {

        if (result.getStatus() == ITestResult.FAILURE) {
            childTest.fail(result.getThrowable());
            extent.flush();
            System.out.println(result.getThrowable());
            try {
                childTest.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64()).build());
            } catch (Exception e) {
                e.printStackTrace();
                childTest.log(Status.FAIL, "Unable to capture SNAPSHOT : " + e.toString());
                childTest.log(Status.WARNING, "DETAILS : " + e.toString());
                childTest.log(Status.WARNING, e.toString());
            }
            extent.flush();

        } else if (result.getStatus() == ITestResult.SKIP) {
            childTest.skip(result.getThrowable());
            extent.flush();
            System.out.println(result.getThrowable());
            try {
                String screenshotPath1 = getScreenshot(result.getName());
                childTest.log(Status.FAIL, "SNAPSHOT BELOW: " + childTest.addScreenCaptureFromPath(screenshotPath1));
            } catch (Exception e) {
                e.printStackTrace();
                childTest.log(Status.FAIL, "Unable to capture SNAPSHOT : " + e.toString());
            }
            extent.flush();

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            // String testcaseName = result.getName();
            // childTest.log(Status.INFO, testcaseName + " :- Passed ");
            extent.flush();
        }
        File htmlFile = new File("src/test/report/DeploymentReport.html");
        Desktop.getDesktop().browse(htmlFile.toURI());
    }
    public static String getBase64() throws IOException {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    public static String getScreenshot(String screenshotName) throws IOException {
        String timeStamp = new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println(timeStamp);
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/report/"
                + screenshotName + "_" + timeStamp + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }




}
