package ru.github.components.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Элемент для текстовых элементов (span, div, p, h1-h6, etc.)
 */
public class TextElement extends BaseElement {
    
    /**
     * Конструктор по SelenideElement
     * @param element текстовый элемент
     */
    public TextElement(SelenideElement element) {
        super(element);
    }
    
    /**
     * Создает текстовый элемент по XPath
     * @param xpath выражение XPath
     * @return TextElement
     */
    public static TextElement byXpath(String xpath) {
        return new TextElement($(com.codeborne.selenide.Selectors.byXpath(xpath)));
    }
    
    /**
     * Создает текстовый элемент по CSS селектору
     * @param cssSelector CSS селектор
     * @return TextElement
     */
    public static TextElement byCssSelector(String cssSelector) {
        return new TextElement($(cssSelector));
    }
    
    /**
     * Создает текстовый элемент по ID
     * @param id значение атрибута id
     * @return TextElement
     */
    public static TextElement byId(String id) {
        return new TextElement($("#" + id));
    }
    
    /**
     * Создает текстовый элемент по тексту
     * @param text текст элемента
     * @return TextElement
     */
    public static TextElement byText(String text) {
        return new TextElement($(com.codeborne.selenide.Selectors.byText(text)));
    }
    
    /**
     * Создает текстовый элемент по data-testid
     * @param testId значение атрибута data-testid
     * @return TextElement
     */
    public static TextElement byTestId(String testId) {
        return new TextElement($("[data-testid='" + testId + "']"));
    }
    
    /**
     * Создает текстовый элемент по классу
     * @param className CSS класс
     * @return TextElement
     */
    public static TextElement byClass(String className) {
        return new TextElement($("." + className));
    }
    
    /**
     * Создает текстовый элемент по aria-label
     * @param ariaLabel значение атрибута aria-label
     * @return TextElement
     */
    public static TextElement byAriaLabel(String ariaLabel) {
        return new TextElement($("[aria-label*='" + ariaLabel + "']"));
    }
    
    /**
     * Получает текст элемента с ожиданием видимости
     * @return текст элемента
     */
    public String getTextWithWait() {
        log.info("Получаю текст элемента с ожиданием видимости");
        waitForVisible();
        return element.getText();
    }
    
    /**
     * Проверяет, что элемент содержит указанный текст
     * @param expectedText ожидаемый текст
     * @return true, если элемент содержит текст
     */
    public boolean isContainsText(String expectedText) {
        return getText().contains(expectedText);
    }
    
    /**
     * Проверяет, что элемент содержит указанный текст (case-insensitive)
     * @param expectedText ожидаемый текст
     * @return true, если элемент содержит текст
     */
    public boolean isContainsTextIgnoreCase(String expectedText) {
        return getText().toLowerCase().contains(expectedText.toLowerCase());
    }
    
    /**
     * Кликает по текстовому элементу
     * @return текущий элемент
     */
    public TextElement click() {
        log.info("Кликаю по текстовому элементу");
        waitForVisible();
        element.click();
        return this;
    }
    
    /**
     * Получает внутренний HTML элемента
     * @return HTML содержимое
     */
    public String getInnerHtml() {
        return element.innerHtml();
    }
}
