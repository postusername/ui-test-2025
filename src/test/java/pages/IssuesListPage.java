package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IssuesListPage extends BasePage {
    public IssuesListPage(WebDriver driver) {
        super(driver);
    }

    public boolean isIssueDisplayed(String title) {
        try {
            WebElement issue = wait.until(d ->
                    d.findElement(By.xpath("//a[contains(text(), '" + title + "')]")));
            return issue.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}