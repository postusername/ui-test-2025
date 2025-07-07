package ru.github.components.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Элемент изображения (img)
 */
public class ImageElement extends BaseElement {
    
    /**
     * Конструктор по SelenideElement
     * @param element элемент изображения
     */
    public ImageElement(SelenideElement element) {
        super(element);
    }
    
    /**
     * Кликает по изображению
     * @return текущий элемент
     */
    public ImageElement click() {
        log.info("Кликаю по изображению");
        waitForVisible();
        element.click();
        return this;
    }

    /**
     * Создает элемент изображения по CSS селектору
     * @param cssSelector CSS селектор
     * @return ImageElement
     */
    public static ImageElement byCssSelector(String cssSelector) {
        return new ImageElement($(cssSelector));
    }

    
    /**
     * Создает элемент изображения по классу
     * @param className CSS класс
     * @return ImageElement
     */
    public static ImageElement byClass(String className) {
        return new ImageElement($("img." + className));
    }
}
