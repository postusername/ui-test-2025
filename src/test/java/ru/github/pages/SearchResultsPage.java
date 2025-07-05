package ru.github.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.github.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Страница результатов поиска
 */
public class SearchResultsPage extends BasePage {
    
    private final ElementsCollection searchResults = $$(".repo-list-item");
    private final SelenideElement noResultsMessage = $(".blankslate");
    private final SelenideElement searchQuery = $(".search-form input[name='q']");
    
    /**
     * Конструктор страницы результатов поиска
     */
    public SearchResultsPage() {
        super();
        waitForPageLoad();
    }
    
    /**
     * Получает количество найденных результатов
     * @return количество результатов
     */
    public int getResultsCount() {
        if (noResultsMessage.isDisplayed()) {
            return 0;
        }
        return searchResults.size();
    }
    
    /**
     * Переходит к репозиторию по имени
     * @param repositoryName имя репозитория
     * @return страница репозитория
     */
    public RepositoryPage clickRepository(String repositoryName) {
        log.info("Переход к репозиторию: {}", repositoryName);
        SelenideElement repositoryLink = $x("//a[contains(@href, '/" + repositoryName + "')]");
        repositoryLink.shouldBe(visible).click();
        return new RepositoryPage();
    }
    
    /**
     * Проверяет, что результаты поиска отображаются
     * @return true, если результаты есть
     */
    public boolean hasResults() {
        return !noResultsMessage.isDisplayed() && searchResults.size() > 0;
    }
    
    /**
     * Получает текст поискового запроса
     * @return поисковый запрос
     */
    public String getSearchQuery() {
        return searchQuery.shouldBe(visible).getValue();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы результатов поиска");
        searchQuery.shouldBe(visible);
    }
}
