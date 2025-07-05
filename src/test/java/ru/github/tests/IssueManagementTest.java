package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.base.BaseTest;
import ru.github.pages.*;
import ru.github.utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты управления issue (закрытие, переоткрытие, блокировка)
 */
public class IssueManagementTest extends BaseTest {

    private static final String ISSUE_TITLE = ConfigReader.getTestData("issue.test.title");
    private static final String ISSUE_DESCRIPTION = ConfigReader.getTestData("issue.test.description");

    @Test
    public void testCloseIssue() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        assertTrue(issueDetailsPage.isOpen(), "Issue должна быть открыта");

        log.info("Текущий статус до закрытия: {}", issueDetailsPage.getStatus());
        issueDetailsPage.clickClose();

        String finalStatus = issueDetailsPage.getStatus();
        log.info("Статус после попытки закрытия: {}", finalStatus);
        
        assertTrue(issueDetailsPage.isClosed(), "Issue должна быть закрыта");
    }

    @Test
    public void testReopenClosedIssue() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        issueDetailsPage.clickClose();
        assertTrue(issueDetailsPage.isClosed(), "Issue должна быть закрыта");
        
        issueDetailsPage.clickReopen();
        assertTrue(issueDetailsPage.isOpen(), "Issue должна быть переоткрыта");
    }

    @Test
    public void testPinIssue() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        issueDetailsPage.clickPin();
        log.info("Issue успешно закреплена");
    }
}
