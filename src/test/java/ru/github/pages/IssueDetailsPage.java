package ru.github.pages;

import com.codeborne.selenide.SelenideElement;
import ru.github.base.BasePage;
import ru.github.components.IssueActionsComponent;
import ru.github.components.CommentComponent;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Страница деталей issue
 */
public class IssueDetailsPage extends BasePage {
    
    private static final String EDIT_BUTTON_TEXT = "Edit";
    private static final String CLOSE_BUTTON_TEXT = "Close issue";
    private static final String REOPEN_BUTTON_TEXT = "Reopen issue";
    private static final String PIN_BUTTON_TEXT = "Pin issue";
    private static final String LOCK_BUTTON_TEXT = "Lock conversation";
    private static final String COMMENT_BUTTON_TEXT = "Comment";
    private static final String CLOSED_STATUS = "Closed";
    private static final String OPEN_STATUS = "Open";
    
    // Обновленные локаторы для React версии GitHub
    private final SelenideElement issueTitle = $("[data-testid='issue-title']");
    private final SelenideElement issueTitleSticky = $("[data-testid='issue-title-sticky']");
    private final SelenideElement issueDescription = $("[data-testid='markdown-body']");
    private final SelenideElement statusBadge = $("[data-testid='header-state']");
    
    // Кнопки управления issue - могут быть в меню или отдельно
    private final SelenideElement editButton = $x("//button[contains(text(), 'Edit')] | //a[contains(text(), 'Edit')]");
    private final SelenideElement closeButton = $x("//button[contains(text(), 'Close issue')] | //a[contains(text(), 'Close issue')]");
    private final SelenideElement reopenButton = $x("//button[contains(text(), 'Reopen issue')] | //a[contains(text(), 'Reopen issue')]");
    private final SelenideElement pinButton = $x("//button[contains(text(), 'Pin issue')] | //a[contains(text(), 'Pin issue')]");
    private final SelenideElement lockButton = $x("//button[contains(text(), 'Lock conversation')] | //a[contains(text(), 'Lock conversation')]");
    
    // Альтернативные локаторы для кнопок в меню
    private final SelenideElement actionMenuButton = $x("//button[@aria-label='Issue body actions'] | //button[contains(@aria-label, 'actions')]");
    private final SelenideElement menuEditButton = $x("//div[@role='menu']//button[contains(text(), 'Edit')] | //div[@role='menu']//a[contains(text(), 'Edit')]");
    private final SelenideElement menuCloseButton = $x("//div[@role='menu']//button[contains(text(), 'Close')] | //div[@role='menu']//a[contains(text(), 'Close')]");
    private final SelenideElement menuPinButton = $x("//div[@role='menu']//button[contains(text(), 'Pin')] | //div[@role='menu']//a[contains(text(), 'Pin')]");
    private final SelenideElement menuLockButton = $x("//div[@role='menu']//button[contains(text(), 'Lock')] | //div[@role='menu']//a[contains(text(), 'Lock')]");
    
    // Комментарии - новая React структура
    private final SelenideElement commentField = $("textarea[placeholder*='comment']");
    private final SelenideElement commentButton = $x("//button[contains(text(), 'Comment')] | //button[@type='submit'][contains(@class, 'btn-primary')]");
    private final SelenideElement lockConfirmButton = $x("//button[contains(text(), 'Lock conversation') and contains(@class, 'btn-danger')]");
    private final SelenideElement lockedMessage = $x("//div[contains(text(), 'locked')] | //div[contains(@class, 'locked')]");
    private final SelenideElement noPermissionTooltip = $("[aria-label*='permission']");
    
