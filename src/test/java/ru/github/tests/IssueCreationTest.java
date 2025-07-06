package ru.github.tests;

import org.junit.jupiter.api.Test;
import ru.github.tests.BaseTest;
import ru.github.pages.IssueDetailsPage;
import ru.github.pages.IssuesListPage;
import ru.github.pages.MainPage;
import ru.github.pages.NewIssuePage;

import ru.github.utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты создания и редактирования issue
 */
public class IssueCreationTest extends BaseTest {

    private static final String ISSUE_TITLE = ConfigReader.getTestData("issue.test.title");
    private static final String ISSUE_DESCRIPTION = ConfigReader.getTestData("issue.test.description");
    private static final String UPDATED_TITLE = ConfigReader.getTestData("issue.test.updated.title");

    /**
     * Тест создания issue с пустым заголовком
     * Проверяет валидацию формы при попытке создания issue без заголовка
     */
    @Test
    public void testCreateIssueWithEmptyTitle() {
        NewIssuePage newIssuePage = getNewIssuePage();
        newIssuePage.fillDescription(ISSUE_DESCRIPTION);
        newIssuePage.clickCreateWithoutTitle();

        assertTrue(newIssuePage.isEmptyTitleErrorDisplayed(), "Должна быть ошибка валидации");
    }

    /**
     * Тест создания корректной issue
     * Проверяет полный цикл создания issue с валидными данными
     */
    @Test
    public void testCreateValidIssue() {
        IssueDetailsPage issueDetailsPage = createIssue(ISSUE_TITLE, ISSUE_DESCRIPTION);

        assertEquals(ISSUE_TITLE, issueDetailsPage.getTitle(), "Заголовок issue должен совпадать");
        assertTrue(issueDetailsPage.isIssueStatusOpen(), "Issue должна быть открыта");

        MainPage mainPage = authService.getMainPage();
        IssuesListPage issuesListPage = mainPage
                .navigateToTestRepository()
                .clickIssuesTab();
        assertTrue(issuesListPage.isIssueWithTitleExists(ISSUE_TITLE), "Issue должен быть в списке");
    }

    /**
     * Тест редактирования заголовка issue
     * Проверяет возможность изменения заголовка после создания issue
     */
    @Test
    public void testEditIssueTitle() {
        IssueDetailsPage issueDetailsPage = createIssue(ISSUE_TITLE, ISSUE_DESCRIPTION);
        issueDetailsPage.editTitle(UPDATED_TITLE);
        assertEquals(UPDATED_TITLE, issueDetailsPage.getTitle(), "Заголовок должен быть обновлен");
    }
}
