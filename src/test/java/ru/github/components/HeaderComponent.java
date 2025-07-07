package ru.github.components;

import ru.github.components.elements.ButtonElement;
import ru.github.components.elements.ImageElement;

/**
 * Компонент заголовка страницы с навигацией
 */
public class HeaderComponent extends BaseComponent {

    private final ImageElement userAvatar = ImageElement.byCssSelector("img.avatar.circle");
    private final ButtonElement dropdownCaret = ButtonElement.byCssSelector(".dropdown-caret");

    /**
     * Конструктор компонента заголовка
     */
    public HeaderComponent() {
        log.info("Инициализация компонента заголовка");
    }
    
    /**
     * Открывает меню пользователя
     */
    public void openUserMenu() {
        log.info("Открытие меню пользователя");
        
        if (userAvatar.exists()) {
            userAvatar.click();
        } else if (dropdownCaret.exists()) {
            dropdownCaret.click();
        } else {
            log.warn("Меню пользователя не найдено. Возможно, пользователь не авторизован.");
        }
    }
    
    /**
     * Выходит из системы
     */
    public void signOut() {
        log.info("Выход из системы");
        openUserMenu();

        // Сначала нужно открыть меню, а потом искать кнопку
        ButtonElement signOutButton = ButtonElement.byXpath("//a[@href='/logout'] | //a[contains(text(), 'Sign out')] | //button[contains(text(), 'Sign out')]");
        signOutButton.waitForVisible();
        if (signOutButton.exists()) {
            signOutButton.click();
            ButtonElement signOutConfirmButton = ButtonElement.byXpath("//input[@data-disable-with='Sign out from all accounts']");
            signOutConfirmButton.waitForVisible();
            signOutConfirmButton.click();
        } else {
            log.warn("Кнопка выхода не найдена в меню");
        }
    }
}
