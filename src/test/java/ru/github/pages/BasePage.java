package ru.github.pages;

import com.codeborne.selenide.WebDriverRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.github.components.elements.BaseElement;
import ru.github.components.elements.InputElement;
import ru.github.components.elements.TextareaElement;
import ru.github.components.elements.TextElement;

import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.url;

/**
 * Базовый класс для всех страниц
 */
public abstract class BasePage {
    
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    /**
     * Конструктор базовой страницы
     */
    public BasePage() {
        log.info("Открыта страница: {}", this.getClass().getSimpleName());
    }
    
    /**
     * Получает текущий URL страницы
     * @return текущий URL
     */
    public String getCurrentUrl() {
        return url();
    }
    
    /**
     * Получает заголовок страницы
     * @return заголовок страницы
     */
    public String getPageTitle() {
        return title();
    }
    
    /**
     * Проверяет, что URL содержит указанную строку
     * @param expectedUrlPart ожидаемая часть URL
     * @return true, если URL содержит указанную строку
     */
    public boolean isUrlContains(String expectedUrlPart) {
        return getCurrentUrl().contains(expectedUrlPart);
    }
    
    /**
     * Ожидает загрузки страницы
     * Переопределяется в наследниках при необходимости
     */
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы: {}", this.getClass().getSimpleName());
    }
    
    /**
     * Ожидает видимости нескольких элементов
     * @param elements элементы для ожидания
     */
    protected void waitForElementsVisible(BaseElement... elements) {
        for (BaseElement element : elements) {
            element.waitForVisible();
        }
    }
    
    /**
     * Заполняет поле ввода с логированием
     * @param field поле ввода
     * @param value значение
     * @param fieldName название поля для логирования
     */
    protected void fillField(InputElement field, String value, String fieldName) {
        log.info("Заполнение поля '{}': {}", fieldName, value);
        field.setValue(value);
    }
    
    /**
     * Заполняет textarea с логированием
     * @param field поле textarea
     * @param value значение
     * @param fieldName название поля для логирования
     */
    protected void fillTextarea(TextareaElement field, String value, String fieldName) {
        log.info("Заполнение поля '{}': {}", fieldName, value);
        field.setValue(value);
    }
    
    /**
     * Очищает поле ввода с логированием
     * @param field поле ввода
     * @param fieldName название поля для логирования
     */
    protected void clearField(InputElement field, String fieldName) {
        log.info("Очистка поля '{}'", fieldName);
        field.clear();
    }
    
    /**
     * Выполняет поиск через поле ввода
     * @param searchField поле поиска
     * @param searchQuery поисковый запрос
     * @param searchType тип поиска для логирования
     */
    protected void performSearch(InputElement searchField, String searchQuery, String searchType) {
        log.info("Поиск {}: {}", searchType, searchQuery);
        searchField.setValue(searchQuery);
        searchField.pressEnter();
    }
    
    /**
     * Проверяет наличие ошибки в текстовом элементе
     * @param errorElement элемент с ошибкой
     * @return true, если ошибка отображается
     */
    protected boolean hasError(TextElement errorElement) {
        return errorElement.exists() && errorElement.isDisplayed();
    }
    
    /**
     * Проверяет наличие ошибки с определенным текстом
     * @param errorElement элемент с ошибкой
     * @param expectedErrorText ожидаемый текст ошибки
     * @return true, если ошибка с таким текстом отображается
     */
    protected boolean hasErrorWithText(TextElement errorElement, String expectedErrorText) {
        return hasError(errorElement) && errorElement.getText().contains(expectedErrorText);
    }
    
    /**
     * Получает текст ошибки
     * @param errorElement элемент с ошибкой
     * @return текст ошибки или пустую строку
     */
    protected String getErrorText(TextElement errorElement) {
        return hasError(errorElement) ? errorElement.getText() : "";
    }
}
