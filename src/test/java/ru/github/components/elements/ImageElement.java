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
     * Создает элемент изображения по XPath
     * @param xpath выражение XPath
     * @return ImageElement
     */
    public static ImageElement byXpath(String xpath) {
        return new ImageElement($(com.codeborne.selenide.Selectors.byXpath(xpath)));
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
     * Создает элемент изображения по ID
     * @param id значение атрибута id
     * @return ImageElement
     */
    public static ImageElement byId(String id) {
        return new ImageElement($("#" + id));
    }
    
    /**
     * Создает элемент изображения по alt тексту
     * @param altText значение атрибута alt
     * @return ImageElement
     */
    public static ImageElement byAlt(String altText) {
        return new ImageElement($("img[alt*='" + altText + "']"));
    }
    
    /**
     * Создает элемент изображения по классу
     * @param className CSS класс
     * @return ImageElement
     */
    public static ImageElement byClass(String className) {
        return new ImageElement($("img." + className));
    }
    
    /**
     * Получает источник изображения (src)
     * @return URL изображения
     */
    public String getSrc() {
        return element.getAttribute("src");
    }
    
    /**
     * Получает альтернативный текст изображения
     * @return alt текст
     */
    public String getAlt() {
        return element.getAttribute("alt");
    }
    
    /**
     * Получает title изображения
     * @return title изображения
     */
    public String getTitle() {
        return element.getAttribute("title");
    }
    
    /**
     * Проверяет, что изображение загружено
     * @return true, если изображение загружено
     */
    public boolean isLoaded() {
        try {
            Object result = com.codeborne.selenide.Selenide.executeJavaScript(
                "return arguments[0].complete && arguments[0].naturalWidth > 0", element);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.warn("Не удалось проверить загрузку изображения: {}", e.getMessage());
            return false;
        }
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
}
