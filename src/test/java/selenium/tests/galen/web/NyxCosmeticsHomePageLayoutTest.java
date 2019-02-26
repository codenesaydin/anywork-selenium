package selenium.tests.galen.web;


import context.base.AbstractWebTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.Dimension;
import selenium.pages.UrlFactory;
import selenium.tests.galen.path.SpecFilePath;

import java.io.IOException;
import java.util.Arrays;

public class NyxCosmeticsHomePageLayoutTest extends AbstractWebTest
{

    @Before
    public void init() throws Exception
    {
        super.init();
        driver.manage().window().setSize(new Dimension(1200, 800));
    }

    @Ignore
    @Test
    @DisplayName("The Main Page Layout Design")
    public void testMainPageLayoutDesign() throws IOException
    {
        navigateToURL(UrlFactory.MAIN_URL);
        wait(5);
        pageLongDownScroll();
        checkLayoutDesign(SpecFilePath.HOME_PAGE_WEB.getFilePath(), Arrays.asList(PlatformName.DESKTOP.platformName), this.getClass().getSimpleName());
    }
}
