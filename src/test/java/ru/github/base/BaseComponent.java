package ru.github.base;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

/**
 * Базовый класс для всех UI компонентов
 */
public abstract class BaseComponent {
    
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    /**
     * Создает элемент по ID
     * @param id значение атрибута id
     * @return SelenideElement
     */
    protected static SelenideElement byId(String id) {
        return $("#" + id);
    }
    
    /**
     * Создает элемент по классу CSS
     * @param className название CSS класса
     * @return SelenideElement
     */
    protected static SelenideElement byClass(String className) {
        return $("." + className);
    }
    
    /**
     * Создает элемент по тексту
     * @param text текст элемента
     * @return SelenideElement
     */
    protected static SelenideElement byText(String text) {
        return $(com.codeborne.selenide.Selectors.byText(text));
    }
    
    /**
     * Создает элемент по XPath
     * @param xpath выражение XPath
     * @return SelenideElement
     */
    protected static SelenideElement byXpath(String xpath) {
        return $(com.codeborne.selenide.Selectors.byXpath(xpath));
    }
    
    /**
     * Создает элемент по CSS селектору
     * @param cssSelector CSS селектор
     * @return SelenideElement
     */
    protected static SelenideElement byCssSelector(String cssSelector) {
        return $(cssSelector);
    }
    
    /**
     * Создает элемент по имени
     * @param name значение атрибута name
     * @return SelenideElement
     */
    protected static SelenideElement byName(String name) {
        return $("[name='" + name + "']");
    }
    
    /**
     * Проверяет, что компонент видим
     * @param element элемент для проверки
     * @return true, если элемент видим
     */
    protected boolean isVisible(SelenideElement element) {
        return element.is(visible);
    }
    
    /**
     * Ожидает появления элемента
     * @param element элемент для ожидания
     */
    protected void waitForVisible(SelenideElement element) {
        element.shouldBe(visible);
    }
    
    /**
     * Ожидает исчезновения элемента
     * @param element элемент для ожидания
     */
    protected void waitForNotVisible(SelenideElement element) {
        element.shouldNotBe(visible);
    }
}
