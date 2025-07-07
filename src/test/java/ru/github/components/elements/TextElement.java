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
     * Создает текстовый элемент по data-testid
     * @param testId значение атрибута data-testid
     * @return TextElement
     */
    public static TextElement byTestId(String testId) {
        return new TextElement($("[data-testid='" + testId + "']"));
    }
    
    /**
     * Создает текстовый элемент по aria-label
     * @param ariaLabel значение атрибута aria-label
     * @return TextElement
     */
    public static TextElement byAriaLabel(String ariaLabel) {
        return new TextElement($("[aria-label*='" + ariaLabel + "']"));
    }
}
