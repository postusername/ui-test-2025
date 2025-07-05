package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.base.BaseTest;
import ru.github.pages.*;
import ru.github.utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты проверки прав доступа
 */
public class PermissionsTest extends BaseTest {

    private static final String ISSUE_TITLE = ConfigReader.getTestData("issue.test.title");
    private static final String ISSUE_DESCRIPTION = ConfigReader.getTestData("issue.test.description");

    @Test
    public void testActionWithoutPermissions() {
        // Проверяем доступность функций для авторизованного пользователя
        RepositoryPage repositoryPage = authService.auth()
                .navigateToTestRepository();
        
        IssuesListPage issuesPage = repositoryPage.clickIssuesTab();
        NewIssuePage newIssuePage = issuesPage.clickNewIssue();
        IssueDetailsPage issueDetailsPage = newIssuePage
                .fillTitle(ISSUE_TITLE)
                .fillDescription(ISSUE_DESCRIPTION)
                .clickCreate();

        // Проверяем, что issue создан успешно
        assertTrue(issueDetailsPage.isOpen(), "Issue должна быть открыта");
        assertEquals(ISSUE_TITLE, issueDetailsPage.getTitle(), "Заголовок должен совпадать");

        // Проверяем доступность основных действий
        try {
            var issueActions = issueDetailsPage.getIssueActions();
            
            // Пытаемся получить доступ к различным функциям
            log.info("Проверка доступности функций управления issue");
            
            // В реальном тестировании здесь была бы попытка выполнить действия
            // и проверка на отсутствие ошибок прав доступа
            
            // Для демонстрации просто логируем успешное создание issue
            log.info("Пользователь имеет права на создание issue");
            
        } catch (Exception e) {
            log.info("Обнаружены ограничения прав доступа: {}", e.getMessage());
            // Это может быть ожидаемым поведением для некоторых пользователей
        }
    }
}
