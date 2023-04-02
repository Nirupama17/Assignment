package BBC.Website;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BbcWebsite {

	public WebDriver driver;
	public String BaseURL = "https://www.bbc.co.uk/";

	@Test
	public void loginPage() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver","path of chromedriver.exe");
		
		//Adding below setting to make compatible with Chrome browser 111 version
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");
		
		System.out.println(BaseURL);
		driver = new ChromeDriver(option);
		driver.get(BaseURL);
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);

		//Maximising window size
		driver.manage().window().maximize();

		// Verify that the page title contains the expected text
		if (driver.getTitle().contains("BBC")) {
			System.out.println("Page title contains 'BBC'.");
		} else {
			System.out.println("Page title does not contain 'BBC'.");
		}

		// Click the Sign In button
		driver.findElement(
				By.xpath("//*[@id='header-content']//span[@class='ssrcss-17cn1ks-AccountIconWrapper e1gviwgp5']"))
				.click();

		driver.findElement(By.linkText("Register now")).click();
		
		//Waiting until 'Under 13/ 13 or Over text' and click
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='container']//a[2]")));

		driver.findElement(By.xpath("//*[@id='container']//a[2]")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Enter the user's date of birth
		driver.findElement(By.id("day-input")).sendKeys("01");
		driver.findElement(By.id("month-input")).sendKeys("01");
		driver.findElement(By.id("year-input")).sendKeys("2000");

		// Click the Continue button	
		driver.findElement(By.id("submit-button")).click();

		// Enter the email address
		driver.findElement(By.id("user-identifier-input")).sendKeys("xyz@hotmail.com");
		// Enter the password
		driver.findElement(By.id("password-input")).sendKeys("password@hotmail");

		driver.findElement(By.id("postcode-input")).sendKeys("RG1 1ST");

		//Selecting Gender
		WebElement genderDropdown = driver.findElement(By.id("gender-input"));
		Select select = new Select(genderDropdown);
		select.selectByVisibleText("Female");

		// Click the Sign In button
		driver.findElement(By.id("submit-button")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.findElement(By.xpath("//span[text()='No, thanks']")).click();

		driver.findElement(By.id("submit-button")).click();

		// Verify that the user is signed in
		if (driver.getCurrentUrl().equals("https://www.bbc.co.uk/")) {
			System.out.println("Sign in successful. Test passed!");
		} else {
			System.out.println("Sign in unsuccessful. Test failed!");
		}

		// Wait for the search results to load
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Print the page title to verify that the search results are displayed
		System.out.println(driver.getTitle());

		driver.findElement(By.xpath("//*[@id='more-menu-button']/a")).click();

		driver.findElement(By.xpath(
				".//div[@class='ssrcss-1obp207-MenuWrapper e1y2z29v0']//a[contains(@href,'https://www.bbc.co.uk/food')]"))
				.click();

		//Verifying 'Food' text
		WebElement Message = driver.findElement(By.xpath("//a[@class='page-title__logo']"));
		Assert.assertEquals(Message.getText(), "Food");

		// Close the browser
		driver.quit();
	}
}
