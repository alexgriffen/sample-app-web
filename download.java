package com.swaglabs.Tests;

import com.swaglabs.Tests.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.List;

public class download extends TestBase {

    /**
     * Runs a simple test verifying file download.
     *
     * @throws InvalidElementStateException
     * @throws InterruptedException
     */
    @Test(dataProvider = "hardCodedBrowsers")
    public void downloadTest (String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException {

        //create webdriver session
        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        driver.navigate().to("https://www.thinkbroadband.com/download");

        //We find the download links
        List<WebElement> list =driver.findElements(By.cssSelector("div.module>p>a>img"));

        //Click the last one to downaload 5MB file :)
        WebElement el = list.get(list.size()-1);
        el.click();


        File folder = new File(System.getProperty("user.dir"));

        System.out.println(folder);

        //List the files on that folder
        File[] listOfFiles = folder.listFiles();
        boolean found = false;
        File f = null;
        //Look for the file in the files
        // You should write smart REGEX according to the filename
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String fileName = listOfFile.getName();
                System.out.println("File " + listOfFile.getName());
                if (fileName.matches("5MB.zip")) {
                    f = new File(fileName);
                    found = true;
                }
            }
        }
        Assert.assertTrue(found, "Downloaded document is not found");
        f.deleteOnExit();
    }
}
