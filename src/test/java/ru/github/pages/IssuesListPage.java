package ru.github.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.github.base.BasePage;
import ru.github.components.IssueRowComponent;
import ru.github.pages.NewIssuePage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Страница со списком issues
 */
public class IssuesListPage extends BasePage {
    
    private static final String NEW_ISSUE_BUTTON_TEXT = "New issue";
    private static final String CLOSED_ISSUES_FILTER = "is:issue is:closed";
    
    private final SelenideElement newIssueButton = $("a[data-variant='primary']");
    private final SelenideElement searchField = $("#repository-input");
    private final ElementsCollection issueRows = $$(".Box-row");
    private final SelenideElement noIssuesMessage = $(".blankslate");
    
    /**
     * Конструктор страницы списка issues
     */
    public IssuesListPage() {
        super();
        waitForPageLoad();
    }
    
    /**
     * Нажимает кнопку создания новой issue
     * @return страница создания новой issue
     */
    public NewIssuePage clickNewIssue() {
        log.info("Нажатие кнопки 'New issue'");
        
        // Запоминаем количество окон до клика
        int initialWindowCount = WebDriverRunner.getWebDriver().getWindowHandles().size();
        
        newIssueButton.shouldBe(visible).click();
        
        // Ждем появления нового окна с WebDriverWait
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10));
        boolean newWindowOpened = false;
        
        try {
            wait.until(driver -> driver.getWindowHandles().size() > initialWindowCount);
            newWindowOpened = true;
        } catch (Exception e) {
            log.info("Новое окно не открылось за отведенное время");
        }
        
        if (newWindowOpened) {
            // Переключаемся на новое окно
            String originalWindow = WebDriverRunner.getWebDriver().getWindowHandle();
            for (String windowHandle : WebDriverRunner.getWebDriver().getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    WebDriverRunner.getWebDriver().switchTo().window(windowHandle);
                    break;
                }
            }
        } else {
            log.info("Новое окно не открылось, остаемся в текущем окне");
        }
        
        return new NewIssuePage();
    }
    
    /**
     * Переходит к issue по заголовку
     * @param issueTitle заголовок issue
     * @return страница деталей issue
     */
    public IssueDetailsPage clickIssueByTitle(String issueTitle) {
        log.info("Переход к issue с заголовком: {}", issueTitle);
        SelenideElement issueLink = $x("//a[contains(@class, 'Link--primary') and contains(text(), '" + issueTitle + "')]");
        issueLink.shouldBe(visible).click();
        return new IssueDetailsPage();
    }
    
    /**
     * Получает компонент строки issue по заголовку
     * @param issueTitle заголовок issue
     * @return компонент строки issue
     */
    public IssueRowComponent getIssueRowByTitle(String issueTitle) {
        log.info("Получение строки issue с заголовком: {}", issueTitle);
        SelenideElement issueRow = $x("//div[contains(@class, 'Box-row') and .//a[contains(text(), '" + issueTitle + "')]]");
        return new IssueRowComponent(issueRow);
    }
    
    /**
     * Выполняет поиск issues
     * @param searchQuery поисковый запрос
     * @return текущая страница с результатами поиска
     */
    public IssuesListPage searchIssues(String searchQuery) {
        log.info("Поиск issues по запросу: {}", searchQuery);
        searchField.shouldBe(visible).setValue(searchQuery).pressEnter();
        return this;
    }
    
    /**
     * Фильтрует закрытые issues
     * @return текущая страница с закрытыми issues
     */
    public IssuesListPage filterClosedIssues() {
        log.info("Фильтрация закрытых issues");
        return searchIssues(CLOSED_ISSUES_FILTER);
    }
    
    /**
     * Проверяет, что issue с заголовком существует
     * @param issueTitle заголовок issue
     * @return true, если issue существует
     */
    public boolean isIssueExists(String issueTitle) {
        log.info("Проверка существования issue с заголовком: {}", issueTitle);
        SelenideElement issueLink = $x("//a[@data-testid='issue-pr-title-link' and .//span[text()='" + issueTitle + "']]");
        boolean exists = issueLink.exists();
        log.info("Issue '{}' существует: {}", issueTitle, exists);
        return exists;
    }
    
    /**
     * Получает количество отображаемых issues
     * @return количество issues
     */
    public int getIssuesCount() {
        if (noIssuesMessage.isDisplayed()) {
            return 0;
        }
        return issueRows.size();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы списка issues");
        newIssueButton.shouldBe(visible);
    }
}
