package ru.github.pages;

import com.codeborne.selenide.SelenideElement;
import ru.github.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Страница репозитория GitHub
 */
public class RepositoryPage extends BasePage {
    
    private static final String ISSUES_TAB_TEXT = "Issues";
    
    private final SelenideElement issuesTab = $("#issues-tab");
    private final SelenideElement repositoryName = $("[data-pjax='#js-repo-pjax-container'] h1");
    private final SelenideElement codeTab = $("#code-tab");
    
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
        issuesTab.shouldBe(visible).click();
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
        codeTab.shouldBe(visible).click();
        return this;
    }
    
    /**
     * Получает имя репозитория
     * @return имя репозитория
     */
    public String getRepositoryName() {
        return repositoryName.shouldBe(visible).getText();
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
        issuesTab.shouldBe(visible);
        codeTab.shouldBe(visible);
    }
}
