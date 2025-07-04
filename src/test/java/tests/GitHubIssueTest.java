package tests;

import base.BaseTest;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.*;

public class GitHubIssueTest extends BaseTest {
    @Test
    public void testCreateNewIssueWithAuth() {
        driver.get(BASE_URL + "/login");

        // Логин
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        // Переход в репозиторий
        driver.get(BASE_URL + "/" + USERNAME + "/" + REPO_NAME);

        // Создание issue
        RepositoryPage repositoryPage = new RepositoryPage(driver);

        IssuesPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssueButton();

        String expectedTitle = "Title_test";
        String expectedDescription = "description_test";

        // Создаем issue
        newIssuePage.createNewIssue(expectedTitle, expectedDescription);

        // Проверки
        IssueDetailsPage issueDetailsPage = new IssueDetailsPage(driver);

        wait.until(ExpectedConditions.titleContains(expectedTitle));



        String actualTitle = issueDetailsPage.getIssueTitle();
        assert actualTitle.equals(expectedTitle) :
                "Заголовок issue не совпадает. Ожидалось: " + expectedTitle +
                        ", получено: " + actualTitle;

        String actualDescription = issueDetailsPage.getIssueDescription();
        assert actualDescription.equals(expectedDescription) :
                "Описание issue не совпадает. Ожидалось: " + expectedDescription +
                        ", получено: " + actualDescription;


        driver.get(BASE_URL + "/" + USERNAME + "/" + REPO_NAME + "/issues");
        IssuesListPage issuesListPage = new IssuesListPage(driver);

        wait.until(d -> issuesListPage.isIssueDisplayed(expectedTitle));

        assert issuesListPage.isIssueDisplayed(expectedTitle) :
                "Issue с заголовком '" + expectedTitle + "' не найдена в списке";
    }
}