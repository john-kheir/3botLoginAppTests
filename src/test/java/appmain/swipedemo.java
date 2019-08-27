package appmain;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.IOException;

public class swipedemo extends Base{
	
	@BeforeTest
	public void setUp() throws IOException {
		logger.error("This is setup");
		appiumService = startServer();
	}
	
	 @AfterTest
		public void tearDown() {
		 logger.info("This is teardown");
		 appiumService.stop();
		}

    
    @Test
    public void Test2() throws InterruptedException, IOException {
        // TODO Auto-generated method stub
    	logger.info("This is test2");
        AndroidDriver<AndroidElement> driver=Capabilities();
        
        

    }
    
   

}
