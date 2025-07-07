package ru.github.pages;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.github.components.elements.ButtonElement;

/**
 * Страница со списком issues
 */
public class IssuesListPage extends BasePage {
    private final ButtonElement newIssueButton = ButtonElement.byCssSelector("a[data-variant='primary']");
    
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
        
        newIssueButton.click();
        
        // Ждем появления нового окна с WebDriverWait
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), java.time.Duration.ofSeconds(10));
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
     * Проверяет, что issue с заголовком существует
     * @param issueTitle заголовок issue
     * @return true, если issue существует
     */
    public boolean isIssueWithTitleExists(String issueTitle) {
        log.info("Проверка существования issue с заголовком: {}", issueTitle);
        ButtonElement issueLink = ButtonElement.byXpath("//a[contains(@class, 'IssuePullRequestTitle') and contains(., '" + issueTitle + "')]");
        issueLink.waitForVisible();
        boolean exists = issueLink.exists();
        log.info("Issue '{}' существует: {}", issueTitle, exists);
        return exists;
    }

    /**
     * Проверяет, что issue с заголовком закреплена
     * @param issueTitle заголовок issue
     * @return true, если issue закреплена
     */
    public boolean isIssueWithTitlePinned(String issueTitle) {
        log.info("Проверка закрепления issue с заголовком: {}", issueTitle);
        ButtonElement issueLink = ButtonElement.byXpath("//li[contains(@class, 'PinnedIssues') and contains(., '" + issueTitle + "')]");
        issueLink.waitForVisible();
        boolean exists = issueLink.exists();
        log.info("Issue '{}' закреплен: {}", issueTitle, exists);
        return exists;
    }

    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы списка issues");
        newIssueButton.waitForVisible();
    }
}
