package selenium;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;


public class test_case {

		static WebDriver driver= new ChromeDriver();
		static Actions action = new Actions(driver);

	public static void homepage() {

		try {

			driver.get("https://www.n11.com/");
			String title1 = driver.getTitle();
			String title_check = "n11.com - Alýþveriþin Uðurlu Adresi";
			Assert.assertEquals(title_check, title1);
			System.out.println("The title of n11: "+driver.getTitle());

		}
		catch (AssertionError a){
			System.out.println("Homepage is not found");

			driver.quit();
		}
		}
	public static void login() throws InterruptedException {
		driver.findElement(By.className("btnSignIn")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("email")).sendKeys("denemetest5824@gmail.com");
		driver.findElement(By.id("password")).sendKeys("58585858m");
		driver.findElement(By.id("loginButton")).click();
		Thread.sleep(5000);
		
		try {
			driver.findElement(By.cssSelector("#header > div > div > div.hMedMenu > div.customMenu > div.myAccountHolder.customMenuItem.hasMenu.withLocalization > div.myAccount > a.menuLink.user"));
			System.out.println("Successfully '"+driver.findElement(By.cssSelector("#header > div > div > div.hMedMenu > div.customMenu > div.myAccountHolder.customMenuItem.hasMenu.withLocalization > div.myAccount > a.menuLink.user")).getText() +"' logged in");
			
		} catch (NoSuchElementException b ) {
			System.out.println("Could not log in");
			driver.quit();
		}
	}
	public static void search() throws InterruptedException {
		try {
		driver.findElement(By.id("searchData")).sendKeys("Iphone");
		driver.findElement(By.className("searchBtn")).click();
		Thread.sleep(2000);
		String title1 = driver.getTitle();
		String title_check ="Iphone - n11.com";
		Assert.assertEquals(title_check, title1);
		System.out.println("the searched word: "+driver.getTitle().split("-")[0]);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("#contentListing > div > div > div.productArea > div.pagination > a:nth-child(2)")).click();;
		
		}
		catch(AssertionError a) {
			System.out.println("Could not search");
		}
		
	}
	public static void see_favorites() throws InterruptedException {
		List<WebElement> a =driver.findElement(By.cssSelector("#contentListing > div > div > div.productArea > section.group.listingGroup.resultListGroup")).findElements(By.className("followBtn"));
		a.get(2).click();

		
	}
	public static void check_favorites() throws InterruptedException {
		
       WebElement element = driver.findElement(By.linkText("Hesabým"));
        action.moveToElement(element).build().perform();
        driver.findElement(By.linkText("Ýstek Listem / Favorilerim")).click();
        Thread.sleep(5000);
		try {
			String title1=driver.getTitle();
			String title_check="Ýstek Listelerim - n11.com";
			Assert.assertEquals(title_check, title1);
			System.out.println("Showing my favorites");
		}
		catch (AssertionError a) {
			System.out.println("Could not show my favorites");
		}
	}
	public static void delete_favorite() throws InterruptedException {
		driver.findElement(By.className("listItemTitle")).click();
		Thread.sleep(3000);
		driver.findElement(By.className("deleteProFromFavorites")).click();
		System.out.println("Deleted product: "+driver.findElement(By.className("productName")).getText());
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("body > div.lightBox > div > div > span")).click();
		
		try {
			driver.findElement(By.className("column wishListColumn "));
			}
			catch (NoSuchElementException e) {
				System.out.println("The product is deleted");
				log_out();
				
			}
	}
	public static void log_out() throws InterruptedException {
			driver.get("https://www.n11.com/");
	        action.moveToElement(driver.findElement(By.linkText("Hesabým"))).moveToElement(driver.findElement(By.className("logoutBtn"))).click().build().perform();
	        Thread.sleep(2000);
	        
	}
	public static void report() throws FileNotFoundException {
        File file = new File("log.txt");
        PrintWriter write = new PrintWriter(file);
        Logs logs = driver.manage().logs();
        LogEntries logEntries = logs.get(LogType.BROWSER);
        for(LogEntry logEntry :logEntries)
        {
             if (logEntry.getMessage().toLowerCase().contains("error")) {
                write.println(new Date(logEntry.getTimestamp())+" Error Message: "+logEntry.getMessage());
            } else if (logEntry.getMessage().toLowerCase().contains("warning")){
               write.println(new Date(logEntry.getTimestamp())+" Warning Message: "+logEntry.getMessage());
            }else{
                write.println(new Date(logEntry.getTimestamp())+" Information Message: "+logEntry.getMessage());
            }
        }
        write.close();
        driver.quit();
        System.out.println("Log file is saved " +file.getAbsolutePath());
	}
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {

		homepage();
		login();
		search();
		see_favorites();
		check_favorites();
		delete_favorite();
		report();

	}

}
