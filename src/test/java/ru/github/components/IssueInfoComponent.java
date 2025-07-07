package ru.github.components;

import ru.github.components.elements.TextElement;
import ru.github.components.elements.InputElement;

/**
 * Компонент информации о issue (заголовок, описание, статус)
 */
public class IssueInfoComponent extends BaseComponent {
    
    private static final String OPEN_STATUS = "Open";
    private static final String CLOSED_STATUS = "Closed";
    
    private final TextElement issueTitle = TextElement.byTestId("issue-title");
    private final TextElement statusBadge = TextElement.byTestId("header-state");
    private final InputElement titleInput = InputElement.byTestId("issue-title-input");
    
    /**
     * Конструктор компонента информации о issue
     */
    public IssueInfoComponent() {
        super();
    }
    
    /**
     * Получает заголовок issue
     * @return заголовок issue
     */
    public String getTitle() {
        return issueTitle.getTextWithWait();
    }
    
    /**
     * Получает статус issue
     * @return статус issue
     */
    public String getStatus() {
        return statusBadge.getTextWithWait();
    }
    
    /**
     * Проверяет, что issue открыта
     * @return true, если issue открыта
     */
    public boolean isIssueStatusOpen() {
        return statusBadge.isContainsText(OPEN_STATUS);
    }
    
    /**
     * Проверяет, что issue закрыта
     * @return true, если issue закрыта
     */
    public boolean isIssueStatusClosed() {
        return statusBadge.isContainsText(CLOSED_STATUS);
    }
    
    /**
     * Редактирует заголовок issue (inline редактирование)
     * @param newTitle новый заголовок
     * @return текущий компонент
     */
    public IssueInfoComponent editTitle(String newTitle) {
        log.info("Редактирование заголовка issue на: {}", newTitle);
        if (titleInput.exists()) {
            titleInput.setValue(newTitle);
            titleInput.pressEnter();
            issueTitle.waitForVisible();
        } else {
            log.warn("Поле редактирования заголовка не найдено");
        }
        
        return this;
    }
    
    /**
     * Ожидает загрузки компонента
     */
    public void waitForLoad() {
        log.debug("Ожидание загрузки компонента информации о issue");
        statusBadge.waitForVisible();
        issueTitle.waitForVisible();
    }
}
