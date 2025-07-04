package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RepositoryPage extends BasePage {
    @FindBy(xpath = "//a[@id='issues-tab']")
    private WebElement issuesTab;

    public RepositoryPage(WebDriver driver) {
        super(driver);
    }

    public IssuesPage clickIssuesTab() {
        issuesTab.click();
        return new IssuesPage(driver);
    }
}