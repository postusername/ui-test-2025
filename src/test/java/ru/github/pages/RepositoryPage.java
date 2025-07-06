package ru.github.pages;

import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.TextElement;

import static com.codeborne.selenide.Selenide.*;

/**
 * Страница репозитория GitHub
 */
public class RepositoryPage extends BasePage {
    
    private static final String ISSUES_TAB_TEXT = "Issues";
    
    private final ButtonElement issuesTab = ButtonElement.byId("issues-tab");
    private final TextElement repositoryName = TextElement.byCssSelector("[data-pjax='#js-repo-pjax-container'] h1");
    private final ButtonElement codeTab = ButtonElement.byId("code-tab");
    
    /**
     * Конструктор страницы репозитория
     */
    public RepositoryPage() {
        super();
        waitForPageLoad();
    }
    
    /**
     * Переходит на вкладку Issues
     * @return страница со списком issues
     */
    public IssuesListPage navigateToIssues() {
        log.info("Переход на вкладку 'Issues'");
        issuesTab.click();
        return new IssuesListPage();
    }
    
    /**
     * Переходит на вкладку Issues (алиас для navigateToIssues)
     * @return страница со списком issues
     */
    public IssuesListPage clickIssuesTab() {
        return navigateToIssues();
    }
    
    /**
     * Переходит на вкладку Code
     * @return страница с кодом
     */
    public RepositoryPage navigateToCode() {
        log.info("Переход на вкладку 'Code'");
        codeTab.click();
        return this;
    }
    
    /**
     * Получает имя репозитория
     * @return имя репозитория
     */
    public String getRepositoryName() {
        return repositoryName.getText();
    }
    
    /**
     * Проверяет, что находимся на странице репозитория
     * @return true, если на странице репозитория
     */
    public boolean isOnRepositoryPage() {
        return issuesTab.isDisplayed() && codeTab.isDisplayed();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы репозитория");
        waitForElementsVisible(issuesTab, codeTab);
    }
}
