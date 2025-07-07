package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.tests.BaseTest;
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

    /**
     * Тест добавления комментария к issue
     * Проверяет возможность добавления комментария к созданной issue
     */
    @Test
    public void testAddCommentToIssue() {
        IssueDetailsPage issueDetailsPage = createIssue(ISSUE_TITLE, ISSUE_DESCRIPTION);
        issueDetailsPage.addComment(COMMENT_TEXT);
        assertTrue(issueDetailsPage.isCommentWithTextExists(COMMENT_TEXT), "Комментарий не добавлен");
        log.info("Комментарий успешно добавлен");
    }

    /**
     * Тест блокировки обсуждения issue
     * Проверяет возможность блокировки комментирования для issue
     */
    @Test
    public void testLockConversation() {
        IssueDetailsPage issueDetailsPage = createIssue(ISSUE_TITLE, ISSUE_DESCRIPTION);
        issueDetailsPage.clickLockConversation();
        assertTrue(issueDetailsPage.isConversationLocked(), "Обсуждение должно быть заблокировано");
        log.info("Обсуждение успешно заблокировано");
    }
}
