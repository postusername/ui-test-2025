package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.pages.IssueDetailsPage;
import ru.github.pages.MainPage;
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
        // Создаем issue с первым пользователем
        IssueDetailsPage issueDetailsPage = createIssue(ISSUE_TITLE, ISSUE_DESCRIPTION);
        assertTrue(issueDetailsPage.isIssueStatusOpen(), "Issue должна быть открыта");
        assertEquals(ISSUE_TITLE, issueDetailsPage.getTitle(), "Заголовок должен совпадать");
        
        // Получаем URL текущей issue для последующего перехода
        String issueUrl = issueDetailsPage.getCurrentUrl();
        log.info("URL созданной issue: {}", issueUrl);
        
        // Выходим из системы
        MainPage mainPage = authService.getMainPage();
        mainPage.getHeaderComponent().signOut();
        log.info("Выход из системы выполнен");
        
        // Авторизуемся вторым пользователем
        authService.authSecondaryUser();
        log.info("Авторизация вторым пользователем выполнена");
        
        // Переходим к той же issue
        openUrl(issueUrl);
        IssueDetailsPage issueDetailsPageSecondUser = new IssueDetailsPage();
        
        // Проверяем ограничения доступа
        boolean closeButtonDisabled = issueDetailsPageSecondUser.isCloseButtonDisabled();
        log.info("Кнопка 'Close issue' заблокирована: {}", closeButtonDisabled);
        
        // Проверяем tooltip с сообщением о недостатке прав
        String permissionTooltip = issueDetailsPageSecondUser.getNoPermissionTooltip();
        log.info("Tooltip с правами доступа: {}", permissionTooltip);
        
        // Проверяем, что кнопка заблокирована или недоступна
        assertTrue(closeButtonDisabled || permissionTooltip.contains("do not have permissions"), 
                "Должны быть ограничения прав доступа для второго пользователя");
        
        log.info("Тест пройден: обнаружены ограничения прав доступа для второго пользователя");
    }
    
    /**
     * Открывает указанный URL
     * @param url URL для открытия
     */
    private void openUrl(String url) {
        com.codeborne.selenide.Selenide.open(url);
    }
}
