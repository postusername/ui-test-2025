package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewIssuePage extends BasePage {
    @FindBy(xpath = "//input[@aria-label='Add a title' and @placeholder='Title']")
    private WebElement titleInput;

    @FindBy(xpath = "//textarea[@placeholder='Type your description here…']")
    private WebElement descriptionTextarea;

    @FindBy(xpath = "//button[@data-testid='create-issue-button']")
    private WebElement createIssueButton;

    public NewIssuePage(WebDriver driver) {
        super(driver);
    }

    public void createNewIssue(String title, String description) {
        waitAndSendKeys(titleInput, title);
        waitAndSendKeys(descriptionTextarea, description);
        waitAndClick(createIssueButton);
    }
}