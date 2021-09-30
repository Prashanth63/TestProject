import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BasePage {
        public static AndroidDriver driver;

        @BeforeSuite
        public void baseMethod() throws MalformedURLException, MalformedURLException {
            DesiredCapabilities cap = new DesiredCapabilities();

            cap.setCapability(MobileCapabilityType.DEVICE_NAME, "prashanth-device");
            cap.setCapability("appPackage", "com.adaptavant.setmore");
            cap.setCapability("appActivity", "com.adaptavant.setmore.ui.LaunchActivity");
            cap.setCapability("app" , System.getProperty("user.dir")+"/src/apps/app-setmoreproduction-release.apk");
            driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), cap);

            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);


        }
}
