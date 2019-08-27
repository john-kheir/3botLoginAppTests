package appmain;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeSuite;



public class Base {
	
	public static AppiumDriverLocalService appiumService;
	public static AndroidDriver<AndroidElement>  driver;
	public static Logger logger;
	
	@BeforeSuite
	public void stopAppiumServerBeforeSuite() {
		// delete any local appium instance running before running tests
		boolean flag = checkIfServerIsRunnning(4723);
		if (flag) {
		stopServer();
		sleep(5000);
		}
		
		log();
	}
	    

	public static AndroidDriver<AndroidElement> Capabilities() throws MalformedURLException, IOException {

		// System.getProperty("user.dir") : gets the project path
		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/appmain/global.properties");
		Properties prop = new Properties();
		prop.load(file);

		File appDir = new File("src");
		File app = new File(appDir, (String) prop.get("appName"));
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, (String) prop.get("device"));
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		// capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2"); //uiautomator2 this gives error understand why
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		return driver;

	}
	
	public void log() {
		logger= Logger.getLogger("Log4j_Demo");
        File appDir = new File("src");
        File app = new File(appDir, "log4j.properties");
        PropertyConfigurator.configure(app.getAbsolutePath());
        System.out.println(app.getAbsolutePath());

	}
	
	public AppiumDriverLocalService startServer() throws IOException {
		// use this when u will just run using mvn test command (not from inside the IDE)
		// appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingPort(4723));
		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/appmain/global.properties");
		Properties prop = new Properties();
		prop.load(file);
			
		boolean flag = checkIfServerIsRunnning(4723);
		if (!flag) {
			appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
					.usingDriverExecutable(new File((String) prop.get("node_bin")))
					.withAppiumJS(new File((String) prop.get("appium_main")))
					.usingPort(4723));
			appiumService.start();
		}
			return appiumService;
	}
	
	public void stopServer() {
	    Runtime runtime = Runtime.getRuntime();
	    try {
	        runtime.exec("killall node");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static boolean checkIfServerIsRunnning(int port) {
		
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			
			serverSocket.close(); 
			} 
		catch (IOException e) {
			//If control comes here, then it means that the port is in use
			isServerRunning = true;
			} 
		finally {
			serverSocket = null;
			}
		return isServerRunning;
		}
	
	public static void sleep(int ms) {
	    try {
	        Thread.sleep(ms);
	    } catch (InterruptedException e) {
	        System.err.format("IOException: %s%n", e);
	    }
	}

}