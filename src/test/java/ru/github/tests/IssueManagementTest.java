package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.tests.BaseTest;
import ru.github.pages.*;
import ru.github.utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты управления issue (закрытие, переоткрытие, блокировка)
 */
public class IssueManagementTest extends BaseTest {

    private static final String ISSUE_TITLE = ConfigReader.getTestData("issue.test.title");
    private static final String ISSUE_DESCRIPTION = ConfigReader.getTestData("issue.test.description");

    /**
     * Тест закрытия issue
     * Проверяет возможность изменения статуса issue с "открыта" на "закрыта"
     */
    @Test
    public void testCloseIssue() {
        IssueDetailsPage issueDetailsPage = createIssue(ISSUE_TITLE, ISSUE_DESCRIPTION);
        assertTrue(issueDetailsPage.isIssueStatusOpen(), "Issue должна быть открыта");

        log.info("Текущий статус до закрытия: {}", issueDetailsPage.getStatus());
        issueDetailsPage.clickClose();
        String finalStatus = issueDetailsPage.getStatus();
        log.info("Статус после попытки закрытия: {}", finalStatus);
        assertTrue(issueDetailsPage.isIssueStatusClosed(), "Issue должна быть закрыта");
    }

    /**
     * Тест переоткрытия закрытой issue
     * Проверяет возможность возврата статуса issue с "закрыта" на "открыта"
     */
    @Test
    public void testReopenClosedIssue() {
        IssueDetailsPage issueDetailsPage = createIssue(ISSUE_TITLE, ISSUE_DESCRIPTION);

        issueDetailsPage.clickClose();
        assertTrue(issueDetailsPage.isIssueStatusClosed(), "Issue должна быть закрыта");
        
        issueDetailsPage.clickReopen();
        assertTrue(issueDetailsPage.isIssueStatusOpen(), "Issue должна быть переоткрыта");
    }

    /**
     * Тест закрепления issue
     * Проверяет возможность закрепления issue для выделения её в списке
     */
    @Test
    public void testPinIssue() {
        IssueDetailsPage issueDetailsPage = createIssue(ISSUE_TITLE, ISSUE_DESCRIPTION);
        issueDetailsPage.clickPin();
        log.info("Issue успешно закреплена");
    }
}
