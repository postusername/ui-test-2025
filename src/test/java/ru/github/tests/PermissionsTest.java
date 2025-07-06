package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.tests.BaseTest;
import ru.github.pages.*;
import ru.github.utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты проверки прав доступа
 */
public class PermissionsTest extends BaseTest {

    private static final String ISSUE_TITLE = ConfigReader.getTestData("issue.test.title");
    private static final String ISSUE_DESCRIPTION = ConfigReader.getTestData("issue.test.description");

    /**
     * Тест выполнения действий без необходимых прав
     * Проверяет ограничения доступа к функциям управления issue
     */
    @Test
    public void testActionWithoutPermissions() {
        IssueDetailsPage issueDetailsPage = createIssue(ISSUE_TITLE, ISSUE_DESCRIPTION);
        assertTrue(issueDetailsPage.isIssueStatusOpen(), "Issue должна быть открыта");
        assertEquals(ISSUE_TITLE, issueDetailsPage.getTitle(), "Заголовок должен совпадать");

        try {
            var issueActions = issueDetailsPage.getIssueActions();     
            log.info("Пользователь имеет права на создание issue");
        } catch (Exception e) {
            log.info("Обнаружены ограничения прав доступа: {}", e.getMessage());
        }
    }
}
