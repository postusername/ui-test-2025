package ru.github.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.github.components.elements.BaseElement;
import ru.github.components.elements.InputElement;
import ru.github.components.elements.TextareaElement;
import ru.github.components.elements.TextElement;

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
}
