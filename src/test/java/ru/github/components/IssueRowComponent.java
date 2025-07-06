package ru.github.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.github.components.BaseComponent;
import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.ImageElement;
import ru.github.components.elements.TextElement;

import static com.codeborne.selenide.Selenide.*;

/**
 * Компонент строки issue в списке
 */
public class IssueRowComponent extends BaseComponent {
    
    private final SelenideElement issueRow;
    private final ButtonElement titleLink;
    private final TextElement statusBadge;
    private final ElementsCollection labels;
    private final ImageElement pinnedIndicator;
    
    /**
     * Конструктор компонента строки issue
     * @param issueRow элемент строки issue
     */
    public IssueRowComponent(SelenideElement issueRow) {
        this.issueRow = issueRow;
        this.titleLink = new ButtonElement(issueRow.$(".Link--primary"));
        this.statusBadge = new TextElement(issueRow.$(".State"));
        this.labels = issueRow.$$(".Label");
        this.pinnedIndicator = new ImageElement(issueRow.$(".octicon-pin"));
        
        log.info("Инициализация компонента строки issue");
    }
    
    /**
     * Получает заголовок issue
     * @return заголовок issue
     */
    public String getTitle() {
        return titleLink.getText();
    }
    
    /**
     * Получает статус issue
     * @return статус issue
     */
    public String getStatus() {
        return statusBadge.getText();
    }
    
    /**
     * Проверяет, что issue открыта
     * @return true, если issue открыта
     */
    public boolean isOpen() {
        return statusBadge.getAttribute("title").contains("Open");
    }
    
    /**
     * Проверяет, что issue закрыта
     * @return true, если issue закрыта
     */
    public boolean isClosed() {
        return statusBadge.getAttribute("title").contains("Closed");
    }
    
    /**
     * Получает список меток issue
     * @return список названий меток
     */
    public java.util.List<String> getLabels() {
        return labels.texts();
    }
    
    /**
     * Проверяет, что issue имеет указанную метку
     * @param labelName название метки
     * @return true, если метка присутствует
     */
    public boolean hasLabel(String labelName) {
        return getLabels().contains(labelName);
    }
    
    /**
     * Проверяет, что issue закреплена
     * @return true, если issue закреплена
     */
    public boolean isPinned() {
        return pinnedIndicator.isDisplayed();
    }
    
    /**
     * Нажимает на заголовок issue
     */
    public void clickTitle() {
        log.info("Нажатие на заголовок issue: {}", getTitle());
        titleLink.click();
    }
    
    /**
     * Нажимает на строку issue
     */
    public void click() {
        log.info("Нажатие на строку issue");
        issueRow.click();
    }
    
    /**
     * Проверяет, что строка issue отображается
     * @return true, если строка видна
     */
    public boolean isVisible() {
        return isVisible(issueRow);
    }
}
