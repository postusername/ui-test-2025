package ru.github.components.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

/**
 * Элемент input
 */
public class InputElement extends BaseElement {
    
    /**
     * Конструктор по SelenideElement
     * @param element элемент input
     */
    public InputElement(SelenideElement element) {
        super(element);
    }
    
    /**
     * Создает элемент input по XPath
     * @param xpath выражение XPath
     * @return InputElement
     */
    public static InputElement byXpath(String xpath) {
        return new InputElement($(com.codeborne.selenide.Selectors.byXpath(xpath)));
    }
    
    /**
     * Создает элемент input по CSS селектору
     * @param cssSelector CSS селектор
     * @return InputElement
     */
    public static InputElement byCssSelector(String cssSelector) {
        return new InputElement($(cssSelector));
    }
    
    /**
     * Создает элемент input по ID
     * @param id значение атрибута id
     * @return InputElement
     */
    public static InputElement byId(String id) {
        return new InputElement($("#" + id));
    }
    
    public static InputElement byTestId(String testId) {
        return new InputElement($("[data-testid='" + testId + "']"));
    }

    /**
     * Создает элемент input по имени
     * @param name значение атрибута name
     * @return InputElement
     */
    public static InputElement byName(String name) {
        return new InputElement($("input[name='" + name + "']"));
    }
    
    /**
     * Создает элемент input по placeholder
     * @param placeholder значение атрибута placeholder
     * @return InputElement
     */
    public static InputElement byPlaceholder(String placeholder) {
        return new InputElement($("input[placeholder*='" + placeholder + "']"));
    }
    
    /**
     * Создает элемент input по типу
     * @param type значение атрибута type
     * @return InputElement
     */
    public static InputElement byType(String type) {
        return new InputElement($("input[type='" + type + "']"));
    }
    
    /**
     * Создает элемент input по aria-label
     * @param ariaLabel значение атрибута aria-label
     * @return InputElement
     */
    public static InputElement byAriaLabel(String ariaLabel) {
        return new InputElement($("input[aria-label*='" + ariaLabel + "']"));
    }
    
    /**
     * Устанавливает значение в input с предварительной проверкой видимости
     * @param value значение для установки
     * @return текущий элемент
     */
    public InputElement setValue(String value) {
        log.info("Устанавливаю значение в input: {}", value);
        waitForVisible();
        element.setValue(value);
        return this;
    }
    
    /**
     * Добавляет текст к существующему значению
     * @param text текст для добавления
     * @return текущий элемент
     */
    public InputElement appendText(String text) {
        log.info("Добавляю текст к input: {}", text);
        waitForVisible();
        element.append(text);
        return this;
    }
    
    /**
     * Очищает поле input
     * @return текущий элемент
     */
    public InputElement clear() {
        return setValue("");
    }
    
    /**
     * Получает значение из input
     * @return значение input
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
     * Кликает по input
     * @return текущий элемент
     */
    public InputElement click() {
        log.info("Кликаю по input");
        waitForVisible();
        element.click();
        return this;
    }
    
    /**
     * Нажимает Enter
     * @return текущий элемент
     */
    public InputElement pressEnter() {
        log.info("Нажимаю Enter в input");
        waitForVisible();
        element.pressEnter();
        return this;
    }
    
    /**
     * Нажимает Tab
     * @return текущий элемент
     */
    public InputElement pressTab() {
        log.info("Нажимаю Tab в input");
        waitForVisible();
        element.pressTab();
        return this;
    }
    
    /**
     * Нажимает Escape
     * @return текущий элемент
     */
    public InputElement pressEscape() {
        log.info("Нажимаю Escape в input");
        waitForVisible();
        element.pressEscape();
        return this;
    }
    
    /**
     * Нажимает произвольную клавишу
     * @param key клавиша
     * @return текущий элемент
     */
    public InputElement pressKey(Keys key) {
        log.info("Нажимаю клавишу {} в input", key);
        waitForVisible();
        element.sendKeys(key);
        return this;
    }
}
