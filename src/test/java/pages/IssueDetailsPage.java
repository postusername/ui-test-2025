package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IssueDetailsPage extends BasePage {
    @FindBy(xpath = "//bdi[contains(@class, 'markdown-title') and @data-testid='issue-title']")
    private WebElement titleElement;

    @FindBy(css = "div.Box-sc-g0xbh4-0.markdown-body p")
    private WebElement descriptionElement;

    // Альтернативные локаторы
    @FindBy(css = "h1.gh-header-title")
    private WebElement issueTitle;

    @FindBy(css = "td.comment-body")
    private WebElement issueDescription;

    public IssueDetailsPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }

    public String getIssueTitle() {
        wait.until(ExpectedConditions.visibilityOf(titleElement));
        return titleElement.getText();
    }
    public String getIssueDescription() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText();
    }

}