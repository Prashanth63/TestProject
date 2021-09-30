import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class LoginPage extends BasePage {
        @Test
        public void login() throws InterruptedException {

            driver.findElementById("com.adaptavant.setmore:id/login").click();
            WebDriverWait wait=new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gms:id/cancel")));
            driver.findElementById("com.google.android.gms:id/cancel").click();
            driver.findElementById("com.adaptavant.setmore:id/forgotpassword").click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.adaptavant.setmore:id/email")));
            driver.findElementById("com.adaptavant.setmore:id/email").sendKeys("new@sb.co");
            driver.findElementById("com.adaptavant.setmore:id/reset").click();
            Thread.sleep(5000);
            System.out.println("hello completed");
        }

    }
