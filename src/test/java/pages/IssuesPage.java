package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IssuesPage extends BasePage {

    @FindBy(css = "a.prc-Button-ButtonBase-c50BI[href*='/issues/new']")
    private WebElement newIssueButton;

    private static final By NEW_ISSUE_BUTTON_LOCATOR = By.xpath("//a[contains(@href, '/new/choose')]");

    public IssuesPage(WebDriver driver) {
        super(driver);
    }

    public NewIssuePage clickNewIssueButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(newIssueButton));

        wait.until(d -> {
            try {
                return button.isDisplayed() && button.isEnabled();
            } catch (Exception e) {
                return false;
            }
        });

        newIssueButton.click();
        return new NewIssuePage(driver);
    }

}