package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.base.BaseTest;
import ru.github.pages.IssueDetailsPage;
import ru.github.pages.IssuesListPage;
import ru.github.pages.MainPage;
import ru.github.pages.NewIssuePage;
import ru.github.pages.RepositoryPage;
import ru.github.utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты создания и редактирования issue
 */
public class IssueCreationTest extends BaseTest {

    private static final String ISSUE_TITLE = ConfigReader.getTestData("issue.test.title");
    private static final String ISSUE_DESCRIPTION = ConfigReader.getTestData("issue.test.description");
    private static final String UPDATED_TITLE = ConfigReader.getTestData("issue.test.updated.title");

    @Test
    public void testCreateIssueWithEmptyTitle() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        
        newIssuePage.fillDescription(ISSUE_DESCRIPTION);
        
        newIssuePage.clickCreateWithoutTitle();
        
        assertTrue(newIssuePage.hasValidationError(), "Должна быть ошибка валидации");
    }

    @Test
    public void testCreateValidIssue() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        assertEquals(ISSUE_TITLE, issueDetailsPage.getTitle(), "Заголовок issue должен совпадать");
        assertTrue(issueDetailsPage.isOpen(), "Issue должна быть открыта");

        MainPage mainPage = authService.getMainPage();
        IssuesListPage issuesListPage = mainPage.navigateToTestRepository()
                .clickIssuesTab();
        
        assertTrue(issuesListPage.isIssueExists(ISSUE_TITLE), "Issue должен быть в списке");
    }

    @Test
    public void testEditIssueTitle() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        issueDetailsPage.editTitle(UPDATED_TITLE);

        assertEquals(UPDATED_TITLE, issueDetailsPage.getTitle(), "Заголовок должен быть обновлен");
    }
}
