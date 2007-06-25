package gov.nih.nci.ispy.test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

public class GoogleTest extends SeleneseTestCase {
    private Selenium selenium;
    public void setUp() {
        selenium = new DefaultSelenium("localhost",
            4444, "*firefox", "http://caintegrator-dev.nci.nih.gov/ispy");
        selenium.start();
    }
    
    public void testNew() throws Exception {
        selenium.open("/ispy/logout.do");
        selenium.type("userName", "rossok");
        selenium.click("//input[@value='Submit']");
        selenium.waitForPageToLoad("30000");
        checkForVerificationErrors();
    }

    
    public void testGoogle() {
        selenium.open("http://www.google.com/webhp");
        selenium.type("q", "hello world");
        selenium.click("btnG");
        selenium.waitForPageToLoad("5000");
        assertEquals("hello world - Google Search", selenium.getTitle());
    }
    
    public void tearDown() {
        selenium.stop();
    }
}