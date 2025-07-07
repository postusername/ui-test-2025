package ru.github.components.elements;

import com.codeborne.selenide.SelenideElement;

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
     * Получает значение из input
     * @return значение input
     */
    public String getValue() {
        return element.getValue();
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
     * Создает элемент input по aria-label
     * @param ariaLabel значение атрибута aria-label
     * @return InputElement
     */
    public static InputElement byAriaLabel(String ariaLabel) {
        return new InputElement($("input[aria-label*='" + ariaLabel + "']"));
    }
}