    private final IssueActionsComponent issueActions = new IssueActionsComponent();
    
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
        if (issueTitle.exists()) {
            return issueTitle.shouldBe(visible).getText();
        }
        return issueTitleSticky.shouldBe(visible).getText();
    }
    
    /**
     * Получает описание issue
     * @return описание issue
     */
    public String getDescription() {
        return issueDescription.shouldBe(visible).getText();
    }
    
    /**
     * Получает статус issue
     * @return статус issue
     */
    public String getStatus() {
        return statusBadge.shouldBe(visible).getText();
    }
    
    /**
     * Проверяет, что issue открыта
     * @return true, если issue открыта
     */
    public boolean isOpen() {
        return statusBadge.shouldBe(visible).getText().contains(OPEN_STATUS);
    }
    
    /**
     * Проверяет, что issue закрыта
     * @return true, если issue закрыта
     */
    public boolean isClosed() {
        return statusBadge.shouldBe(visible).getText().contains(CLOSED_STATUS);
    }
    
    /**
     * Нажимает кнопку редактирования
     * @return страница редактирования issue
     */
    public EditIssuePage clickEdit() {
        log.info("Нажатие кнопки 'Edit'");
        
        if (editButton.exists()) {
            editButton.shouldBe(visible).click();
        } else {
            // Пытаемся найти кнопку в меню
            if (actionMenuButton.exists()) {
                actionMenuButton.shouldBe(visible).click();
                menuEditButton.shouldBe(visible).click();
            } else {
                log.warn("Кнопка Edit не найдена ни в основном интерфейсе, ни в меню");
            }
        }
        
        return new EditIssuePage();
    }
    
    /**
     * Закрывает issue
     * @return текущая страница
     */
    public IssueDetailsPage clickClose() {
        log.info("Нажатие кнопки 'Close issue'");
        
        if (closeButton.exists()) {
            closeButton.shouldBe(visible).click();
        } else {
            // Пытаемся найти кнопку в меню
            if (actionMenuButton.exists()) {
                actionMenuButton.shouldBe(visible).click();
                menuCloseButton.shouldBe(visible).click();
            } else {
                log.warn("Кнопка Close issue не найдена ни в основном интерфейсе, ни в меню");
            }
        }
        
        return this;
    }
    
    /**
     * Открывает закрытую issue
     * @return текущая страница
     */
    public IssueDetailsPage clickReopen() {
        log.info("Нажатие кнопки 'Reopen issue'");
        reopenButton.shouldBe(visible).click();
        return this;
    }
    
    /**
     * Закрепляет issue
     * @return текущая страница
     */
    public IssueDetailsPage clickPin() {
        log.info("Нажатие кнопки 'Pin issue'");
        
        if (pinButton.exists()) {
            pinButton.shouldBe(visible).click();
        } else {
            // Пытаемся найти кнопку в меню
            if (actionMenuButton.exists()) {
                actionMenuButton.shouldBe(visible).click();
                menuPinButton.shouldBe(visible).click();
            } else {
                log.warn("Кнопка Pin issue не найдена ни в основном интерфейсе, ни в меню");
            }
        }
        
        return this;
    }
    
    /**
     * Блокирует комментарии
     * @return текущая страница
     */
    public IssueDetailsPage clickLockConversation() {
        log.info("Нажатие кнопки 'Lock conversation'");
        
        if (lockButton.exists()) {
            lockButton.shouldBe(visible).click();
        } else {
            // Пытаемся найти кнопку в меню
            if (actionMenuButton.exists()) {
                actionMenuButton.shouldBe(visible).click();
                menuLockButton.shouldBe(visible).click();
            } else {
                log.warn("Кнопка Lock conversation не найдена ни в основном интерфейсе, ни в меню");
            }
        }
        
        if (lockConfirmButton.exists()) {
            lockConfirmButton.shouldBe(visible).click();
        }
        
        return this;
    }
    
    /**
     * Добавляет комментарий
     * @param commentText текст комментария
     * @return текущая страница
     */
    public IssueDetailsPage addComment(String commentText) {
        log.info("Добавление комментария: {}", commentText);
        
        if (commentField.exists()) {
            commentField.shouldBe(visible).setValue(commentText);
            commentButton.shouldBe(visible).click();
        } else {
            log.warn("Поле для комментария не найдено. Возможно, пользователь не авторизован.");
        }
        
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
     * Получает компонент комментария по тексту
     * @param commentText текст комментария
     * @return компонент комментария
     */
    public CommentComponent getCommentByText(String commentText) {
        SelenideElement commentElement = $x("//div[contains(@class, 'comment-body') and contains(text(), '" + commentText + "')]");
        return new CommentComponent(commentElement);
    }
    
    /**
     * Проверяет, что комментарии заблокированы
     * @return true, если комментарии заблокированы
     */
    public boolean isConversationLocked() {
        return lockedMessage.exists();
    }
    
    /**
     * Проверяет, что кнопка закрытия заблокирована
     * @return true, если кнопка заблокирована
     */
    public boolean isCloseButtonDisabled() {
        return closeButton.getAttribute("disabled") != null || !closeButton.exists();
    }
    
    /**
     * Получает текст всплывающей подсказки о недостатке прав
     * @return текст подсказки
     */
    public String getNoPermissionTooltip() {
        return noPermissionTooltip.getAttribute("aria-label");
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы деталей issue");
        // Ждем либо основной заголовок, либо статус, главное чтобы страница загрузилась
        try {
            if (issueTitle.exists()) {
                issueTitle.shouldBe(visible);
            } else {
                // Если основной заголовок не найден, проверим что элементы страницы есть
                statusBadge.shouldBe(visible);
            }
        } catch (Exception e) {
            log.debug("Не удалось найти заголовок issue, проверяем статус: {}", e.getMessage());
            // Если заголовок не найден, проверяем хотя бы что статус загрузился
            statusBadge.shouldBe(visible);
        }
    }
}
