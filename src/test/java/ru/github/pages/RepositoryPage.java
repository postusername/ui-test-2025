package ru.github.pages;

import ru.github.components.elements.ButtonElement;

/**
 * Страница репозитория GitHub
 */
public class RepositoryPage extends BasePage {
    private final ButtonElement codeTab = ButtonElement.byId("code-tab");
    private final ButtonElement issuesTab = ButtonElement.byId("issues-tab");
    
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
    public IssuesListPage clickIssuesTab() {
        log.info("Переход на вкладку 'Issues'");
        issuesTab.click();
        return new IssuesListPage();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы репозитория");
        waitForElementsVisible(issuesTab, codeTab);
    }
}
