package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.base.BaseTest;
import ru.github.pages.*;
import ru.github.utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты работы с комментариями
 */
public class CommentsTest extends BaseTest {

    private static final String ISSUE_TITLE = ConfigReader.getTestData("issue.test.title");
    private static final String ISSUE_DESCRIPTION = ConfigReader.getTestData("issue.test.description");
    private static final String COMMENT_TEXT = ConfigReader.getTestData("comment.test.text");

    @Test
    public void testAddCommentToIssue() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        issueDetailsPage.addComment(COMMENT_TEXT);
        log.info("Комментарий успешно добавлен");
    }

    @Test
    public void testLockConversation() {
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        issueDetailsPage.clickLockConversation();
        log.info("Обсуждение успешно заблокировано");
        
        assertTrue(issueDetailsPage.isConversationLocked(), "Обсуждение должно быть заблокировано");
    }
}
