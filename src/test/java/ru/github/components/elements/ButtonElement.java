package ru.github.components.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Элемент кнопки
 */
public class ButtonElement extends BaseElement {
    
    /**
     * Конструктор по SelenideElement
     * @param element элемент кнопки
     */
    public ButtonElement(SelenideElement element) {
        super(element);
    }
    
    /**
     * Создает элемент кнопки по XPath
     * @param xpath выражение XPath
     * @return ButtonElement
     */
    public static ButtonElement byXpath(String xpath) {
        return new ButtonElement($(com.codeborne.selenide.Selectors.byXpath(xpath)));
    }
    
    /**
     * Создает элемент кнопки по CSS селектору
     * @param cssSelector CSS селектор
     * @return ButtonElement
     */
    public static ButtonElement byCssSelector(String cssSelector) {
        return new ButtonElement($(cssSelector));
    }
    
    /**
     * Создает элемент кнопки по ID
     * @param id значение атрибута id
     * @return ButtonElement
     */
    public static ButtonElement byId(String id) {
        return new ButtonElement($("#" + id));
    }
    
    /**
     * Создает элемент кнопки по тексту
     * @param text текст кнопки
     * @return ButtonElement
     */
    public static ButtonElement byText(String text) {
        return new ButtonElement($(com.codeborne.selenide.Selectors.byText(text)));
    }
    
    /**
     * Создает элемент кнопки по data-testid
     * @param testId значение атрибута data-testid
     * @return ButtonElement
     */
    public static ButtonElement byTestId(String testId) {
        return new ButtonElement($("[data-testid='" + testId + "']"));
    }
    
    /**
     * Кликает по кнопке с предварительной проверкой видимости
     * @return текущий элемент
     */
    public ButtonElement click() {
        log.info("Кликаю по кнопке");
        waitForVisible();
        element.click();
        return this;
    }
    
    /**
     * Кликает по кнопке без проверки видимости
     * @return текущий элемент
     */
    public ButtonElement forceClick() {
        log.info("Принудительный клик по кнопке");
        element.click();
        return this;
    }
} 