package ru.github.pages;

import ru.github.components.elements.TextElement;
import ru.github.components.CommentComponent;
import ru.github.components.CommentFormComponent;
import ru.github.components.IssueActionsComponent;
import ru.github.components.IssueControlsComponent;
import ru.github.components.IssueInfoComponent;
import ru.github.pages.BasePage;

/**
 * Страница деталей issue
 */
public class IssueDetailsPage extends BasePage {
    
    private final IssueInfoComponent issueInfo = new IssueInfoComponent();
    private final IssueControlsComponent issueControls = new IssueControlsComponent();
    private final CommentFormComponent commentForm = new CommentFormComponent();
    private final IssueActionsComponent issueActions = new IssueActionsComponent();
    private final TextElement permissionsTooltip = TextElement.byAriaLabel("permission");

    /**
     * Конструктор страницы деталей issue
     */
    public IssueDetailsPage() {
        super();
        waitForPageLoad();
    }
    
    /**
     * Получает заголовок issue
     * @return заголовок issue
     */
    public String getTitle() {
        return issueInfo.getTitle();
    }
    
    /**
     * Получает описание issue
     * @return описание issue
     */
    public String getDescription() {
        return issueInfo.getDescription();
    }
    
    /**
     * Получает статус issue
     * @return статус issue
     */
    public String getStatus() {
        return issueInfo.getStatus();
    }
    
    /**
     * Проверяет, что issue открыта
     * @return true, если issue открыта
     */
    public boolean isIssueStatusOpen() {
        return issueInfo.isIssueStatusOpen();
    }
    
    /**
     * Проверяет, что issue закрыта
     * @return true, если issue закрыта
     */
    public boolean isIssueStatusClosed() {
        return issueInfo.isIssueStatusClosed();
    }
    
    /**
     * Нажимает кнопку редактирования issue
     * @return текущая страница
     */
    public IssueDetailsPage clickEdit() {
        issueControls.clickEdit();
        return this;
    }
    
    /**
     * Редактирует заголовок issue
     * @param newTitle новый заголовок
     * @return текущая страница
     */
    public IssueDetailsPage editTitle(String newTitle) {
        clickEdit();
        issueInfo.editTitle(newTitle);
        return this;
    }
    
    /**
     * Закрывает issue
     * @return текущая страница
     */
    public IssueDetailsPage clickClose() {
        issueControls.clickClose();
        return this;
    }
    
    /**
     * Переоткрывает issue
     * @return текущая страница
     */
    public IssueDetailsPage clickReopen() {
        issueControls.clickReopen();
        return this;
    }
    
    /**
     * Закрепляет issue
     * @return текущая страница
     */
    public IssueDetailsPage clickPin() {
        issueControls.clickPin();
        return this;
    }
    
    /**
     * Блокирует обсуждение issue
     * @return текущая страница
     */
    public IssueDetailsPage clickLockConversation() {
        issueControls.clickLockConversation();
        return this;
    }
    
    /**
     * Добавляет комментарий к issue
     * @param commentText текст комментария
     * @return текущая страница
     */
    public IssueDetailsPage addComment(String commentText) {
        commentForm.addComment(commentText);
        return this;
    }
    
    /**
     * Получает компонент действий с issue
     * @return компонент действий
     */
    public IssueActionsComponent getIssueActions() {
        return issueActions;
    }
    
    /**
     * Получает компонент информации о issue
     * @return компонент информации
     */
    public IssueInfoComponent getIssueInfo() {
        return issueInfo;
    }
    
    /**
     * Получает компонент управления issue
     * @return компонент управления
     */
    public IssueControlsComponent getIssueControls() {
        return issueControls;
    }
    
    /**
     * Получает компонент формы комментариев
     * @return компонент формы комментариев
     */
    public CommentFormComponent getCommentForm() {
        return commentForm;
    }
    
    /**
     * Получает элемент подсказки о правах доступа
     * @return элемент подсказки
     */
    public TextElement getPermissions() {
        return permissionsTooltip;
    }
    
    /**
     * Получает комментарий по тексту
     * @param commentText текст комментария
     * @return компонент комментария
     */
    public CommentComponent getCommentByText(String commentText) {
        return new CommentComponent(commentText);
    }
    
    /**
     * Проверяет, что обсуждение заблокировано
     * @return true, если обсуждение заблокировано
     */
    public boolean isConversationLocked() {
        return issueControls.isConversationLocked();
    }
    
    /**
     * Проверяет, что кнопка закрытия недоступна
     * @return true, если кнопка закрытия недоступна
     */
    public boolean isCloseButtonDisabled() {
        return issueControls.isCloseButtonDisabled();
    }
    
    /**
     * Получает текст подсказки о недостатке прав
     * @return текст подсказки
     */
    public String getNoPermissionTooltip() {
        return permissionsTooltip.exists() ? permissionsTooltip.getAttribute("aria-label") : "";
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы деталей issue");
        issueInfo.waitForLoad();
    }
}
