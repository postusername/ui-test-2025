package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BaseElement {
    protected WebDriver driver;
    protected WebElement element;

    public BaseElement(WebDriver driver, WebElement element) {
        this.driver = driver;
        this.element = element;
        PageFactory.initElements(driver, this);
    }
}