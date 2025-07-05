package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.base.BaseTest;
import ru.github.pages.*;
import ru.github.utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты назначения и снятия назначения issue
 */
public class IssueAssignmentTest extends BaseTest {

    private static final String ISSUE_TITLE = ConfigReader.getTestData("issue.test.title");
    private static final String ISSUE_DESCRIPTION = ConfigReader.getTestData("issue.test.description");
    private static final String ASSIGNEE_USERNAME = ConfigReader.getTestData("issue.test.assignee");
    private static final String LABEL_NAME = ConfigReader.getTestData("issue.test.label");
    private static final String NONEXISTENT_LABEL = ConfigReader.getTestData("issue.test.nonexistent.label");

    @Test
    public void testAssignUserToIssue() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        var issueActions = issueDetailsPage.getIssueActions();
        assertTrue(issueActions.isAssigneesVisible(), "Секция назначений должна быть видна");
        
        issueActions.assignYourself();
        assertTrue(issueActions.isCurrentUserAssigned(), "Назначение пользователя работает корректно");
    }

    @Test
    public void testAddLabelToIssue() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        var issueActions = issueDetailsPage.getIssueActions();
        issueActions.selectLabel(LABEL_NAME);
        assertTrue(issueActions.isLabelSelected(LABEL_NAME), "Метка должна быть назначена issue");
    }

    @Test
    public void testSearchNonExistentLabel() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        var issueActions = issueDetailsPage.getIssueActions();
        
        assertTrue(issueActions.isLabelsVisible(), "Секция меток должна быть видна");
        issueActions.searchLabel(NONEXISTENT_LABEL);
        
        boolean createSuggestion = issueActions.isCreateLabelSuggestionVisible(NONEXISTENT_LABEL);
        boolean noResults = issueActions.hasNoSearchResults();
        
        assertTrue(createSuggestion || noResults, 
            "Должно быть предложение создать метку или отсутствие результатов поиска");
        
        log.info("Тест поиска несуществующей метки завершен успешно");
    }
}
