package ru.github.pages;

import com.codeborne.selenide.ElementsCollection;
import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.InputElement;
import ru.github.components.elements.TextElement;

import static com.codeborne.selenide.Selenide.*;

/**
 * Страница результатов поиска
 */
public class SearchResultsPage extends BasePage {
    
    private final ElementsCollection searchResults = $$(".repo-list-item");
    private final TextElement noResultsMessage = TextElement.byCssSelector(".blankslate");
    private final InputElement searchQuery = InputElement.byCssSelector(".search-form input[name='q']");
    
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
        ButtonElement repositoryLink = ButtonElement.byXpath("//a[contains(@href, '/" + repositoryName + "')]");
        repositoryLink.click();
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
        return searchQuery.getValue();
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы результатов поиска");
        searchQuery.waitForVisible();
    }
}
