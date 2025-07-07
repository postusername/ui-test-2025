package ru.github.components.elements;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Базовый элемент - обертка над SelenideElement
 */
public abstract class BaseElement {
    
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected final SelenideElement element;
    
    /**
     * Конструктор базового элемента
     * @param element SelenideElement для обертки
     */
    public BaseElement(SelenideElement element) {
        this.element = element;
    }
    
    /**
     * Проверяет существование элемента
     * @return true, если элемент существует
     */
    public boolean exists() {
        try {
            return element.exists();
        } catch (Exception e) {
            log.warn("Ошибка при проверке существования элемента: {}", e.getMessage());
            return false;
        }
    }   
    
    /**
     * Проверяет видимость элемента
     * @return true, если элемент видим
     */
    public boolean isDisplayed() {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            log.warn("Ошибка при проверке видимости элемента: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Проверяет активность элемента
     * @return true, если элемент активен
     */
    public boolean isEnabled() {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            log.warn("Ошибка при проверке активности элемента: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Получает текст элемента
     * @return текст элемента
     */
    public String getText() {
        try {
            return element.getText();
        } catch (Exception e) {
            log.warn("Ошибка при получении текста элемента: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Получает значение атрибута
     * @param attributeName название атрибута
     * @return значение атрибута
     */
    public String getAttribute(String attributeName) {
        try {
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            log.warn("Ошибка при получении атрибута '{}': {}", attributeName, e.getMessage());
            return "";
        }
    }
    
    /**
     * Ожидает появления элемента
     * @return текущий элемент
     */
    public BaseElement waitForVisible() {
        try {
            element.should(com.codeborne.selenide.Condition.visible);
        } catch (Exception e) {
            log.warn("Ошибка при ожидании видимости элемента: {}", e.getMessage());
        }
        return this;
    }
} 