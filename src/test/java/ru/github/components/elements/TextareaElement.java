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
     * Создает элемент textarea по placeholder
     * @param placeholder значение атрибута placeholder
     * @return TextareaElement
     */
    public static TextareaElement byPlaceholder(String placeholder) {
        return new TextareaElement($("textarea[placeholder*='" + placeholder + "']"));
    }
}
