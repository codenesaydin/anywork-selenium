package context.base;

import context.driver.DriverManager;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.pages.UrlFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class AbstractSeleniumTest extends DriverManager implements SeleniumActions
{
    @Override
    public String getCurrentURL()
    {
        return driver.getCurrentUrl();
    }

    @Override
    public void navigateToURL(UrlFactory url)
    {
        int pageLoadTimeOut = Integer.parseInt(prop.getProperty("page.load.timeout"));
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.MINUTES);
        driver.navigate().to(url.pageUrl);
    }

    @Override
    public void openNewTab()
    {
        ((JavascriptExecutor) driver).executeScript("window.open()");
    }

    @Override
    public void click(WebElement element)
    {
        waitElementToBeClickable(element);
        element.click();
    }

    @Override
    public void listElementRandomClick(List<WebElement> element)
    {
        WebElement clickableElement = element.get(new Random().nextInt(element.size()));
        scrollToElement(clickableElement);
        mouseOver(clickableElement);
        waitElementToBeClickable(clickableElement);
        clickableElement.click();
    }

    @Override
    public void rightClick(WebElement element)
    {
        org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(driver);
        action.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
    }

    @Override
    public void doubleClick(WebElement element)
    {
        org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(driver);
        action.doubleClick(element).perform();
    }

    @Override
    public void mouseOver(WebElement element)
    {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    @Override
    public void selectOptionIndex(WebElement element, int index)
    {
        new Select(element).selectByIndex(index);
    }

    @Override
    public void selectOptionValue(WebElement element, String itemValue)
    {
        new Select(element).selectByValue(itemValue);
    }

    @Override
    public void selectOptionVisibleText(WebElement element, String visibleText)
    {
        new Select(element).selectByVisibleText(visibleText);
    }

    @Override
    public void sendKeys(WebElement element, CharSequence text)
    {
        element.sendKeys(text);
    }

    @Override
    public boolean isDisplayed(WebElement element)
    {
        return element.isDisplayed();
    }

    @Override
    public boolean isAttributePresent(WebElement element, String attribute)
    {
        Boolean result = false;

        try
        {
            String value = element.getAttribute(attribute);
            if (value != null)
            {
                result = true;
            }
        }
        catch (Exception e)
        {
        }

        return result;
    }

    @Override
    public void waitElementToBeClickable(WebElement element)
    {
        int waitTimeOutSeconds = Integer.valueOf(prop.getProperty("wait.timeout.seconds"));
        WebDriverWait wait = new WebDriverWait(driver, waitTimeOutSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    @Override
    public void waitElementVisible(WebElement element)
    {
        int waitTimeOutSeconds = Integer.valueOf(prop.getProperty("wait.timeout.seconds"));
        WebDriverWait wait = new WebDriverWait(driver, waitTimeOutSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    @Override
    public void waitElementNotVisible(WebElement element)
    {
        int waitTimeOutSeconds = Integer.valueOf(prop.getProperty("wait.timeout.seconds"));
        WebDriverWait wait = new WebDriverWait(driver, waitTimeOutSeconds);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    @Override
    public void clearInput(WebElement element)
    {
        element.clear();
    }

    @Override
    public void clearMultipleSelectedOption(WebElement element)
    {
        new Select(element).deselectAll();
    }

    @Override
    public String getText(WebElement element)
    {
        return element.getText();
    }

    @Override
    public String getSelectedOptionText(WebElement element)
    {
        Select dropdown = new Select(element);
        return dropdown.getFirstSelectedOption().getText();
    }

    @Override
    public String getAttribute(WebElement element, String attributeName)
    {
        return element.getAttribute(attributeName);
    }

    @Override
    public String selectedOptionGetText(WebElement element)
    {
        return new Select(element).getFirstSelectedOption().getText();
    }

    @Override
    public String selectedOptionGetValue(WebElement element)
    {
        return new Select(element).getFirstSelectedOption().getAttribute("value");
    }

    @Override
    public void wait(int seconds)
    {
        try
        {
            Thread.sleep(seconds * 1000);
        }
        catch (Exception ex)
        {
        }

    }

    @Override
    public void pageLoad()
    {
        int pageLoadTimeOut = Integer.valueOf(prop.getProperty("page.load.timeout"));
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.SECONDS);
    }

    @Override
    public void implicitlyWait()
    {
        int implicitlyWait = Integer.valueOf(prop.getProperty("implicitly.wait"));
        driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
    }

    @Override
    public void assertEquals(Object actual, Object expected)
    {
        Assert.assertEquals(actual, expected);
    }

    @Override
    public void checkBoxChecked(WebElement element)
    {
        element.isSelected();
    }

    @Override
    public void pageRefresh()
    {
        driver.navigate().refresh();
    }

    @Override
    public void keysENTER(WebElement element)
    {
        element.sendKeys(Keys.ENTER);
    }

    @Override
    public void switchWindowTab(int tab)
    {
        ArrayList<String> TabSwitch = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(TabSwitch.get(tab));
    }

    @Override
    public void switchParentFrame()
    {
        driver.switchTo().parentFrame();
    }

    @Override
    public void switchFrame(WebElement element)
    {
        driver.switchTo().frame(element);
    }

    @Override
    public String getWindowHandle()
    {
        return driver.getWindowHandle();
    }

    @Override
    public void deleteCookie()
    {
        driver.manage().deleteAllCookies();
    }

    @Override
    public void dragAndDrop(WebElement from, WebElement to) throws Exception
    {
        org.openqa.selenium.interactions.Actions act = new org.openqa.selenium.interactions.Actions(driver);

        scrollToElement(from);
        wait(1);
        act.clickAndHold(from).build().perform();
        scrollToElement(to);
        wait(1);
        act.moveToElement(to).build().perform();
        act.release(to).build().perform();
    }


    //-- Actions JavaScript

    @Override
    public void pageZoom(String zoomValue)
    {
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='" + zoomValue + "%'");
    }

    @Override
    public void pageScroll(int x, int y)
    {
        JavascriptExecutor scroll = driver;
        scroll.executeScript("scroll(" + x + "," + y + ")");
    }

    @Override
    public void scrollToElement(WebElement element)
    {
        JavascriptExecutor scroll = driver;
        scroll.executeScript("arguments[0].scrollIntoView();", element);
    }

    @Override
    public void clickViaJs(WebElement element)
    {
        JavascriptExecutor executor = driver;
        executor.executeScript("arguments[0].getText();", element);
    }

    @Override
    public void createFolder(String folderPathName)
    {
        File theDir = new File(folderPathName);

        if (!theDir.exists())
        {
            boolean result = false;

            try
            {
                theDir.mkdir();
                result = true;
            }
            catch (SecurityException se)
            {
                //handle it
            }
            if (result)
            {
                System.out.println("DIR created");
            }
        }

    }

    @Override
    public void pageLongDownScroll()
    {
        for (int s = 100; s <= 1700; s += 100)
        {
            wait(1);
            pageScroll(0, s);
        }
    }


}
