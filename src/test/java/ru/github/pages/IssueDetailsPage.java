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
    
    private final SelenideElement issueTitle = $("[data-testid='issue-title']");
    private final SelenideElement issueTitleSticky = $("[data-testid='issue-title-sticky']");
    private final SelenideElement issueDescription = $("[data-testid='markdown-body']");
    private final SelenideElement statusBadge = $("[data-testid='header-state']");
    
    private final SelenideElement editButton = $$("[data-testid='edit-issue-title-button']").filterBy(visible).first();
    private final SelenideElement closeButton = $x("//div[contains(@class, 'IssueActions-module__IssueActionsButtonGroup--k8eny')]//button[1] | //div[contains(@class, 'IssueActions-module__IssueActionsButtonGroup') and contains(@class, 'prc-ButtonGroup-ButtonGroup')]//button[1]");
    private final SelenideElement reopenButton = $x("//div[contains(@class, 'IssueActions-module__IssueActionsButtonGroup--k8eny')]//button[1] | //div[contains(@class, 'IssueActions-module__IssueActionsButtonGroup') and contains(@class, 'prc-ButtonGroup-ButtonGroup')]//button[1]");
    private final SelenideElement pinButton = $x("//button[contains(text(), 'Pin issue')] | //span[contains(text(), 'Pin issue')]//ancestor::button | //*[contains(text(), 'Pin issue')]//ancestor::button");
    private final SelenideElement lockButton = $x("//button[contains(text(), 'Lock conversation')] | //a[contains(text(), 'Lock conversation')]");
    
    private final SelenideElement actionMenuButton = $x("//button[@aria-label='Issue body actions'] | //button[contains(@aria-label, 'actions')]");
    private final SelenideElement menuEditButton = $x("//div[@role='menu']//button[contains(text(), 'Edit')] | //div[@role='menu']//a[contains(text(), 'Edit')]");
    private final SelenideElement menuCloseButton = $x("//div[@role='menu']//button[contains(text(), 'Close')] | //div[@role='menu']//a[contains(text(), 'Close')]");
    private final SelenideElement menuPinButton = $x("//button[contains(text(), 'Pin issue')] | //span[contains(text(), 'Pin issue')]//ancestor::button | //*[contains(text(), 'Pin issue')]//ancestor::button");
    private final SelenideElement menuLockButton = $x("//div[@role='menu']//button[contains(text(), 'Lock')] | //div[@role='menu']//a[contains(text(), 'Lock')]");
    
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
     * Нажимает кнопку редактирования issue (inline редактирование)
     * @return текущая страница
     */
    public IssueDetailsPage clickEdit() {
        log.info("Нажатие кнопки 'Edit'");
        
        if (editButton.exists()) {
            editButton.shouldBe(visible).click();
        } else {
            if (actionMenuButton.exists()) {
                actionMenuButton.shouldBe(visible).click();
                menuEditButton.shouldBe(visible).click();
            } else {
                log.warn("Кнопка Edit не найдена ни в основном интерфейсе, ни в меню");
            }
        }
        
        return this;
    }
    
    /**
     * Редактирует заголовок issue (inline редактирование)
     * @param newTitle новый заголовок
     * @return текущая страница
     */
    public IssueDetailsPage editTitle(String newTitle) {
        log.info("Редактирование заголовка issue на: {}", newTitle);
        
        clickEdit();
        
        SelenideElement titleInput = $x("//input[contains(@class, 'form-control')] | //input[@type='text'] | //input[contains(@aria-label, 'title')]");
        
        if (titleInput.exists()) {
            titleInput.shouldBe(visible).clear();
            titleInput.setValue(newTitle);
            
            titleInput.pressEnter();
            
            issueTitle.shouldBe(visible);
        } else {
            log.warn("Поле редактирования заголовка не найдено");
        }
        
        return this;
    }
    
    /**
     * Закрывает issue
     * @return текущая страница
     */
    public IssueDetailsPage clickClose() {
        log.info("Нажатие кнопки 'Close issue'");
        
        String statusBefore = getStatus();
        log.info("Статус issue до нажатия: {}", statusBefore);
        
        if (closeButton.exists()) {
            log.info("Кнопка Close issue найдена, нажимаем");
            closeButton.shouldBe(visible).click();
            
            statusBadge.shouldBe(visible);
            
            SelenideElement confirmButton = $x("//button[contains(text(), 'Close issue')] | //button[contains(text(), 'Confirm')]");
            if (confirmButton.exists()) {
                log.info("Найдена кнопка подтверждения, нажимаем");
                confirmButton.shouldBe(visible).click();
            } else {
                log.info("Кнопка подтверждения не найдена");
            }
        } else {
            log.warn("Кнопка Close issue не найдена в основном интерфейсе");
            if (actionMenuButton.exists()) {
                log.info("Открываем меню действий");
                actionMenuButton.shouldBe(visible).click();
                menuCloseButton.shouldBe(visible).click();
                
                SelenideElement confirmButton = $x("//button[contains(text(), 'Close issue')] | //button[contains(text(), 'Confirm')]");
                if (confirmButton.exists()) {
                    log.info("Найдена кнопка подтверждения в меню, нажимаем");
                    confirmButton.shouldBe(visible).click();
                }
            } else {
                log.error("Кнопка закрытия issue не найдена");
            }
        }
        
        return this;
    }
    
    /**
     * Переоткрывает issue
     * @return текущая страница
     */
    public IssueDetailsPage clickReopen() {
        log.info("Нажатие кнопки 'Reopen issue'");
        
        if (reopenButton.exists()) {
            reopenButton.shouldBe(visible).click();
        } else {
            log.warn("Кнопка Reopen issue не найдена");
        }
        
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
     * Блокирует обсуждение issue
     * @return текущая страница
     */
    public IssueDetailsPage clickLockConversation() {
        log.info("Нажатие кнопки 'Lock conversation'");
        
        if (lockButton.exists()) {
            lockButton.shouldBe(visible).click();
        } else {
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
     * Добавляет комментарий к issue
     * @param commentText текст комментария
     * @return текущая страница
     */
    public IssueDetailsPage addComment(String commentText) {
        log.info("Добавление комментария: {}", commentText);
        commentField.shouldBe(visible).setValue(commentText);
        commentButton.shouldBe(visible).click();
        
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
        return lockedMessage.exists();
    }
    
    /**
     * Проверяет, что кнопка закрытия недоступна
     * @return true, если кнопка закрытия недоступна
     */
    public boolean isCloseButtonDisabled() {
        return closeButton.exists() && !closeButton.isEnabled();
    }
    
    /**
     * Получает текст подсказки о недостатке прав
     * @return текст подсказки
     */
    public String getNoPermissionTooltip() {
        return noPermissionTooltip.exists() ? noPermissionTooltip.getAttribute("aria-label") : "";
    }
    
    @Override
    protected void waitForPageLoad() {
        log.debug("Ожидание загрузки страницы деталей issue");
        statusBadge.shouldBe(visible);
        issueTitle.shouldBe(visible);
    }
}
