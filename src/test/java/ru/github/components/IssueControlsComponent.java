package ru.github.components;

import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.TextElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Компонент кнопок управления issue
 */
public class IssueControlsComponent extends BaseComponent {
    // Длинные XPath для покрытия всех вариантов A/B-тестов интерфейса

    private final ButtonElement editButton = new ButtonElement($$("[data-testid='edit-issue-title-button']").filterBy(visible).first());
    private final ButtonElement closeButton = ButtonElement.byXpath("//div[contains(@class, 'IssueActions-module__IssueActionsButtonGroup--k8eny')]//button[1] | //div[contains(@class, 'IssueActions-module__IssueActionsButtonGroup') and contains(@class, 'prc-ButtonGroup-ButtonGroup')]//button[1]");
    private final ButtonElement reopenButton = ButtonElement.byXpath("//div[contains(@class, 'IssueActions-module__IssueActionsButtonGroup--k8eny')]//button[1] | //div[contains(@class, 'IssueActions-module__IssueActionsButtonGroup') and contains(@class, 'prc-ButtonGroup-ButtonGroup')]//button[1]");
    private final ButtonElement pinButton = ButtonElement.byXpath("//button[contains(text(), 'Pin issue')] | //span[contains(text(), 'Pin issue')]//ancestor::button | //*[contains(text(), 'Pin issue')]//ancestor::button");
    private final ButtonElement lockButton = new ButtonElement($$(".prc-ActionList-ActionListContent-sg9-x").filterBy(visible).get(1));
    
    private final ButtonElement actionMenuButton = ButtonElement.byXpath("//button[@aria-label='Issue body actions'] | //button[contains(@aria-label, 'actions')]");
    private final ButtonElement menuEditButton = ButtonElement.byXpath("//div[@role='menu']//button[contains(text(), 'Edit')] | //div[@role='menu']//a[contains(text(), 'Edit')]");
    private final ButtonElement menuCloseButton = ButtonElement.byXpath("//div[@role='menu']//button[contains(text(), 'Close')] | //div[@role='menu']//a[contains(text(), 'Close')]");
    private final ButtonElement menuPinButton = ButtonElement.byXpath("//button[contains(text(), 'Pin issue')] | //span[contains(text(), 'Pin issue')]//ancestor::button | //*[contains(text(), 'Pin issue')]//ancestor::button");
    private final ButtonElement menuLockButton = ButtonElement.byXpath("//div[@role='menu']//button[contains(text(), 'Lock')] | //div[@role='menu']//a[contains(text(), 'Lock')]");
    
    private final ButtonElement lockConfirmButton = new ButtonElement($$(".prc-Button-ButtonBase-c50BI").filterBy(visible).get(2));
    private final TextElement lockedMessage = new TextElement($$(".row-module__timelineBodyContent--yAgzD").filterBy(visible).first());
    
    /**
     * Конструктор компонента кнопок управления issue
     */
    public IssueControlsComponent() {
        super();
    }
    
    /**
     * Нажимает кнопку редактирования issue
     * @return текущий компонент
     */
    public IssueControlsComponent clickEdit() {
        log.info("Нажатие кнопки 'Edit'");
        
        if (editButton.exists()) {
            editButton.click();
        } else {
            if (actionMenuButton.exists()) {
                actionMenuButton.click();
                menuEditButton.click();
            } else {
                log.warn("Кнопка Edit не найдена ни в основном интерфейсе, ни в меню");
            }
        }
        
        return this;
    }
    
    /**
     * Закрывает issue
     * @return текущий компонент
     */
    public IssueControlsComponent clickClose() {
        log.info("Нажатие кнопки 'Close issue'");
        
        if (closeButton.exists()) {
            log.info("Кнопка Close issue найдена, нажимаем");
            closeButton.click();
            
            ButtonElement confirmButton = ButtonElement.byXpath("//button[contains(text(), 'Close issue')] | //button[contains(text(), 'Confirm')]");
            if (confirmButton.exists()) {
                log.info("Найдена кнопка подтверждения, нажимаем");
                confirmButton.click();
            } else {
                log.info("Кнопка подтверждения не найдена");
            }
        } else {
            log.warn("Кнопка Close issue не найдена в основном интерфейсе");
            if (actionMenuButton.exists()) {
                log.info("Открываем меню действий");
                actionMenuButton.click();
                menuCloseButton.click();
                
                ButtonElement confirmButton = ButtonElement.byXpath("//button[contains(text(), 'Close issue')] | //button[contains(text(), 'Confirm')]");
                if (confirmButton.exists()) {
                    log.info("Найдена кнопка подтверждения в меню, нажимаем");
                    confirmButton.click();
                }
            } else {
                log.error("Кнопка закрытия issue не найдена");
            }
        }
        
        return this;
    }
    
    /**
     * Переоткрывает issue
     * @return текущий компонент
     */
    public IssueControlsComponent clickReopen() {
        log.info("Нажатие кнопки 'Reopen issue'");
        
        if (reopenButton.exists()) {
            reopenButton.click();
        } else {
            log.warn("Кнопка Reopen issue не найдена");
        }
        
        return this;
    }
    
    /**
     * Закрепляет issue
     * @return текущий компонент
     */
    public IssueControlsComponent clickPin() {
        log.info("Нажатие кнопки 'Pin issue'");
        
        if (pinButton.exists()) {
            pinButton.click();
        } else {
            if (actionMenuButton.exists()) {
                actionMenuButton.click();
                menuPinButton.click();
            } else {
                log.warn("Кнопка Pin issue не найдена ни в основном интерфейсе, ни в меню");
            }
        }
        
        return this;
    }
    
    /**
     * Блокирует обсуждение issue
     * @return текущий компонент
     */
    public IssueControlsComponent clickLockConversation() {
        log.info("Нажатие кнопки 'Lock conversation'");
        
        if (lockButton.exists()) {
            lockButton.click();
        } else {
            if (actionMenuButton.exists()) {
                actionMenuButton.click();
                menuLockButton.click();
            } else {
                log.warn("Кнопка Lock conversation не найдена ни в основном интерфейсе, ни в меню");
            }
        }

        if (lockConfirmButton.exists()) {
            lockConfirmButton.click();
        }
        
        lockedMessage.waitForVisible();

        return this;
    }
    
    /**
     * Проверяет, что кнопка закрытия недоступна
     * @return true, если кнопка закрытия недоступна
     */
    public boolean isCloseButtonDisabled() {
        return closeButton.exists() && !closeButton.isEnabled();
    }
    
    /**
     * Проверяет, что обсуждение заблокировано
     * @return true, если обсуждение заблокировано
     */
    public boolean isConversationLocked() {
        return lockedMessage.exists();
    }
}
