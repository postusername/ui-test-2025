package ru.github.components.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Элемент textarea
 */
public class TextareaElement extends BaseElement {
    
    /**
     * Конструктор по SelenideElement
     * @param element элемент textarea
     */
    public TextareaElement(SelenideElement element) {
        super(element);
    }
    
    /**
     * Создает элемент textarea по XPath
     * @param xpath выражение XPath
     * @return TextareaElement
     */
    public static TextareaElement byXpath(String xpath) {
        return new TextareaElement($(com.codeborne.selenide.Selectors.byXpath(xpath)));
    }
    
    /**
     * Создает элемент textarea по CSS селектору
     * @param cssSelector CSS селектор
     * @return TextareaElement
     */
    public static TextareaElement byCssSelector(String cssSelector) {
        return new TextareaElement($(cssSelector));
    }
    
    /**
     * Создает элемент textarea по ID
     * @param id значение атрибута id
     * @return TextareaElement
     */
    public static TextareaElement byId(String id) {
        return new TextareaElement($("#" + id));
    }
    
    /**
     * Создает элемент textarea по placeholder
     * @param placeholder значение атрибута placeholder
     * @return TextareaElement
     */
    public static TextareaElement byPlaceholder(String placeholder) {
        return new TextareaElement($("textarea[placeholder*='" + placeholder + "']"));
    }
    
    /**
     * Создает элемент textarea по имени
     * @param name значение атрибута name
     * @return TextareaElement
     */
    public static TextareaElement byName(String name) {
        return new TextareaElement($("textarea[name='" + name + "']"));
    }
    
    /**
     * Устанавливает значение в textarea с предварительной проверкой видимости
     * @param value значение для установки
     * @return текущий элемент
     */
    public TextareaElement setValue(String value) {
        log.info("Устанавливаю значение в textarea: {}", value);
        waitForVisible();
        element.setValue(value);
        return this;
    }
    
    /**
     * Добавляет текст к существующему значению
     * @param text текст для добавления
     * @return текущий элемент
     */
    public TextareaElement appendText(String text) {
        log.info("Добавляю текст к textarea: {}", text);
        waitForVisible();
        element.append(text);
        return this;
    }
    
    /**
     * Очищает поле textarea
     * @return текущий элемент
     */
    public TextareaElement clear() {
        log.info("Очищаю textarea");
        waitForVisible();
        element.clear();
        return this;
    }
    
    /**
     * Получает значение из textarea
     * @return значение textarea
     */
    public String getValue() {
        return element.getValue();
    }
    
    /**
     * Проверяет, что поле пустое
     * @return true, если поле пустое
     */
    public boolean isEmpty() {
        return getValue().isEmpty();
    }
    
    /**
     * Кликает по textarea
     * @return текущий элемент
     */
    public TextareaElement click() {
        log.info("Кликаю по textarea");
        waitForVisible();
        element.click();
        return this;
    }
}
